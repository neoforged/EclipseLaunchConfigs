package net.neoforged.elc.tests;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.xml.stream.XMLStreamException;

import net.neoforged.elc.EclipseVariables;
import net.neoforged.elc.configs.GradleLaunchConfig;
import net.neoforged.elc.configs.JavaApplicationLaunchConfig;
import net.neoforged.elc.configs.LaunchConfig;
import net.neoforged.elc.configs.LaunchGroup;
import net.neoforged.elc.configs.LaunchGroup.Action;
import net.neoforged.elc.configs.LaunchGroup.Mode;

public class Main {

    public static void main(String[] args) throws Exception {

        System.out.println("Prompt: " + System.getenv("PROMPT_VALUE"));
        System.out.println("Date: " + System.getenv("CURRENT_DATE"));

        System.out.println("Args: ");
        for (String s : args) System.out.println(s);

        writeLaunchToFile("EclipseLaunchConfigs - runTest",
            JavaApplicationLaunchConfig.builder("EclipseLaunchConfigs")
                .envVar("PROMPT_VALUE", EclipseVariables.prompt("Enter Something", "Some"))
                .envVar("CURRENT_DATE", EclipseVariables.currentDate("yyyy/MM/dd"))
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
                    .adoptIfRunning(false)
                    .mode(Mode.RUN)
                    .action(Action.delay(2)))
                .entry(LaunchGroup.entry("EclipseLaunchConfigs - runTest")
                    .enabled(true)
                    .adoptIfRunning(false)
                    .mode(Mode.DEBUG)
                    .action(Action.waitForTermination()))
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
