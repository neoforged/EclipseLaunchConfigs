package net.neoforged.elc.attribute;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.function.Function;

@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class SimpleValue<T> {
    private final T value;

    @ToString.Exclude @EqualsAndHashCode.Exclude
    private final Function<T, String> toString;
    public String valueAsString() {
        return toString.apply(value);
    }

    public static SimpleValue<String> string(String value) {
        return new SimpleValue<>(value, Function.identity());
    }

    public static SimpleValue<Boolean> bool(boolean value) {
        return new SimpleValue<>(value, Object::toString);
    }

    public static SimpleValue<Integer> integer(int value) {
        return new SimpleValue<>(value, Object::toString);
    }

    public static <T extends Enum<T>> SimpleValue<T> enumValue(T value) {
        return new SimpleValue<>(value, T::toString);
    }
}
