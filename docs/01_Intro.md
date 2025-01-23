# Introduction to Eclipse Runs
## Description
To launch programs within the Eclipse IDE, a configuration file known as a Run Configuration must be created, which instructs Eclipse
to perform a particular action or series of actions, eventually starting the program.

## Basic Information
Eclipse Run Configurations (`.launch` files) are [XML files](https://www.w3schools.com/xml/xml_whatis.asp). Each file declares one element named `launchConfiguration`, which holds the data of the run config.

The `launchConfiguration` must declare a `type` attribute, where the `type` is any `launchConfigurationType` declared by Eclipse or an extension (such as Buildship).

That element then contains sub-elements in the Eclipse **E-Attribute** format (not to be confused with [XML Attributes](https://www.w3schools.com/xml/xml_attributes.asp)).  
E-Attributes are described [here](./02_EclipseAttributes.md).

Each `launchConfiguration` has its own unique schema, defining which E-Attributes make up the configuration. 

## Known Launch Configurations
Currently, we know of the following types:
* `org.eclipse.buildship.core.launch.runconfiguration`
  * See [Gradle Task Launch](./04_BuildshipLaunches.md#gradle-task-launch)
* `org.eclipse.buildship.core.launch.test.runconfiguration`
* `org.eclipse.debug.core.groups.GroupLaunchConfigurationType`
  * See [Launch Groups](./05_LaunchGroups.md#launch-groups)
* `org.eclipse.jdt.launching.localJavaApplication`
  * See [Local Java Application](./03_NativeLaunches.md#local-java-application)
* `org.eclipse.jdt.launching.remoteJavaApplication`
* `org.eclipse.jdt.launching.javaApplet`
* `org.eclipse.jdt.junit.launchconfig`

#### [Next](./02_EclipseAttributes.md)
