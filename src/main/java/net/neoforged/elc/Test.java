package net.neoforged.elc;

import java.io.StringWriter;
import java.util.Arrays;

import net.neoforged.elc.configs.GradleLaunchConfig;
import net.neoforged.elc.configs.LaunchGroup;
import net.neoforged.elc.configs.LaunchGroup.Action;
import net.neoforged.elc.configs.LaunchGroup.Mode;
import net.neoforged.elc.configs.JavaApplicationLaunchConfig;

public class Test {
    public static void main(String[] args) throws Exception {
        final StringWriter stringWriter = new StringWriter();

        LaunchGroup.builder()
            .entry(LaunchGroup.entry("EclipseLaunchConfigs - runTest")
                .enabled(true)
                .adoptIfRunning(true)
                .mode(Mode.DEBUG)
                .action(Action.delay(5)))
            .build()
            .write(stringWriter);

        stringWriter.write("\n");
        GradleLaunchConfig.builder()
            .task(":prepareResources")
            .showConsoleView(false)
            .offlineMode(true)
            .argument("--help")
            .gradleProperty("updating", true)
            .jvmArgument("-Xmx1G")
            .build().write(stringWriter);

        stringWriter.write("\n");
        JavaApplicationLaunchConfig.builder("EclipseLaunchConfigs")
            .envVar("MC_VERSION", "1.18.2")
            .envVar("MOD_CLASSES", "modId%%C:/Users/X")
            .vmArgument("-Dmixin.env.remapRefMap=true")
            .workingDirectory("")
            .build("net.neoforged.elc.Test").write(stringWriter);

        System.out.println(stringWriter.toString());
    }
}
