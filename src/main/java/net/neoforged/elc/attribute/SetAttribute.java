package net.neoforged.elc.attribute;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.HashSet;
import java.util.Set;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public final class SetAttribute implements ELCAttribute {
    private final String key;
    private final Set<SimpleValue<?>> values;

    public SetAttribute(String key) {
        this(key, new HashSet<>());
    }

    public SetAttribute add(SimpleValue<?> value) {
        this.values.add(value);
        return this;
    }

    @Override
    public void write(XMLStreamWriter writer, XMLOutputFactory outputFactory) throws XMLStreamException {
        writer.writeStartElement("setAttribute");
        writer.writeAttribute("key", key);
        for (SimpleValue<?> entry : values) {
            writer.writeStartElement("setEntry");
            writer.writeAttribute("value", entry.valueAsString());
            writer.writeEndElement();
        }
        writer.writeEndElement();
    }
}
