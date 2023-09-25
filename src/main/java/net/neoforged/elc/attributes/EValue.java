package net.neoforged.elc.attributes;

import java.util.function.Function;

public record EValue<T>(T value, Function<T, String> serializer) {

    public String serialize() {
        return this.serializer.apply(this.value);
    }

    public static EValue<String> of(String value) {
        return of(value, Object::toString);
    }

    public static EValue<Boolean> of(boolean value) {
        return of(value, Object::toString);
    }

    public static EValue<Integer> of(int value) {
        return of(value, Object::toString);
    }

    public static <T extends Enum<T>> EValue<T> of(T value) {
        return of(value, Object::toString);
    }

    public static <T> EValue<T> of(T value, Function<T, String> serializer) {
        return new EValue<>(value, serializer);
    }
}
