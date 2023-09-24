package net.neoforged.elc;

import lombok.Builder;
import lombok.Data;
import net.neoforged.elc.attribute.ELCAttribute;
import net.neoforged.elc.attribute.SimpleAttribute;
import net.neoforged.elc.attribute.SimpleValue;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GroupLaunchConfig extends LaunchConfig {
    private final List<GroupEntry> entries = new ArrayList<>();

    public GroupLaunchConfig addEntry(GroupEntry entry) {
        this.entries.add(entry);
        return this;
    }

    @Override
    public String getType() {
        return "org.eclipse.debug.core.groups.GroupLaunchConfigurationType";
    }

    @Override
    public List<ELCAttribute> finishChildren() {
        final List<ELCAttribute> attributes = new ArrayList<>();
        for (int i = 0; i < entries.size(); i++) {
            final int finalI = i;
            entries.get(i).build().forEach(attr -> attributes.add(attr.prependKeyPath("org.eclipse.debug.core.launchGroup", Integer.toString(finalI))));
        }
        return attributes;
    }

    @Data
    @Builder
    public static final class GroupEntry {
        private final String name;
        @Builder.Default
        private final Action action = Action.NONE;
        @Builder.Default
        private final boolean adoptIfRunning = false;
        @Builder.Default
        private final boolean enabled = true;
        private final Mode mode;

        public List<SimpleAttribute<?>> build() {
            final List<SimpleAttribute<?>> list = new ArrayList<>();
            action.build(list);
            list.add(ELCAttribute.string("name", name));
            list.add(ELCAttribute.bool("enabled", enabled));
            list.add(ELCAttribute.bool("adoptIfRunning", adoptIfRunning));
            list.add(ELCAttribute.enumAttribute("mode", mode));
            return list;
        }
    }

    @Data
    public static final class Action {
        private final ActionType type;
        @Nullable
        private final SimpleValue<?> param;

        public static final Action NONE = new Action(ActionType.NONE, null);

        public void build(List<SimpleAttribute<?>> attributes) {
            attributes.add(ELCAttribute.enumAttribute("action", type));
            if (param != null) {
                attributes.add(ELCAttribute.string("actionParam", param.valueAsString()));
            }
        }
    }

    public enum ActionType {
        NONE, WAIT_FOR_TERMINATION, DELAY, OUTPUT_REGEXP
    }

    public enum Mode {
        INHERIT, RUN, DEBUG;

        @Override
        public String toString() {
            return name().toLowerCase(Locale.ROOT);
        }
    }
}
