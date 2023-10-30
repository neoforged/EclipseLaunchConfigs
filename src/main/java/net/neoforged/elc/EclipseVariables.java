package net.neoforged.elc;

import java.text.SimpleDateFormat;

import org.jetbrains.annotations.Nullable;

/**
 * Eclipse Variables are special symbols that can be used in the resolution of certain launch config parameters.
 * <p>
 * Variables have two components: the variable name, and the parameter. The parameter is not required for all variables.
 * <p>
 * The proper form for a variable is ${var_name:parameter}, or ${var_name} if a parameter is not necessary.
 * <p>
 * Many methods in this class refer to the concept of an Eclipse <code>resource</code>, which is an object known to Eclipse, such as a project name.
 * <p>
 * Finally, the javadocs of methods in this class specify that they return a certain value. This value is the value the variable will return when evaluated.
 * The literal return value of these methods is an Eclipse Variable definition.
 */
public class EclipseVariables {

    /**
     * Returns the set of absolute file system paths whose modification caused the current build. A list of the characters, 'a' (added), 'c' (changed), 'r'
     * (removed), 'f' (files only), 'd' (directories only), can be supplied as an argument to limit the file list to just those types of deltas. Defaults to all
     * deltas.
     *
     * @param deltas A string of deltas for limiting the listed files.
     */
    public static String buildFiles(@Nullable String deltas) {
        return variable("build_files", deltas);
    }

    /**
     * Returns the absolute file system path of the project currently being built, or the absolute file system path of the
     * resource argument interpreted as a path relative to the project currently being built.
     *
     * @param resource A path relative to the project currently being built, specifying a specific resource.
     */
    public static String buildProject(@Nullable String resource) {
        return variable("build_project");
    }

    /**
     * Returns the type of build being performed. One of "incremental", "full", "auto", or "none".
     */
    public static String buildType() {
        return variable("build_type");
    }

    /**
     * Returns the absolute file system path of a resource's container.
     * <p>
     * The target resource is the selected resource when unspecified, or the resource identified by a workspace relative path.
     *
     * @param resource The target resource, or null to use the selected resource.
     */
    public static String containerLocation(@Nullable String resource) {
        return variable("container_loc", resource);
    }

    /**
     * Returns the name of a resource's container.
     * <p>
     * The target resource is the selected resource when unspecified, or the resource identified by a workspace relative path.
     *
     * @param resource The target resource, or null to use the selected resource.
     */
    public static String containerName(@Nullable String resource) {
        return variable("container_name", resource);
    }

    /**
     * Returns the workspace relative path of a resource's container.
     * <p>
     * The target resource is the selected resource when unspecified, or the resource identified by a workspace relative path.
     *
     * @param resource The target resource, or null to use the selected resource.
     */
    public static String containerPath(@Nullable String resource) {
        return variable("container_path", resource);
    }

    /**
     * Returns the current system time formatted as <code>yyyyMMdd_HHmm</code>.
     *
     * @param dateFormat An optional {@link SimpleDateFormat} pattern.
     */
    public static String currentDate(@Nullable String dateFormat) {
        return variable("current_date", dateFormat);
    }

    /**
     * Returns the location of the base installation for the running platform.
     */
    public static String eclipseHome() {
        return variable("eclipse_home");
    }

    /**
     * Returns the absolute file system path of the JRE home directory corresponding to the specified execution environment.
     *
     * @param id An execution environment identifier, such as <code>JavaSE-1.8</code> or <code>JavaSE-17</code>.
     */
    public static String eeHome(String id) {
        return variable("ee_home", id);
    }

    /**
     * Returns the value of an environment variable.
     *
     * @param name The name of the environment variable.
     */
    public static String envVar(String name) {
        return variable("env_var", name);
    }

    /**
     * Returns the absolute file system path of a file chosen in a file selection dialog.
     * <p>
     * If the second argument is provided, the first must be provided.
     *
     * @param hint       A prompt hint on the dialog title.
     * @param initialDir The initial path in the selection dialog.
     */
    public static String filePrompt(@Nullable String hint, @Nullable String initialDir) {
        return variable("file_prompt", hint, initialDir);
    }

    /**
     * Returns the absolute file system path of a directory chosen in a directory selection dialog.
     * <p>
     * If the second argument is provided, the first must be provided.
     *
     * @param hint       A prompt hint on the dialog title.
     * @param initialDir The initial path in the selection dialog.
     */
    public static String folderPrompt(@Nullable String hint, @Nullable String initialDir) {
        return variable("folder_prompt", hint, initialDir);
    }

    /**
     * Returns the current HEAD in the Git repository that contains the selected or referenced resource
     * <p>
     * The target resource is the selected resource when unspecified, or the resource identified by a workspace relative path.
     *
     * @param resource The target resource, or null to use the selected resource.
     */
    public static String gitBranch(@Nullable String resource) {
        return variable("git_branch", resource);
    }

    /**
     * Returns the path to the .git directory for the selected or named resource
     * <p>
     * The target resource is the selected resource when unspecified, or the resource identified by a workspace relative path.
     *
     * @param resource The target resource, or null to use the selected resource.
     */
    public static String gitDir(@Nullable String resource) {
        return variable("git_dir", resource);
    }

    /**
     * Returns the path of a resource relative to the Git repository
     * <p>
     * The target resource is the selected resource when unspecified, or the resource identified by a workspace relative path.
     *
     * @param resource The target resource, or null to use the selected resource.
     */
    public static String gitRepoRelativePath(@Nullable String resource) {
        return variable("git_repo_relative_path", resource);
    }

    /**
     * Returns the work tree root of the Git repository for a resource.
     * <p>
     * The target resource is the selected resource when unspecified, or the resource identified by a workspace relative path.
     *
     * @param resource The target resource, or null to use the selected resource.
     */
    public static String gitWorkTree(@Nullable String resource) {
        return variable("git_work_tree", resource);
    }

    /**
     * Returns a regular expression matching registered Java-like file extensions
     */
    public static String javaExtRegex() {
        return variable("java_extensions_regex");
    }

    /**
     * Returns the fully qualified Java type name of the primary type in the selected resource.
     */
    public static String javaTypeName() {
        return variable("java_type_name");
    }

    /**
     * Returns the text value entered into a password prompt dialog.
     * <p>
     * If the second argument is provided, the first must be provided.
     *
     * @param hint    A prompt hint on the input dialog.
     * @param initial The initial value in the input dialog.
     */
    public static String passwordPrompt(@Nullable String hint, @Nullable String initial) {
        return variable("password_prompt", hint, initial);
    }

    /**
     * Returns the absolute file system path of a resource's project.
     * <p>
     * The target resource is the selected resource when unspecified, or the resource identified by a workspace relative path.
     *
     * @param resource The target resource, or null to use the selected resource.
     */
    public static String projectLocation(@Nullable String resource) {
        return variable("project_loc", resource);
    }

    /**
     * Returns the name of a resource's project.
     * <p>
     * The target resource is the selected resource when unspecified, or the resource identified by a workspace relative path.
     *
     * @param resource The target resource, or null to use the selected resource.
     */
    public static String projectName(@Nullable String resource) {
        return variable("project_name", resource);
    }

    /**
     * Returns the workspace relative path of a resource's project.
     * <p>
     * The target resource is the selected resource when unspecified, or the resource identified by a workspace relative path.
     *
     * @param resource The target resource, or null to use the selected resource.
     */
    public static String projectPath(@Nullable String resource) {
        return variable("project_path", resource);
    }

    /**
     * Returns the absolute file system path of a resource.
     * <p>
     * The target resource is the selected resource when unspecified, or the resource identified by a workspace relative path.
     *
     * @param resource The target resource, or null to use the selected resource.
     */
    public static String resourceLocation(@Nullable String resource) {
        return variable("resource_loc", resource);
    }

    /**
     * Returns the name of a resource.
     * <p>
     * The target resource is the selected resource when unspecified, or the resource identified by a workspace relative path.
     *
     * @param resource The target resource, or null to use the selected resource.
     */
    public static String resourceName(@Nullable String resource) {
        return variable("resource_name", resource);
    }

    /**
     * Returns the workspace relative path of a resource.
     * <p>
     * The target resource is the selected resource when unspecified, or the resource identified by a workspace relative path.
     *
     * @param resource The target resource, or null to use the selected resource.
     */
    public static String resourcePath(@Nullable String resource) {
        return variable("resource_path", resource);
    }

    /**
     * Returns the absolute file system path of the selected resource.
     */
    public static String selectedResourceLocation() {
        return variable("selected_resource_loc");
    }

    /**
     * Returns the name of the selected resource.
     */
    public static String selectedResourceName() {
        return variable("selected_resource_name");
    }

    /**
     * Returns the workspace relative path of the selected resource.
     */
    public static String selectedResourcePath() {
        return variable("selected_resource_path");
    }

    /**
     * Returns the text currently selected in the active editor.
     */
    public static String selectedText() {
        return variable("selected_text");
    }

    /**
     * Returns the text value entered into a prompt dialog.
     * <p>
     * If the second argument is provided, the first must be provided.
     *
     * @param hint    A prompt hint on the input dialog.
     * @param initial The initial value in the input dialog.
     */
    public static String prompt(@Nullable String hint, @Nullable String initial) {
        return variable("string_prompt", hint, initial);
    }

    /**
     * Returns the value of an Eclipse system variable.
     *
     * @param varName The variable name. One of: ARCH, ECLIPSE_HOME, NL, OS, or WS.
     */
    public static String systemVar(String varName) {
        return variable("system", varName);
    }

    /**
     * Returns the absolute file system path of the external tool.
     * <p>
     * Resolved by finding the first occurrence of the named tool based on the system path specification
     *
     * @param tool The name of the tool.
     */
    public static String systemPath(String tool) {
        return variable("system_path", tool);
    }

    /**
     * Returns the value of a system property from the Eclipse runtime.
     *
     * @param property The property name.
     */
    public static String systemProperty(String property) {
        return variable("system_property", property);
    }

    /**
     * Returns the absolute file system path of the workspace root.
     * <p>
     * If a resource is specified, the absolute file system path of the resource identified by a workspace relative path is returned.
     *
     * @param resource The target resource, or null if one is not being targetted.
     */
    public static String workspaceLocation(@Nullable String resource) {
        return variable("workspace_loc", resource);
    }

    private static String variable(String name) {
        return variable(name, null);
    }

    private static String variable(String name, @Nullable String param) {
        return variable(name, param, null);
    }

    private static String variable(String name, @Nullable String param, @Nullable String param2) {
        if (param2 != null) {
            if (param == null)
                throw new UnsupportedOperationException("A second parameter may not be provided if the first one is not provided.");
            return String.format("${%s:%s:%s}", name, param, param2);
        } else if (param != null) return String.format("${%s:%s}", name, param);
        else return String.format("${%s}", name);
    }
}
