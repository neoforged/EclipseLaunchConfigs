package net.neoforged.elc;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.stream.Stream;

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

        stringWriter.write("\n");
        JavaApplicationLaunchConfig.builder()
                .projectAttribution("MyProject")
                .envVar("MC_VERSION", "1.18.2")
                .envVar("MOD_CLASSES", "modId%%C:/Users/X")
                .vmArgument("-Dmixin.env.remapRefMap=true")
                .classpath(Arrays.asList("C:Users/X/cp.jar"))
                .workingDirectory("")
                .mainClass("net.me.you.Main")
                .build().write(stringWriter);

        System.out.println(stringWriter.toString());
    }
}
