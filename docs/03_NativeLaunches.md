# Eclipse Native Launches
## Description
This document covers the `launchConfiguration` types declared under the `org.eclipse.jdt.launching` package. The information found here was derived from [IJavaLaunchConfigurationConstants](https://github.com/eclipse-jdt/eclipse.jdt.debug/blob/master/org.eclipse.jdt.launching/launching/org/eclipse/jdt/launching/IJavaLaunchConfigurationConstants.java).

The following types are covered:
* `org.eclipse.jdt.launching.localJavaApplication`
  * Used when launching a Java Application (via main class) locally.
* `org.eclipse.jdt.launching.remoteJavaApplication`
  * Used when connecting to a remote JVM via socket for remote debugging.
* `org.eclipse.jdt.launching.javaApplet`
  * Used when launching a [Java Applet](https://www.tutorialspoint.com/java/java_applet_basics.htm) in an applet viewer.

These types form the core set of basic launch configurations that are native to eclipse when working with java programs.  
The sections below detail how to construct a launch configuration of each type.

## Local Java Application
### Description
This section details the E-Attributes relevant to the `org.eclipse.jdt.launching.localJavaApplication` launch configuration type.  
### Supported Keys
* `org.eclipse.jdt.launching.PROJECT_ATTR`
  * E-Attribute type: `stringAttribute`
  * Usage: Declares the Eclipse Project Name associated with this launch configuration. Must match the name of a project in the current Eclipse workspace.
  * Required: True
* `org.eclipse.jdt.launching.MAIN_TYPE`
  * E-Attribute type: `stringAttribute`
  * Usage: Declares the fully-qualified class name of the main class to launch when starting the application.
  * Required: True
* `org.eclipse.jdt.launching.MODULE_NAME`
  * E-Attribute type: `stringAttribute`
  * Usage: The value is the module name for the main type to launch.
  * Required: False
  * Default Value: The value of `org.eclipse.jdt.launching.PROJECT_ATTR`.
  * Note: Not Verified, was not used in FG6 runs and may be unused or unnecessary. In testing, this uses the same value as `org.eclipse.jdt.launching.PROJECT_ATTR`.
* `org.eclipse.jdt.launching.PROGRAM_ARGUMENTS`
  * E-Attribute type: `stringAttribute`
  * Usage: A string specifying all arguments that will be passed to the program as they would appear on the command line.
  * Required: False
  * Default Value: `""`
* `org.eclipse.jdt.launching.VM_ARGUMENTS`
  * E-Attribute type: `stringAttribute`
  * Usage: A string specifying all arguments that will be passed to the JVM as they would appear on the command line.
  * Required: False
  * Default Value: `""`
* `org.eclipse.jdt.launching.WORKING_DIRECTORY`
  * E-Attribute type: `stringAttribute`
  * Usage: A string specifying the working directory of the launch configuration. May be either an absolute file path, or use eclipse variables (or any combination thereof).
  * Required: False
  * Default Value: `"${workspace_loc:ProjectName}"` where `ProjectName` is the value specified in `org.eclipse.jdt.launching.PROJECT_ATTR`.
  * Note: FG6 specified the full file path, but it is also acceptable to specify `"${workspace_loc:ProjectName}/run"` instead. The forward slash works on windows.
* `org.eclipse.debug.core.environmentVariables`
  * E-Attribute type: `mapAttribute`
  * Usage: A string->string map of environment variables that will be set when executing the configuration.
  * Required: False
  * Default Value: Empty map
  * Note: FG6 used this to set `MCP_MAPPINGS` and `MOD_CLASSES`.
* `org.eclipse.jdt.launching.STOP_IN_MAIN`
  * E-Attribute type: `booleanAttribute`
  * Usage: If true, execution will stop in main when entered, awaiting a debugger command to continue execution.
  * Required: False
  * Default Value: `"false"`
* `org.eclipse.jdt.launching.JRE_CONTAINER`
  * E-Attribute type: `stringAttribute`
  * Usage: Specifies the JRE to use when executing the application. Must be in some weirdly specific form that I do not know the specifics of.
  * Examples:
    * `value="org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/jdk-17.0.5.8-hotspot/"`
  * Required: False
  * Default Value: Either the JVM of the Project, or the Workspace if the Project does not specify a JVM.
* `org.eclipse.jdt.launching.CLASSPATH`
  * E-Attribute type: `listAttribute`
  * Usage: "An ordered list of strings which are mementos for runtime class path entries."
  * Required: False
  * Default Value: "A default classpath is generated by the classpath provider associated with this launch configuration."
  * Note: Format Unknown
* `org.eclipse.jdt.launching.MODULEPATH`
  * E-Attribute type: `listAttribute`
  * Usage: "An ordered list of strings which are mementos for runtime module path entries."
  * Required: False
  * Default Value: "A default modulepath is generated by the dependency provider associated with this launch configuration."
  * Note: Format Unknown
### Examples
```xml
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<launchConfiguration type="org.eclipse.jdt.launching.localJavaApplication">
  <stringAttribute key="org.eclipse.jdt.launching.PROJECT_ATTR" value="MyProjectName"/>
  <stringAttribute key="org.eclipse.jdt.launching.MODULE_NAME" value="MyProjectName"/>
  <stringAttribute key="org.eclipse.jdt.launching.MAIN_TYPE" value="dev.shadowsoffire.test.MyProjectName"/>
  <stringAttribute key="org.eclipse.jdt.launching.VM_ARGUMENTS" value="-Xmx512M"/>
  <stringAttribute key="org.eclipse.jdt.launching.PROGRAM_ARGUMENTS" value="--testArgument testArgValue"/>
  <stringAttribute key="org.eclipse.jdt.launching.WORKING_DIRECTORY" value="${workspace_loc:MyProjectName}/run"/>
  <mapAttribute key="org.eclipse.debug.core.environmentVariables">
    <mapEntry key="TEST_VAR" value="test_value"/>
  </mapAttribute>
</launchConfiguration>
```