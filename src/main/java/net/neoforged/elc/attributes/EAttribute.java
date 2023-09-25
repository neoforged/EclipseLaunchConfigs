package net.neoforged.elc.attributes;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Base class for all Eclipse Attributes.
 *
 * @param <T> The type corresponding to the value of the attribute.
 */
public interface EAttribute {

    /**
     * Writes this E-Attribute to an XML stream.
     */
    void write(XMLStreamWriter writer, XMLOutputFactory outputFactory) throws XMLStreamException;

    /**
     * Gets the key of this E-Attribute.
     */
    String key();

    static ListAttribute of(String key, List<EValue<?>> values) {
        return new ListAttribute(key, values);
    }

    static SetAttribute of(String key, Set<EValue<?>> values) {
        return new SetAttribute(key, values);
    }

    static MapAttribute of(String key, Map<String, EValue<?>> data) {
        return new MapAttribute(key, data);
    }

    static PrimitiveAttribute<String> of(String key, String value) {
        return new PrimitiveAttribute<>(key, EValue.of(value));
    }

    static PrimitiveAttribute<Boolean> of(String key, boolean value) {
        return new PrimitiveAttribute<>(key, EValue.of(value));
    }

    static PrimitiveAttribute<Integer> of(String key, int value) {
        return new PrimitiveAttribute<>(key, EValue.of(value));
    }

    static <T extends Enum<T>> PrimitiveAttribute<T> of(String key, T value) {
        return new PrimitiveAttribute<>(key, EValue.of(value));
    }

    static <T> PrimitiveAttribute<T> of(String key, T value, Function<T, String> serializer) {
        return new PrimitiveAttribute<>(key, EValue.of(value, serializer));
    }
}
