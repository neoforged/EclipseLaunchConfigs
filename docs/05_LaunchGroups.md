# Launch Groups
## Description
This document covers Launch Groups, which are `launchConfiguration`s that have the type `org.eclipse.debug.core.groups.GroupLaunchConfigurationType`.
These are launch configurations that execute multiple launches in sequence.
The information in this section was derived from [GroupLaunchConfigurationDelegate](https://github.com/eclipse-platform/eclipse.platform/blob/master/debug/org.eclipse.debug.core/core/org/eclipse/debug/internal/core/groups/GroupLaunchConfigurationDelegate.java).

The keys of a launch group each include an index number, represented in this document as `N`. The index is placed in the keys of the inner E-Attributes between the `org.eclipse.debug.core.launchGroup.` and the true key name.
For clarity, the key names will be declared below as `org.eclipse.debug.core.launchGroup.N.keyName` where `N` is the index number.

A Launch Group specifies many entries, with the index numbers starting at zero. Grouped launches will be run in ascending order based on index number, but do not need to be declared in ascending order. However, it is illegal to have gaps between index numbers. If a launch group declares an entry with index 3,
it must declare entries with indicies 0, 1, and 2.

## Supported Keys
* `org.eclipse.debug.core.launchGroup.N.name`
  * E-Attribute type: `stringAttribute`
  * Usage: Specifies the name of the launch configuration that will be run for this index.
  * Required: True
  * Note: The name of a launch configuration is generally its file name, omitting the `.launch` extension.
* `org.eclipse.debug.core.launchGroup.N.enabled`
  * E-Attribute type: `booleanAttribute`
  * Usage: Specifies if the launch group entry at this index is enabled and should be run.
  * Required: True
  * Note: This value should default to `"true"`, but the parser always attempts to read the value, which means the default is ignored.
* `org.eclipse.debug.core.launchGroup.N.adoptIfRunning`
  * E-Attribute type: `booleanAttribute`
  * Usage: Specifies if the launch group entry should use a currently executing instance, if one exists.
  * Required: False
  * Default Value: `"false"`
* `org.eclipse.debug.core.launchGroup.N.mode`
  * E-Attribute type: `stringAttribute`
  * Usage: The execution mode for the launch configuration at this index. One of `"run"`, `"debug"`, `"profile"`, or `"inherit"`. See [here](https://github.com/eclipse-platform/eclipse.platform/blob/master/debug/org.eclipse.debug.core/core/org/eclipse/debug/core/ILaunchManager.java) for reference.
    * `"run"` - Always execute the configuration without invoking the debugger or the profiler.
    * `"debug"` - Always execute the configuration with the debugger.
    * `"profile"` - Always execute the configuration with the profiler.
    * `"inherit"` - Execute the configuration in whatever mode was specified by the user when starting the Launch Group.
  * Required: True
  * Note: This value should default to `"inherit"`, but the parser always attempts to read the value, which means the default is ignored.
* `org.eclipse.debug.core.launchGroup.N.action`
  * E-Attribute type: `stringAttribute`
  * Usage: The name of the post-launch action to take after the configuration at this index has launched, but before the next index has launched. One of `"NONE"`, `"WAIT_FOR_TERMINATION"`, `"DELAY"`, or `"OUTPUT_REGEXP"`. See [here](https://github.com/eclipse-platform/eclipse.platform/blob/master/debug/org.eclipse.debug.core/core/org/eclipse/debug/internal/core/groups/GroupLaunchConfigurationDelegate.java#L203) for reference.
    * `"NONE"` - Perform no action.
    * `"WAIT_FOR_TERMINATION"` - The thread controlling the Launch Group will sleep until the current entry has completed execution.
    * `"DELAY"` - The thread controlling the Launch Group will sleep for a specified number of seconds.
    * `"OUTPUT_REGEXP"` - The thread controlling the Launch Group will await a response from the entry that matches the specified regular expression.
  * Required: False
  * Default Value: `"NONE"`
* `org.eclipse.debug.core.launchGroup.N.actionParam`
  * The `actionParam` has a variable declaration depending on the value of `org.eclipse.debug.core.launchGroup.N.action`.
  * `"NONE"`
    * Ignored
  * `"WAIT_FOR_TERMINATION"`
    * Ignored
  * `"DELAY"`
    * E-Attribute type: `intAttribute`
    * Usage: Specifies the time, in seconds, that the `DELAY` action will sleep for.
    * Required: True
  * `"OUTPUT_REGEXP"`
    * E-Attribute type: `stringAttribute`
    * Usage: The regular expression that will be matched against the output of the launch group entry.
    * Required: True
## Examples
Assume we have a launch configuration named `Project_RunGradleTasks` and another named `Project_LaunchApplication`.
```xml
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<launchConfiguration type="org.eclipse.debug.core.groups.GroupLaunchConfigurationType">
  <stringAttribute key="org.eclipse.debug.core.launchGroup.0.name" value="Project_RunGradleTasks"/>
  <booleanAttribute key="org.eclipse.debug.core.launchGroup.0.enabled" value="true"/>
  <stringAttribute key="org.eclipse.debug.core.launchGroup.0.mode" value="run"/>
  <stringAttribute key="org.eclipse.debug.core.launchGroup.1.name" value="Project_LaunchApplication"/>
  <booleanAttribute key="org.eclipse.debug.core.launchGroup.1.enabled" value="true"/>
  <stringAttribute key="org.eclipse.debug.core.launchGroup.1.mode" value="inherit"/>
</launchConfiguration>
```

#### [Prev](./04_BuildshipLaunches.md)
