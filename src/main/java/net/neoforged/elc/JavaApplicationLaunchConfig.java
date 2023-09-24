package net.neoforged.elc;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import net.neoforged.elc.attribute.ELCAttribute;
import net.neoforged.elc.attribute.ListAttribute;
import net.neoforged.elc.attribute.MapAttribute;
import net.neoforged.elc.attribute.SimpleValue;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@ToString
@Builder(builderClassName = "Builder")
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
public final class JavaApplicationLaunchConfig extends LaunchConfig {
    @Default
    private final List<String> arguments = new ArrayList<>();
    @Default
    private final List<String> vmArguments = new ArrayList<>();
    @Default
    private final Map<String, String> environmentVariables = new HashMap<>();
    @Default
    private final List<String> classpath = new ArrayList<>();
    @Default
    private final List<String> modulePath = new ArrayList<>();

    @Default
    private final String workingDirectory = "";
    private final String mainClass;
    private final String projectAttribution;
    @Default @Nullable
    private final String moduleName = null;

    @Default
    private final boolean stopInMain = false;
    @Default @Nullable
    private final String jreContainer = null;

    @Default
    private final List<ELCAttribute<?>> extraAttributes = new ArrayList<>();

    @Override
    public String getType() {
        return "org.eclipse.jdt.launching.localJavaApplication";
    }

    @Override
    public List<ELCAttribute<?>> finishChildren() {
        final List<ELCAttribute<?>> attributes = new ArrayList<>();

        Stream.of(
                ELCAttribute.string("PROGRAM_ARGUMENTS", arguments.stream().map(a -> "\"" + a + "\"").collect(Collectors.joining(" "))),
                ELCAttribute.string("VM_ARGUMENTS", vmArguments.stream().map(a -> "\"" + a + "\"").collect(Collectors.joining(" "))),
                ELCAttribute.list("MODULEPATH", classpath.stream().map(SimpleValue::string).toArray(SimpleValue[]::new)),
                ELCAttribute.list("CLASSPATH", modulePath.stream().map(SimpleValue::string).toArray(SimpleValue[]::new)),
                ELCAttribute.string("WORKING_DIRECTORY", workingDirectory),

                ELCAttribute.string("PROJECT_ATTR", projectAttribution),
                ELCAttribute.string("MAIN_TYPE", mainClass),
                moduleName == null ? null : ELCAttribute.string("MODULE_NAME", moduleName),

                ELCAttribute.bool("STOP_IN_MAIN", stopInMain),
                jreContainer == null ? null : ELCAttribute.string("JRE_CONTAINER", jreContainer)
        ).filter(Objects::nonNull).filter(attr -> {
            return !(attr instanceof ListAttribute) || !((ListAttribute) attr).getValues().isEmpty();
        }).forEach(attr -> attributes.add(attr.prependKeyPath("org.eclipse.jdt.launching")));

        final MapAttribute envVars = new MapAttribute("org.eclipse.debug.core.environmentVariables");
        environmentVariables.forEach((k, v) -> envVars.put(k, SimpleValue.string(v)));
        attributes.add(envVars);

        attributes.addAll(extraAttributes);

        return attributes;
    }

    private static void addStringIfNotNull(List<ELCAttribute<?>> attributes, String key, @Nullable String value) {
        if (value != null) {
            attributes.add(ELCAttribute.string(key, value));
        }
    }

    public static final class Builder {
        public Builder argument(String argument) {
            this.arguments$set = true;
            this.arguments$value = (this.arguments$value == null ? new ArrayList<>() : this.arguments$value);
            this.arguments$value.add(argument);
            return this;
        }

        public Builder vmArgument(String argument) {
            this.vmArguments$set = true;
            this.vmArguments$value = (this.vmArguments$value == null ? new ArrayList<>() : this.vmArguments$value);
            this.vmArguments$value.add(argument);
            return this;
        }

        public Builder envVar(String key, String value) {
            this.environmentVariables$set = true;
            this.environmentVariables$value = (this.environmentVariables$value == null ? new HashMap<>() : this.environmentVariables$value);
            this.environmentVariables$value.put(key, value);
            return this;
        }
    }
}
