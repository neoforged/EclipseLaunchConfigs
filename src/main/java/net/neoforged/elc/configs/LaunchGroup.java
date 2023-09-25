package net.neoforged.elc.configs;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.jetbrains.annotations.Nullable;

import net.neoforged.elc.attributes.EAttribute;
import net.neoforged.elc.attributes.EValue;
import net.neoforged.elc.attributes.PrimitiveAttribute;

/**
 * This class implements the <code>org.eclipse.debug.core.groups.GroupLaunchConfigurationType</code> launch configuration type.
 * <p>
 * This type of config is known in Eclipse as a Launch Group instead of a Launch Configuration.
 * <p>
 * It is used for launching multiple launch configs in sequence.
 * 
 * @param entries The list of sub launches to launch.
 */
public record LaunchGroup(List<Entry> entries) implements LaunchConfig {

    @Override
    public String getType() {
        return "org.eclipse.debug.core.groups.GroupLaunchConfigurationType";
    }

    @Override
    public List<EAttribute> bakeAttributes() {
        List<EAttribute> attributes = new ArrayList<>();
        this.entries.forEach(entry -> entry.bakeAttributes(attributes));
        return attributes;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Entry.Builder entry(String name) {
        return new Entry.Builder(name);
    }

    /**
     * Represents a single launch group entry, which are parameters on how to execute an external {@link LaunchConfig}.
     * 
     * @param index          The index of the entry in the Launch Group
     * @param name           The name of the launch configuration that will be executed by this entry.
     * @param enabled        If this entry is enabled, and will be executed when the Launch Group is run.
     * @param adoptIfRunning If true, the currently executing instance will be adopted to the executed config.
     * @param mode           The execution mode. See {@link Mode}.
     * @param action         The post-launch action. See {@link Action}.
     */
    public static record Entry(int index, String name, boolean enabled, boolean adoptIfRunning, Mode mode, Action action) {

        public void bakeAttributes(List<EAttribute> attributes) {
            attributes.add(EAttribute.of(Keys.name(index), name));
            attributes.add(EAttribute.of(Keys.enabled(index), enabled));
            if (this.adoptIfRunning) {
                attributes.add(EAttribute.of(Keys.adoptIfRunning(index), adoptIfRunning));
            }
            attributes.add(EAttribute.of(Keys.mode(index), mode));
            attributes.add(EAttribute.of(Keys.action(index), action.type));
            if (action.type.hasParam()) {
                attributes.add(new PrimitiveAttribute<>(Keys.actionParam(index), action.param));
            }
        }

        public static Entry.Builder builder(String name) {
            return new Entry.Builder(name);
        }

        /**
         * Builder for {@link LaunchGroup.Entry}.
         */
        public static class Builder {

            private final String name;

            private boolean enabled = true;
            private boolean adoptIfRunning = false;
            private Mode mode = Mode.INHERIT;
            private Action action = Action.NONE;

            public Builder(String name) {
                this.name = name;
            }

            /**
             * Sets the enabled state. If this entry is not enabled, it will not be launched.
             * 
             * @param enabled If this entry is enabled.
             * @return this
             */
            public Builder enabled(boolean enabled) {
                this.enabled = enabled;
                return this;
            }

            /**
             * Sets the adopt if running flag. It does something™.
             * 
             * @param adoptIfRunning The adopt if running flag
             * @return this
             */
            public Builder adoptIfRunning(boolean adoptIfRunning) {
                this.adoptIfRunning = adoptIfRunning;
                return this;
            }

            /**
             * Sets the launch mode for the target configuration.
             * 
             * @param mode The new launch mode.
             * @return this
             * @see {@link Mode}
             */
            public Builder mode(Mode mode) {
                this.mode = mode;
                return this;
            }

            /**
             * Sets the post-launch action.
             * 
             * @param action The new action.
             * @return this
             * @see {@link Action}
             */
            public Builder action(Action action) {
                this.action = action;
                return this;
            }

            public Entry build(int index) {
                return new Entry(index, name, enabled, adoptIfRunning, mode, action);
            }

        }

    }

    /**
     * Represents a post-launch action for a Launch Group entry.
     * 
     * @param type  The type of action being taken.
     * @param param Additional data passed to specific {@link ActionType}s.
     */
    public static record Action(ActionType type, @Nullable EValue<?> param) {

        public static final Action NONE = new Action(ActionType.NONE, null);

        public void bakeAttributes(List<EAttribute> attributes) {
            attributes.add(EAttribute.of("action", type));
            if (param != null) {
                attributes.add(new PrimitiveAttribute<>("actionParam", param));
            }
        }

        /**
         * Performs no action.
         */
        public static Action none() {
            return NONE;
        }

        /**
         * The thread controlling the Launch Group will sleep until the current entry has completed execution.
         */
        public static Action waitForTermination() {
            return new Action(ActionType.WAIT_FOR_TERMINATION, null);
        }

        /**
         * The thread controlling the Launch Group will sleep for the specified number of seconds.
         * 
         * @param delay The delay, in seconds, that the thread will sleep for.
         */
        public static Action delay(int delay) {
            return new Action(ActionType.DELAY, EValue.of(delay));
        }

        /**
         * The thread controlling the Launch Group will await a response from the entry that matches the specified regular expression.
         * 
         * @param regex The regular expression that will be matched against the output of the launch group entry.
         */
        public static Action outputRegexp(String regex) {
            return new Action(ActionType.OUTPUT_REGEXP, EValue.of(regex));
        }

        public static enum ActionType {
            NONE, WAIT_FOR_TERMINATION, DELAY, OUTPUT_REGEXP;

            public boolean hasParam() {
                return this == DELAY || this == OUTPUT_REGEXP;
            }
        }
    }

    /**
     * Launch modes for Launch Group entries.
     */
    public static enum Mode {

        /**
         * Execute the configuration in whatever mode was specified by the user when starting the Launch Group.
         */
        INHERIT,

        /**
         * Always execute the configuration without invoking the debugger or the profiler.
         * <p>
         * {@link GradleLaunchConfig}s can only be run in this mode.
         */
        RUN,

        /**
         * Always execute the configuration in debug mode, starting the Eclipse debugger.
         */
        DEBUG,

        /**
         * Always execute the configuration in profiling mode, starting the Eclipse profiler.
         */
        PROFILE;

        @Override
        public String toString() {
            return name().toLowerCase(Locale.ROOT);
        }
    }

    /**
     * Builder for {@link LaunchGroup}.
     */
    public static class Builder {

        List<Entry> entries = new ArrayList<>();

        /**
         * Adds a new {@linkplain LaunchGroup.Entry launch group entry}.
         * 
         * @param entry A builder for the entry being added.
         * @return this
         */
        public Builder entry(Entry.Builder entry) {
            this.entries.add(entry.build(this.entries.size()));
            return this;
        }

        /**
         * Builds a {@link LaunchGroup} from the provided entries.
         * <p>
         * All containers (lists, maps) are copied, so further modification to this builder will not modify the built object.
         * 
         * @return A newly-constructed {@link LaunchGroup}.
         */
        public LaunchGroup build() {
            return new LaunchGroup(List.copyOf(this.entries));
        }

    }

    /**
     * Holds keys relevant to serialization of {@link LaunchGroup}.
     * <p>
     * Because most of the keys require inserting an index into them, this class holds methods instead of constants.
     */
    public static class Keys {

        public static final String PREFIX = "org.eclipse.debug.core.launchGroup.";

        /**
         * Defines the name of the launch configuration that will be run for this index.
         * <p>
         * E-Attribute Type: String
         * <br>
         * Required: True
         */
        public static String name(int index) {
            return PREFIX + index + ".name";
        }

        /**
         * Defines if the launch group entry at this index is enabled and should be run.
         * <p>
         * E-Attribute Type: Boolean
         * <br>
         * Required: True
         */
        public static String enabled(int index) {
            return PREFIX + index + ".enabled";
        }

        /**
         * Defines if the launch group entry should use a currently executing instance, if one exists.
         * <p>
         * E-Attribute Type: Boolean
         * <br>
         * Required: False
         * <br>
         * Default Value: False
         */
        public static String adoptIfRunning(int index) {
            return PREFIX + index + ".adoptIfRunning";
        }

        /**
         * Defines the execution mode for the launch configuration at this index.
         * <p>
         * E-Attribute Type: String
         * <br>
         * Required: True
         */
        public static String mode(int index) {
            return PREFIX + index + ".mode";
        }

        /**
         * Defines the name of the post-launch action to take after the configuration at this index has launched, but before the next index has launched.
         * <p>
         * E-Attribute Type: String
         * <br>
         * Required: False
         * <br>
         * Default Value: {@link Action#NONE}
         */
        public static String action(int index) {
            return PREFIX + index + ".action";
        }

        /**
         * Defines the action parameter, based on the set action.
         */
        public static String actionParam(int index) {
            return PREFIX + index + ".actionParam";
        }
    }
}
