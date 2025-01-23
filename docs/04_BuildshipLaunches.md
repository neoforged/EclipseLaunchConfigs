# Buildship Launches
## Description
This document covers the `launchConfiguration` types declared under the `org.eclipse.buildship` package. They are provided by the [buildship plugin](https://github.com/eclipse/buildship/tree/master). The information found here was derived from the following sources:
* [BaseRunConfigurationAttributes](https://github.com/eclipse/buildship/blob/master/org.eclipse.buildship.core/src/main/java/org/eclipse/buildship/core/internal/launch/BaseRunConfigurationAttributes.java) for the E-Attribute names.
* [GradleRunConfigurationAttributes](https://github.com/eclipse/buildship/blob/master/org.eclipse.buildship.core/src/main/java/org/eclipse/buildship/core/internal/launch/GradleRunConfigurationAttributes.java) for the first parsing step.
* [GradleArguments](https://github.com/eclipse/buildship/blob/master/org.eclipse.buildship.core/src/main/java/org/eclipse/buildship/core/internal/configuration/GradleArguments.java) for further deciphering how each key is used.

The following types are covered:
* `org.eclipse.buildship.core.launch.runconfiguration`
  * Used when launching one or more [gradle tasks](https://docs.gradle.org/current/userguide/more_about_tasks.html).
* `org.eclipse.buildship.core.launch.test.runconfiguration`
  * Used when launching one or more [gradle tests](https://docs.gradle.org/current/userguide/java_testing.html).

These types form the set of launch configurations necessary for interfacing with gradle.  
The sections below detail how to construct a launch configuration of each type.

## Gradle Task Launch
### Description
This section details the E-Attributes relevant to the `org.eclipse.buildship.core.launch.runconfiguration` launch configuration type.  
### Supported Keys
* `tasks`
  * E-Attribute type: `listAttribute`
  * Usage: Ordered list of gradle tasks that will be run by the launch configuration.
  * Required: True
  * Note: Task names must be [fully-qualified names](https://docs.gradle.org/current/userguide/intro_multi_project_builds.html#sec:executing_tasks_by_fully_qualified_name).
* `working_dir`
  * E-Attribute type: `stringAttribute`
  * Usage: Declares the working directory for the gradle task(s) that will be run.
  * Required: False
  * Default Value: `""` - Unsure where execution will begin when the empty string is passed.
  * Note: This value can use Eclipse Variables similar to how `org.eclipse.jdt.launching.WORKING_DIRECTORY` can use them.
* `gradle_distribution`
  * E-Attribute type: `stringAttribute`
  * Usage: The gradle distribution to use. Can specify to use the local gradle wrapper via `"GRADLE_DISTRIBUTION(WRAPPER)"`.
  * Required: False
  * Default Value: `"GRADLE_DISTRIBUTION(WRAPPER)"`
  * Note: Syntax for specifing specific gradle versions unknown.
* `offline_mode`
  * E-Attribute type: `booleanAttribute`
  * Usage: Specifies if offline mode is enabled or not. Equivalent to passing `--offline` to gradle during task execution.
  * Required: False
  * Default Value: `"false"`
* `show_console_view`
  * E-Attribute type: `booleanAttribute`
  * Usage: Specifies if the eclipse console will be shown when execution begins.
  * Required: False
  * Default Value: `"true"`
* `show_execution_view`
  * E-Attribute type: `booleanAttribute`
  * Usage: Specifies if the eclipse Gradle Executions window will be shown when execution begins.
  * Required: False
  * Default Value: `"true"`
* `gradle_user_home`
  * E-Attribute type: `stringAttribute`
  * Usage: Override for the [gradle user home](https://docs.gradle.org/current/userguide/directory_layout.html#dir:gradle_user_home).
  * Required: False
  * Default Value: `null`
  * Note: This value may be able to use Eclipse Variables similar to how `org.eclipse.jdt.launching.WORKING_DIRECTORY` can use them. Requires testing.
* `java_home`
  * E-Attribute type: `stringAttribute`
  * Usage: Override for the [java home](https://docs.gradle.org/current/userguide/build_environment.html) used by gradle.
  * Required: False
  * Default Value: `null`
  * Note: This value may be able to use Eclipse Variables similar to how `org.eclipse.jdt.launching.WORKING_DIRECTORY` can use them. Requires testing.
* `arguments`
  * E-Attribute type: `listAttribute`
  * Usage: A list of strings specifying all arguments that will be passed to the program. One argument per entry.
  * Required: False
  * Default Value: Empty list
* `jvm_arguments`
  * E-Attribute type: `listAttribute`
  * Usage: A string specifying all arguments that will be passed to the JVM. One argument per entry.
  * Required: False
  * Default Value: Empty list
### Examples
```xml
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<launchConfiguration type="org.eclipse.buildship.core.launch.runconfiguration">
  <stringAttribute key="working_dir" value="${workspace_loc:MyProjectName}"/>
  <listAttribute key="tasks">
    <listEntry value=":clean"/>
    <listEntry value=":processResources"/>
  </listAttribute>
</launchConfiguration>
```

#### [Prev](./03_NativeLaunches.md) | [Next](./05_LaunchGroups.md)
