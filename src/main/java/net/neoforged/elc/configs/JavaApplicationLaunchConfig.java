package net.neoforged.elc.configs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.Nullable;

import net.neoforged.elc.EclipseVariables;
import net.neoforged.elc.attributes.EAttribute;
import net.neoforged.elc.attributes.EValue;
import net.neoforged.elc.util.Util;

/**
 * This class implements the <code>org.eclipse.jdt.launching.localJavaApplication</code> launch configuration type.
 * <p>
 * It is used for launching a Java Application locally via the main class.
 * 
 * @param project          The name of the Eclipse project associated with the launch.
 * @param mainClass        The Fully-Qualified name of the main class to launch.
 * @param arguments        Command-line arguments passed to the program before execution.
 * @param vmArguments      Command-line arguments passed to the JVM before startup.
 * @param envVars          Environment variables set during execution.
 * @param workingDirectory The working directory for the launch config.
 * @param stopInMain       If execution will pause in main when entered.
 * @param jreContainer     The specific JRE to use during execution. See {@link Keys#ATTR_JRE_CONTAINER_PATH}.
 * @param extraAttributes  Any additional {@link EAttribute} values not explicitly permitted by this class that may be of relevance to the launch config.
 */
public record JavaApplicationLaunchConfig(
    String project, String mainClass, @Nullable String moduleName,
    List<String> arguments, List<String> vmArguments, Map<String, String> envVars,
    @Nullable String workingDirectory, boolean stopInMain, @Nullable String jreContainer,
    List<EAttribute> extraAttributes, boolean useArgumentsFile) implements LaunchConfig {

    @Override
    public String getType() {
        return "org.eclipse.jdt.launching.localJavaApplication";
    }

    @Override
    public List<EAttribute> bakeAttributes() {
        List<EAttribute> attributes = new ArrayList<>();

        attributes.add(EAttribute.of(Keys.ATTR_PROJECT_NAME, this.project));

        attributes.add(EAttribute.of(Keys.ATTR_MAIN_TYPE_NAME, this.mainClass));

        if (this.moduleName != null) {
            attributes.add(EAttribute.of(Keys.ATTR_MODULE_NAME, this.project));
        }

        attributes.add(EAttribute.of(Keys.ATTR_PROGRAM_ARGUMENTS, argsListToStr(this.arguments)));

        attributes.add(EAttribute.of(Keys.ATTR_VM_ARGUMENTS, argsListToStr(this.vmArguments)));

        if (this.envVars.size() > 0) {
            Map<String, EValue<?>> copy = new HashMap<>();
            for (Map.Entry<String, String> entry : this.envVars.entrySet()) {
                copy.put(entry.getKey(), EValue.of(entry.getValue()));
            }
            attributes.add(EAttribute.of(Keys.ATTR_ENV_VARS, copy));
        }

        if (this.workingDirectory != null) {
            attributes.add(EAttribute.of(Keys.ATTR_WORKING_DIRECTORY, this.workingDirectory));
        }

        attributes.add(EAttribute.of(Keys.ATTR_STOP_IN_MAIN, this.stopInMain));

        if (this.jreContainer != null) {
            attributes.add(EAttribute.of(Keys.ATTR_JRE_CONTAINER_PATH, this.jreContainer));
        }
        
        attributes.add(EAttribute.of(Keys.ATTR_USE_ARGFILE, this.useArgumentsFile));

        attributes.addAll(extraAttributes);

        return attributes;
    }

    public static String argsListToStr(List<String> list) {
        return list.stream().collect(StringBuilder::new, (sb, str) -> sb.append(" ").append(str), (sb1, sb2) -> sb1.append(sb2.toString())).toString();
    }

    /**
     * Creates a new builder for a specific Eclipse project.
     * 
     * @param project The name of the Eclipse project the launch config is for.
     */
    public static Builder builder(String project) {
        return new Builder(project);
    }

    /**
     * Builder for {@link JavaApplicationLaunchConfig}.
     */
    public static final class Builder {

        private String project;

        @Nullable
        private String moduleName;

        List<String> arguments = new ArrayList<>();

        List<String> vmArguments = new ArrayList<>();

        Map<String, String> envVars = new HashMap<>();

        @Nullable
        String workingDirectory;

        boolean stopInMain;

        @Nullable
        String jreContainer;

        List<EAttribute> extraAttributes = new ArrayList<>();
        
        boolean useArgumentsFile = false;

        /**
         * Creates a new builder for a specific Eclipse project.
         * 
         * @param project The name of the Eclipse project the launch config is for.
         */
        public Builder(String project) {
            this.project = project;
        }

        /**
         * Changes the target Eclipse project.
         * 
         * @param project The name of the Eclipse project the launch config is for.
         * @return this
         */
        public Builder project(String project) {
            this.project = project;
            return this;
        }

        /**
         * Sets the launch config's module name.
         * 
         * @param moduleName The module name of the main type.
         * @return this
         */
        public Builder moduleName(@Nullable String moduleName) {
            this.moduleName = moduleName;
            return this;
        }

        /**
         * Adds one or more program argument to the launch config.
         * <p>
         * {@linkplain EclipseVariables eclipse variables} will be resolved in arguments.
         * 
         * @param args The argument(s) to add.
         * @return this
         */
        public Builder args(String... args) {
            for (String arg : args) this.arguments.add(arg);
            return this;
        }

        /**
         * Adds one or more JVM argument to the launch config.
         * <p>
         * {@linkplain EclipseVariables eclipse variables} will be resolved in arguments.
         * 
         * @param args The argument(s) to add.
         * @return this
         */
        public Builder vmArgs(String... args) {
            for (String arg : args) this.vmArguments.add(arg);
            return this;
        }

        /**
         * Adds an environment variable to the launch config.
         * <p>
         * Can use {@linkplain EclipseVariables eclipse variables} for value resolution, but not key resolution.
         * 
         * @param key   The key of the env var.
         * @param value The value of the env var.
         * @return this
         */
        public Builder envVar(String key, String value) {
            this.envVars.put(key, value);
            return this;
        }
        
        
        
        /**
         * Adds all environment variables to the launch config.
         * <p>
         * Can use {@linkplain EclipseVariables eclipse variables} for value resolution, but not key resolution.
         *
         * @param values The values and their keys to add.
         * @return this
         */
        public Builder envVar(Map<String, String> values) {
            this.envVars.putAll(values);
            return this;
        }

        /**
         * Sets the working directory.
         * <p>
         * Can use {@linkplain EclipseVariables eclipse variables} for path resolution.
         * 
         * @param workingDirectory The working directory.
         * @return this
         */
        public Builder workingDirectory(@Nullable String workingDirectory) {
            this.workingDirectory = workingDirectory;
            return this;
        }

        /**
         * Sets the stop in main flag.
         * 
         * @param stopInMain The stop in main flag value.
         * @return this
         */
        public Builder stopInMain(boolean stopInMain) {
            this.stopInMain = stopInMain;
            return this;
        }

        /**
         * Sets the JRE that will be used during execution.
         * 
         * @param jreContainer The target JRE.
         * @return this
         * @see Keys#ATTR_JRE_CONTAINER_PATH
         */
        public Builder jreContainer(@Nullable String jreContainer) {
            this.jreContainer = jreContainer;
            return this;
        }

        /**
         * Adds an unspecified attribute to the builder.
         * 
         * @param attr The attribute being added.
         * @return this
         */
        public Builder extraAttribute(EAttribute attr) {
            this.extraAttributes.add(attr);
            return this;
        }
        
        /**
         * Sets whether eclipse should launch the configuration with an argument file preventing command line length issues.
         *
         * @param useArgumentsFile Whether to use an argument file.
         * @return this
         */
        public Builder useArgumentsFile(boolean useArgumentsFile) {
            this.useArgumentsFile = useArgumentsFile;
            return this;
        }
        
        /**
         * Sets eclipse to use an argument file to prevent command line length issues.
         *
         * @return this
         */
        public Builder useArgumentsFile() {
            return useArgumentsFile(true);
        }

        /**
         * Builds a {@link JavaApplicationLaunchConfig} targetting a specific main class.
         * <p>
         * All containers (lists, maps) are copied, so further modification to this builder will not modify the built object.
         * 
         * @param mainClass The main class to launch.
         * @return A newly-constructed {@link JavaApplicationLaunchConfig}.
         */
        public JavaApplicationLaunchConfig build(String mainClass) {
            return new JavaApplicationLaunchConfig(this.project, mainClass, this.moduleName, Util.copyOf(this.arguments), Util.copyOf(this.vmArguments), Util.copyOf(this.envVars), this.workingDirectory, this.stopInMain,
                this.jreContainer, Util.copyOf(this.extraAttributes), useArgumentsFile);
        }
    }

    /**
     * Holds keys relevant to serialization of {@link JavaApplicationLaunchConfig}.
     */
    public static class Keys {

        /**
         * ID of the Eclipse JDT Launching plugin, which is prefixed to all keys of this launch configuration.
         */
        public static final String LAUNCHING_PLUGIN_ID = "org.eclipse.jdt.launching";

        /**
         * Type ID for {@link JavaApplicationLaunchConfig}.
         */
        public static final String TYPE = LAUNCHING_PLUGIN_ID + ".localJavaApplication";

        /**
         * Defines the Eclipse project name associated with this launch configuration. Must match the name of a project in the current Eclipse workspace.
         * <p>
         * E-Attribute Type: String
         * <br>
         * Required: True
         */
        public static final String ATTR_PROJECT_NAME = LAUNCHING_PLUGIN_ID + ".PROJECT_ATTR";

        /**
         * Defines the fully-qualified class name of the main class to launch when starting the application.
         * <p>
         * E-Attribute Type: String
         * <br>
         * Required: True
         */
        public static final String ATTR_MAIN_TYPE_NAME = LAUNCHING_PLUGIN_ID + ".MAIN_TYPE";

        /**
         * Defines the module name for the main type to launch.
         * <p>
         * E-Attribute Type: String
         * <br>
         * Required: False
         * <br>
         * Default Value: The value of {@link #ATTR_PROJECT_NAME}.
         */
        public static final String ATTR_MODULE_NAME = LAUNCHING_PLUGIN_ID + ".MODULE_NAME";

        /**
         * If true, execution will stop in main when entered, awaiting a debugger command to continue execution.
         * <p>
         * E-Attribute type: Boolean
         * <br>
         * Required: False
         * <br>
         * Default Value: False
         */
        public static final String ATTR_STOP_IN_MAIN = LAUNCHING_PLUGIN_ID + ".STOP_IN_MAIN";

        /**
         * Defines all arguments that will be passed to the program as they would appear on the command line.
         * <p>
         * E-Attribute type: String
         * <br>
         * Required: False
         * <br>
         * Default Value: ""
         */
        public static final String ATTR_PROGRAM_ARGUMENTS = LAUNCHING_PLUGIN_ID + ".PROGRAM_ARGUMENTS";

        /**
         * Defines all arguments that will be passed to the JVM as they would appear on the command line.
         * <p>
         * E-Attribute type: String
         * <br>
         * Required: False
         * <br>
         * Default Value: ""
         */
        public static final String ATTR_VM_ARGUMENTS = LAUNCHING_PLUGIN_ID + ".VM_ARGUMENTS";

        /**
         * Defines the working directory of the launch configuration. May be an absolute or relative file path, and may use eclipse variables in its
         * definition.
         * <p>
         * When specified as an absolute path, the path represents a path in the local
         * file system. When specified as a full path, the path represents a workspace
         * relative path. When unspecified, the directory of the Eclipse project specified by {@link #ATTR_PROJECT_NAME} is used.
         * <p>
         * E-Attribute type: String
         * <br>
         * Required: False
         * <br>
         * Default Value: "${workspace_loc:ProjectName}" where ProjectName is the value specified in by {@link #ATTR_PROJECT_NAME}. This value corresponds to the
         * directory of that project.
         */
        public static final String ATTR_WORKING_DIRECTORY = LAUNCHING_PLUGIN_ID + ".WORKING_DIRECTORY";

        /**
         * Defines the JRE to use when executing the application. The definition must take the following three-part format, each part separated by a forward slash:
         * <ol>
         * <li>The header, which is "org.eclipse.jdt.launching.JRE_CONTAINER"</li>
         * <li>The VM type, which is one of "org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType" or "org.eclipse.jdt.launching.EEVMType"</li>
         * <li>The VM name, which is a user-specified value for a JVM in Eclipse, such as "jdk-17.0.5.8-hotspot"</li>
         * </ol>
         * For example, a full declaration may look like
         * "org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/jdk-17.0.5.8-hotspot"
         * <p>
         * E-Attribute type: String
         * <br>
         * Required: False
         * <br>
         * Default Value: The default JVM of the Eclipse project specified by {@link #ATTR_PROJECT_NAME}.
         */
        public static final String ATTR_JRE_CONTAINER_PATH = LAUNCHING_PLUGIN_ID + ".JRE_CONTAINER";

        /**
         * Defines environment variables that will be set when executing the configuration.
         * <p>
         * E-Attribute type: Map
         * <br>
         * Required: False
         * <br>
         * Default Value: Empty Map
         */
        public static final String ATTR_ENV_VARS = "org.eclipse.debug.core.environmentVariables";
        
        /**
         * Indicates whether eclipse should launch the configuration with an argument file preventing command line length issues.
         * <p>
         * E-Attribute type: Boolean
         * <br>
         * Required: False
         * <br>
         * Default Value: False
         */
        public static final String ATTR_USE_ARGFILE = LAUNCHING_PLUGIN_ID + ".ATTR_ATTR_USE_ARGFILE";
    }
}
