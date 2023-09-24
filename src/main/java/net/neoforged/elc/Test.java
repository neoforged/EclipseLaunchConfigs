package net.neoforged.elc;

import java.io.StringWriter;

public class Test {
    public static void main(String[] args) throws Exception {
        final StringWriter stringWriter = new StringWriter();

        new GroupLaunchConfig()
                .addEntry(GroupLaunchConfig.GroupEntry.builder()
                        .adoptIfRunning(true)
                        .mode(GroupLaunchConfig.Mode.INHERIT)
                        .name("abcd")
                        .enabled(true)
                        .build())
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

        System.out.println(stringWriter.toString());
    }
}
