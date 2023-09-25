package net.neoforged.elc.configs;

import lombok.Builder;
import lombok.Builder.Default;
import net.neoforged.elc.attributes.EAttribute;
import net.neoforged.elc.attributes.EValue;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@Builder(builderClassName = "Builder")
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
public final class GradleLaunchConfig extends LaunchConfig {
    @Default
    private final List<String> tasks = new ArrayList<>();
    @Default
    private final List<String> arguments = new ArrayList<>();
    @Default
    private final List<String> jvmArguments = new ArrayList<>();
    @Default
    private final String workingDirectory = "";

    @Default
    private final String gradleDistribution = null;
    @Default
    private final String gradleUserHome = null;
    @Default
    private final String javaHome = null;

    @Default
    private final boolean offlineMode = false;
    @Default
    private final boolean showConsoleView = true;
    @Default
    private final boolean showExecutionView = true;

    @Override
    public String getType() {
        return "org.eclipse.buildship.core.launch.runconfiguration";
    }

    @Override
    public List<EAttribute<?>> finishChildren() {
        final List<EAttribute<?>> attributes = new ArrayList<>();

        attributes.add(EAttribute.list("tasks", tasks.stream().map(EValue::string).toArray(EValue[]::new)));
        attributes.add(EAttribute.list("arguments", arguments.stream().map(EValue::string).toArray(EValue[]::new)));
        attributes.add(EAttribute.list("jvm_arguments", jvmArguments.stream().map(EValue::string).toArray(EValue[]::new)));

        addStringIfNotNull(attributes, "working_dir", workingDirectory);

        addStringIfNotNull(attributes, "gradle_distribution", gradleDistribution);
        addStringIfNotNull(attributes, "gradle_user_home", gradleUserHome);
        addStringIfNotNull(attributes, "java_home", javaHome);

        attributes.add(EAttribute.bool("offline_mode", offlineMode));
        attributes.add(EAttribute.bool("show_console_view", showConsoleView));
        attributes.add(EAttribute.bool("show_execution_view", showConsoleView));

        return attributes;
    }

    private static void addStringIfNotNull(List<EAttribute<?>> attributes, String key, @Nullable String value) {
        if (value != null) {
            attributes.add(EAttribute.string(key, value));
        }
    }

    public static final class Builder {
        public Builder task(String task) {
            this.tasks$set = true;
            this.tasks$value = (this.tasks$value == null ? new ArrayList<>() : this.tasks$value);
            this.tasks$value.add(task);
            return this;
        }

        public Builder argument(String argument) {
            this.arguments$set = true;
            this.arguments$value = (this.arguments$value == null ? new ArrayList<>() : this.arguments$value);
            this.arguments$value.add(argument);
            return this;
        }

        public Builder gradleProperty(String key, Object value) {
            return argument("-P\"" + key + "\"=\"" + value + "\"");
        }

        public Builder jvmArgument(String argument) {
            this.jvmArguments$set = true;
            this.jvmArguments$value = (this.jvmArguments$value == null ? new ArrayList<>() : this.jvmArguments$value);
            this.jvmArguments$value.add(argument);
            return this;
        }
    }
}
