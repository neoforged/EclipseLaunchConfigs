package net.neoforged.elc.tests;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.xml.stream.XMLStreamException;

import net.neoforged.elc.configs.GradleLaunchConfig;
import net.neoforged.elc.configs.JavaApplicationLaunchConfig;
import net.neoforged.elc.configs.LaunchConfig;
import net.neoforged.elc.configs.LaunchGroup;
import net.neoforged.elc.configs.LaunchGroup.Action;
import net.neoforged.elc.configs.LaunchGroup.Mode;

public class Main {

    public static void main(String[] args) throws Exception {

        System.out.println("Test Var 1: " + System.getenv("ELC_TEST_VAR_1"));
        System.out.println("Test Var 2: " + System.getenv("ELC_TEST_VAR_2"));

        System.out.println("Args: ");
        for (String s : args) System.out.println(s);

        writeLaunchToFile("EclipseLaunchConfigs - runTest",
            JavaApplicationLaunchConfig.builder("EclipseLaunchConfigs")
                .envVar("ELC_TEST_VAR_1", "the first value")
                .envVar("ELC_TEST_VAR_2", "the second value")
                .vmArgs("-Xmx256M")
                .args("--doStuff=true")
                .build("net.neoforged.elc.tests.Main"));

        writeLaunchToFile("EclipseLaunchConfigs - runGradle",
            GradleLaunchConfig.builder("EclipseLaunchConfigs")
                .tasks(":hello")
                .showConsole(false)
                .offlineMode(true)
                .args("--console=plain")
                .property("helloReply", "Hello from Eclipse!")
                .jvmArgs("-Xmx256M")
                .build());

        writeLaunchToFile("EclipseLaunchConfigs - runGroup",
            LaunchGroup.builder()
                .entry(LaunchGroup.entry("EclipseLaunchConfigs - runGradle")
                    .enabled(true)
                    .adoptIfRunning(true)
                    .mode(Mode.RUN)
                    .action(Action.waitForTermination()))
                .entry(LaunchGroup.entry("EclipseLaunchConfigs - runTest")
                    .enabled(true)
                    .adoptIfRunning(false)
                    .mode(Mode.DEBUG)
                    .action(Action.delay(3)))
                .build());

    }

    public static void writeLaunchToFile(String fileName, LaunchConfig config) {
        try (FileWriter writer = new FileWriter(fileName + ".launch", StandardCharsets.UTF_8, false)) {
            config.write(writer);
        }
        catch (IOException | XMLStreamException e) {
            e.printStackTrace();
        }
    }
}
