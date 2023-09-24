package net.neoforged.elc.attribute;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public final class MapAttribute implements ELCAttribute {
    private final String key;
    private final Map<String, SimpleValue<?>> attributes;

    public MapAttribute(String key) {
        this(key, new HashMap<>());
    }

    public MapAttribute put(String key, SimpleValue<?> value) {
        this.attributes.put(key, value);
        return this;
    }

    public MapAttribute put(SimpleAttribute<?> attribute) {
        return put(attribute.getKey(), attribute.getValue());
    }

    @Override
    public void write(XMLStreamWriter writer, XMLOutputFactory outputFactory) throws XMLStreamException {
        writer.writeStartElement("mapAttribute");
        writer.writeAttribute("key", key);
        for (Map.Entry<String, SimpleValue<?>> entry : attributes.entrySet()) {
            writer.writeStartElement("mapEntry");
            writer.writeAttribute("key", entry.getKey());
            writer.writeAttribute("value", entry.getValue().valueAsString());
            writer.writeEndElement();
        }
        writer.writeEndElement();
    }
}
