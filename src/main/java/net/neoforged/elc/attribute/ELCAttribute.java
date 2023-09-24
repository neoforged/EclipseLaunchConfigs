package net.neoforged.elc.attribute;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public interface ELCAttribute<T extends ELCAttribute<T>> {
    void write(XMLStreamWriter writer, XMLOutputFactory outputFactory) throws XMLStreamException;

    String getKey();

    T withKey(String key);
    default T prependKeyPath(String... path) {
        return withKey(String.join(".", path) + "." + this.getKey());
    }

    static ListAttribute list(String key, SimpleValue<?>... values) {
        return new ListAttribute(key, new ArrayList<>(Arrays.asList(values)));
    }

    static SetAttribute set(String key, SimpleValue<?>... values) {
        return new SetAttribute(key, new HashSet<>(Arrays.asList(values)));
    }

    static MapAttribute map(String key) {
        return new MapAttribute(key);
    }

    static SimpleAttribute<String> string(String key, String value) {
        return new SimpleAttribute<>("stringAttribute", key, SimpleValue.string(value));
    }

    static SimpleAttribute<Boolean> bool(String key, boolean value) {
        return new SimpleAttribute<>("booleanAttribute", key, SimpleValue.bool(value));
    }

    static SimpleAttribute<Integer> integer(String key, int value) {
        return new SimpleAttribute<>("intAttribute", key, SimpleValue.integer(value));
    }

    static <T extends Enum<T>> SimpleAttribute<T> enumAttribute(String key, T value) {
        return new SimpleAttribute<>("stringAttribute", key, SimpleValue.enumValue(value));
    }
}
