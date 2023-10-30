package net.neoforged.elc.configs;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jetbrains.annotations.Nullable;

import com.github.bsideup.jabel.Desugar;

import net.neoforged.elc.EclipseVariables;
import net.neoforged.elc.attributes.EAttribute;
import net.neoforged.elc.attributes.EValue;
import net.neoforged.elc.configs.LaunchGroup.Mode;
import net.neoforged.elc.util.Util;

/**
 * This class implements the <code>org.eclipse.buildship.core.launch.runconfiguration</code> launch configuration type.
 * <p>
 * It is used for launching one or more gradle tasks.
 * <p>
 * When executing this via {@link LaunchGroup}, it must always be run in {@link Mode#RUN}.
 * 
 * @param tasks              An ordered list of gradle tasks that will be executed by this config.
 * @param workingDirectory   The working directory for the launch config.
 * @param gradleDistribution The gradle distribution to use.
 * @param offlineMode        If offline mode is enabled or not.
 * @param showConsoleView    If the eclipse console will be shown when execution begins.
 * @param showExecutionView  If the eclipse "Gradle Executions" window will be shown when execution begins.
 * @param gradleUserHome     The <a href="https://docs.gradle.org/current/userguide/directory_layout.html#dir:gradle_user_home">gradle user home</a>.
 * @param javaHome           The <a href="https://docs.gradle.org/current/userguide/build_environment.html">java home</a> used by gradle.
 * @param arguments          Command-line arguments passed to the program (gradle, in this case) before execution.
 * @param jvmArguments       Command-line arguments passed to the JVM before startup.
 * @param overrideWorkspace  If the workspace settings are overridden. If false, most of the settings here are ignored. See {@link Keys#OVERRIDE_WORKSPACE}.
 */
@Desugar
public record GradleLaunchConfig(
    List<String> tasks, String workingDirectory, String gradleDistribution,
    boolean offlineMode, boolean showConsoleView, boolean showExecutionView,
    @Nullable String gradleUserHome, @Nullable String javaHome, List<String> arguments,
    List<String> jvmArguments, boolean overrideWorkspace) implements LaunchConfig {

    @Override
    public String getType() {
        return "org.eclipse.buildship.core.launch.runconfiguration";
    }

    @Override
    public List<EAttribute> bakeAttributes() {
        List<EAttribute> attributes = new ArrayList<>();

        attributes.add(EAttribute.of(Keys.TASKS, this.tasks.stream().<EValue<?>>map(EValue::of).collect(Collectors.toList())));

        if (!this.workingDirectory.isEmpty()) { // The working directory must be omitted if empty, as empty is invalid.
            attributes.add(EAttribute.of(Keys.WORKING_DIR, this.workingDirectory));
        }

        attributes.add(EAttribute.of(Keys.GRADLE_DIST, this.gradleDistribution));
        attributes.add(EAttribute.of(Keys.OFFLINE_MODE, this.offlineMode));
        attributes.add(EAttribute.of(Keys.SHOW_CONSOLE, this.showConsoleView));
        attributes.add(EAttribute.of(Keys.SHOW_EXECUTIONS, this.showExecutionView));

        if (this.gradleUserHome != null) {
            attributes.add(EAttribute.of(Keys.GRADLE_USER_HOME, this.gradleUserHome));
        }

        if (this.javaHome != null) {
            attributes.add(EAttribute.of(Keys.JAVA_HOME, this.javaHome));
        }

        attributes.add(EAttribute.of(Keys.ARGUMENTS, this.arguments.stream().<EValue<?>>map(EValue::of).collect(Collectors.toList())));
        attributes.add(EAttribute.of(Keys.JVM_ARGS, this.jvmArguments.stream().<EValue<?>>map(EValue::of).collect(Collectors.toList())));
        attributes.add(EAttribute.of(Keys.OVERRIDE_WORKSPACE, this.overrideWorkspace));

        return attributes;
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
     * Builder for {@link GradleLaunchConfig}.
     */
    public static final class Builder {

        private String project;

        private final List<String> tasks = new ArrayList<>();

        private String workingDirectory = "";

        private String gradleDistribution = "GRADLE_DISTRIBUTION(WRAPPER)";

        private boolean offlineMode = false;

        private boolean showConsoleView = true;

        private boolean showExecutionView = true;

        @Nullable
        private String gradleUserHome;

        @Nullable
        private String javaHome;

        private final List<String> arguments = new ArrayList<>();

        private final List<String> jvmArguments = new ArrayList<>();

        private boolean overrideWorkspace = false;

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
         * <p>
         * For {@link GradleLaunchConfig}, the project is only used to infer the default {@link #workingDirectory}.
         * 
         * @param project The name of the Eclipse project the launch config is for.
         * @return this
         */
        public Builder project(String project) {
            this.project = project;
            return this;
        }

        /**
         * Adds one or more gradle tasks to the end of the execution queue.
         * 
         * @param tasks The new tasks, in execution order.
         * @return this
         */
        public Builder tasks(String... tasks) {
            for (String task : tasks) this.tasks.add(task);
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
        public Builder workingDirectory(String workingDirectory) {
            this.workingDirectory = workingDirectory;
            return this;
        }

        /**
         * Sets the Gradle Distribution to use. Specify the gradle wrapper via <code>"GRADLE_DISTRIBUTION(WRAPPER)"</code>.
         * 
         * @param gradleDist The new gradle distribution.
         * @return this
         */
        public Builder gradleDist(String gradleDist) {
            this.gradleDistribution = gradleDist;
            return overrideWorkspaceSettings(true);
        }

        /**
         * Sets the offline mode flag. If enabled, <code>--offline</code> will be passed to gradle at execution time.
         * 
         * @param offlineMode If offline mode is enabled.
         * @return this
         */
        public Builder offlineMode(boolean offlineMode) {
            this.offlineMode = offlineMode;
            return overrideWorkspaceSettings(true);
        }

        /**
         * Sets the console view flag. If enabled, the Eclipse console will be shown when running the config.
         * 
         * @param showConsole If show console view is enabled.
         * @return this
         */
        public Builder showConsole(boolean showConsole) {
            this.showConsoleView = showConsole;
            return overrideWorkspaceSettings(true);
        }

        /**
         * Sets the executions view flag. If enabled, the Eclipse "Gradle Executions" pane will be shown when running the config.
         * 
         * @param showExecutions If show execution view is enabled.
         * @return this
         */
        public Builder showExecutions(boolean showExecutions) {
            this.showExecutionView = showExecutions;
            return overrideWorkspaceSettings(true);
        }

        /**
         * Sets the <a href="https://docs.gradle.org/current/userguide/directory_layout.html#dir:gradle_user_home">Gradle user home</a>.
         * 
         * @param gradleUserHome The new gradle user home.
         * @return this
         */
        public Builder gradleUserHome(@Nullable String gradleUserHome) {
            this.gradleUserHome = gradleUserHome;
            return overrideWorkspaceSettings(true);
        }

        /**
         * Sets the <a href="https://docs.gradle.org/current/userguide/build_environment.html">java home</a> used by Gradle.
         * 
         * @param javaHome The new Java home.
         * @return this
         */
        public Builder javaHome(@Nullable String javaHome) {
            this.javaHome = javaHome;
            return overrideWorkspaceSettings(true);
        }

        /**
         * Adds one or more program argument to the launch config.
         * <p>
         * Supplying an incorrect or invalid argument will cause the launch config to emit nothing and silently fail.
         * <p>
         * Supplying <code>--no-daemon</code> is not supported and causes the launch to fail similarly.
         * <p>
         * {@linkplain EclipseVariables eclipse variables} will be resolved in arguments.
         * 
         * @param args The argument(s) to add.
         * @return this
         */
        public Builder args(String... args) {
            for (String arg : args) this.arguments.add(arg);
            return overrideWorkspaceSettings(true);
        }

        /**
         * Adds a <a href="https://docs.gradle.org/current/userguide/build_environment.html#sec:project_properties">Project Property</a> to the launch args.
         * <p>
         * This is syntax sugar around {@link #args(String...)}.
         * <p>
         * {@linkplain EclipseVariables eclipse variables} will be resolved in arguments.
         * 
         * @param key   The property key.
         * @param value The property value. It will be stringized via {@link Object#toString()}
         * @return this
         */
        public Builder property(String key, Object value) {
            return args("-P" + key + "=" + value.toString());
        }

        /**
         * Adds one or more JVM argument to the launch config.
         * <p>
         * {@linkplain EclipseVariables eclipse variables} will be resolved in arguments.
         * 
         * @param args The argument(s) to add.
         * @return this
         */
        public Builder jvmArgs(String... args) {
            for (String arg : args) this.jvmArguments.add(arg);
            return overrideWorkspaceSettings(true);
        }

        /**
         * Sets the "Override Workspace Settings" flag. If disabled, only {@link #tasks} and {@link #workingDirectory} will be used during execution.
         * <p>
         * Since this field defaults to <code>false</code>, it will be automatically enabled if any
         * of the dependent settings are modified, and must be manually disabled at the end of the builder.
         * 
         * @param override If workspace settings will be overridden.
         * @return this
         * @see Keys#OVERRIDE_WORKSPACE
         */
        public Builder overrideWorkspaceSettings(boolean override) {
            this.overrideWorkspace = override;
            return this;
        }

        /**
         * Builds a {@link GradleLaunchConfig}.
         * <p>
         * All containers (lists, maps) are copied, so further modification to this builder will not modify the built object.
         * 
         * @return A newly-constructed {@link GradleLaunchConfig}.
         */
        public GradleLaunchConfig build() {
            String workingDir = this.workingDirectory;
            if (workingDir.isEmpty()) workingDir = "${workspace_loc:" + this.project + "}";

            return new GradleLaunchConfig(Util.copyOf(this.tasks), workingDir, this.gradleDistribution, this.offlineMode, this.showConsoleView, this.showExecutionView, this.gradleUserHome, this.javaHome,
                Util.copyOf(this.arguments), Util.copyOf(this.jvmArguments), this.overrideWorkspace);
        }

    }

    /**
     * Holds keys relevant to serialization of {@link GradleLaunchConfig}.
     */
    public static class Keys {

        /**
         * Defines the ordered list of gradle tasks that will be run by the launch configuration.
         * Task names must be
         * <a href="https://docs.gradle.org/current/userguide/intro_multi_project_builds.html#sec:executing_tasks_by_fully_qualified_name">fully-qualified names</a>.
         * <p>
         * E-Attribute type: List
         * <br>
         * Required: True
         */
        public static final String TASKS = "tasks";

        /**
         * Defines the working directory of the launch configuration. May be an absolute or relative file path, and may use eclipse variables in its
         * definition.
         * <p>
         * When specified as an absolute path, the path represents a path in the local
         * file system. When specified as a full path, the path represents a workspace
         * relative path. When unspecified, the directory of the Eclipse project specified by {@link net.neoforged.elc.configs.JavaApplicationLaunchConfig.Keys#ATTR_PROJECT_NAME} is used.
         * <p>
         * E-Attribute type: String
         * <br>
         * Required: False
         * <br>
         * Default Value: ""
         */
        public static final String WORKING_DIR = "working_dir";

        /**
         * Defines the gradle distribution to use. The local gradle wrapper may be specified via <code>"GRADLE_DISTRIBUTION(WRAPPER)"</code>.
         * <p>
         * E-Attribute type: String
         * <br>
         * Required: False
         * <br>
         * Default Value: "GRADLE_DISTRIBUTION(WRAPPER)"
         */
        public static final String GRADLE_DIST = "gradle_distribution";

        /**
         * Defines if offline mode is enabled or not. Equivalent to passing <code>--offline</code> to gradle during task execution.
         * <p>
         * E-Attribute type: Boolean
         * <br>
         * Required: False
         * <br>
         * Default Value: False
         */
        public static final String OFFLINE_MODE = "offline_mode";

        /**
         * Defines if the Eclipse console will be shown when execution begins.
         * <p>
         * E-Attribute type: Boolean
         * <br>
         * Required: False
         * <br>
         * Default Value: True
         */
        public static final String SHOW_CONSOLE = "show_console_view";

        /**
         * Defines if the Eclipse "Gradle Executions" pane will be shown when execution begins.
         * <p>
         * E-Attribute type: Boolean
         * <br>
         * Required: False
         * <br>
         * Default Value: True
         */
        public static final String SHOW_EXECUTIONS = "show_execution_view";

        /**
         * Defines an override for the <a href="https://docs.gradle.org/current/userguide/directory_layout.html#dir:gradle_user_home">Gradle user home</a>.
         * <p>
         * Can use Eclipse Variables in the same manner as {@link #WORKING_DIR}.
         * <p>
         * E-Attribute type: String
         * <br>
         * Required: False
         * <br>
         * Default Value: <code>null</code>
         */
        public static final String GRADLE_USER_HOME = "gradle_user_home";

        /**
         * Defines an override for the <a href="https://docs.gradle.org/current/userguide/build_environment.html">java home</a> used by Gradle.
         * <p>
         * Can use Eclipse Variables in the same manner as {@link #WORKING_DIR}.
         * <p>
         * E-Attribute type: String
         * <br>
         * Required: False
         * <br>
         * Default Value: <code>null</code>
         */
        public static final String JAVA_HOME = "java_home";

        /**
         * Defines a list of strings specifying all arguments that will be passed to the program (Gradle). One argument per entry.
         * <p>
         * E-Attribute type: List
         * <br>
         * Required: False
         * <br>
         * Default Value: Empty List
         */
        public static final String ARGUMENTS = "arguments";

        /**
         * Defines a list of strings specifying all arguments that will be passed to the JVM. One argument per entry.
         * <p>
         * E-Attribute type: List
         * <br>
         * Required: False
         * <br>
         * Default Value: Empty List
         */
        public static final String JVM_ARGS = "jvm_arguments";

        /**
         * Defines if the "Override Workspace Settings" feature is enabled.
         * <p>
         * This feature must be enabled for the following values to be read from the config:
         * <ul>
         * <li>{@link Keys#GRADLE_DIST}</li>
         * <li>{@link Keys#GRADLE_USER_HOME}</li>
         * <li>{@link Keys#JAVA_HOME}</li>
         * <li>{@link Keys#ARGUMENTS}</li>
         * <li>{@link Keys#JVM_ARGS}</li>
         * <li>{@link Keys#OFFLINE_MODE}</li>
         * <li>{@link Keys#SHOW_CONSOLE}</li>
         * <li>{@link Keys#SHOW_EXECUTIONS}</li>
         * </ul>
         * <p>
         * E-Attribute type: Boolean
         * <br>
         * Required: False
         * <br>
         * Default Value: False
         */
        public static final String OVERRIDE_WORKSPACE = "override_workspace_settings";

    }
}
