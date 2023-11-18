package net.neoforged.elc.tests;

import net.neoforged.elc.configs.LaunchGroup;
import org.junit.jupiter.api.Test;

public class LaunchGroupConfigTest extends RunTest {
    @Test
    public void testGroupConfig() throws Exception {
        assertExpectedConfig(LaunchGroup.builder()
                .entry(LaunchGroup.entry("EclipseLaunchConfigs - runGradle")
                        .enabled(true)
                        .adoptIfRunning(false)
                        .mode(LaunchGroup.Mode.RUN)
                        .action(LaunchGroup.Action.delay(2)))
                .entry(LaunchGroup.entry("EclipseLaunchConfigs - runTest")
                        .enabled(true)
                        .adoptIfRunning(false)
                        .mode(LaunchGroup.Mode.DEBUG)
                        .action(LaunchGroup.Action.waitForTermination()))
                .build(), "runGroup.launch");
    }
}
