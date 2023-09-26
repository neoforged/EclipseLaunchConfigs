package net.neoforged.elc.attributes;

import java.util.function.Function;

import com.github.bsideup.jabel.Desugar;

@Desugar
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

    /**
     * Gets the E-Attribute Type ID of the contained value, which is one of the following:
     * <ul>
     * <li><code>"intAttribute"</code> for {@link Integer} values.</li>
     * <li><code>"booleanAttribute"</code> for {@link Boolean} values.</li>
     * <li><code>"stringAttribute"</code> for all other values.</li>
     * </ul>
     * 
     * @return The E-Attribute Type ID of the contained value.
     */
    public String getTypeId() {
        if (this.value.getClass() == Integer.class) return "intAttribute";
        else if (this.value.getClass() == Boolean.class) return "booleanAttribute";
        else return "stringAttribute";
    }
}
