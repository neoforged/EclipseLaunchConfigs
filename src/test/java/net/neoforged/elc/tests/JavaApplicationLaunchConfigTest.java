package net.neoforged.elc.tests;

import net.neoforged.elc.EclipseVariables;
import net.neoforged.elc.configs.JavaApplicationLaunchConfig;
import org.junit.jupiter.api.Test;

public class JavaApplicationLaunchConfigTest extends RunTest {
    @Test
    public void testJavaConfig() throws Exception {
        assertExpectedConfig(JavaApplicationLaunchConfig.builder("EclipseLaunchConfigs")
                .envVar("PROMPT_VALUE", EclipseVariables.prompt("Enter Something", "Some"))
                .envVar("CURRENT_DATE", EclipseVariables.currentDate("yyyy/MM/dd"))
                .vmArgs("-Xmx256M")
                .args("--doStuff=true")
                .build("net.neoforged.elc.tests.Main"), "runJava.launch");
    }
}
