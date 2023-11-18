package net.neoforged.elc.tests;

import net.neoforged.elc.configs.GradleLaunchConfig;
import org.junit.jupiter.api.Test;

public class GradleLaunchConfigTest extends RunTest {
    @Test
    public void testGradleConfig() throws Exception {
        assertExpectedConfig(GradleLaunchConfig.builder("EclipseLaunchConfigs")
                .tasks(":hello")
                .showConsole(false)
                .offlineMode(true)
                .args("--console=plain")
                .property("helloReply", "Hello from Eclipse!")
                .jvmArgs("-Xmx256M")
                .build(), "runGradle.launch");
    }
}
