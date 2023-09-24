package net.neoforged.elc;

public class EclipseVariables {
    public static String workspaceLocation(String projectName) {
        return "${workspace_loc:" + projectName + "}";
    }
}
