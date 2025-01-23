# EclipseLaunchConfigs
A Java library for generating Eclipse Launch Configurations (.launch files).  

## Launch Configs
For information on how Eclipse Launch Configs function, see the [docs](./docs/01_Intro.md).  

## Using This Library
To create new launch configurations, find the appropriate class in [net.neoforged.elc.configs](./src/main/java/net/neoforged/elc/configs) and create a builder using the static `builder` method.  

Each builder has options unique to that particular configuration. Fill out the information you need, then call `Builder#build()` to finalize the instance and create a [`LaunchConfig`](./src/main/java/net/neoforged/elc/configs/LaunchConfig.java) object.  

You may then inspect the finalized config and write it to a file using `LaunchConfig#write(Writer)`.  

If you need to create `EAttribute` objects to supply to the builder, see [here](./src/main/java/net/neoforged/elc/attributes/EAttribute.java). For helpers to work with Eclipse Variables, see [here](./src/main/java/net/neoforged/elc/EclipseVariables.java).  

## Examples
For a few example usages, see the [unit tests](./src/test/java/net/neoforged/elc/tests).
