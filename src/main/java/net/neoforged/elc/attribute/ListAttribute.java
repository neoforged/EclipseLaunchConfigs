package net.neoforged.elc.attribute;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public final class ListAttribute implements ELCAttribute<ListAttribute> {
    private final String key;
    private final List<SimpleValue<?>> values;

    public ListAttribute(String key) {
        this(key, new ArrayList<>());
    }

    public ListAttribute add(SimpleValue<?> value) {
        this.values.add(value);
        return this;
    }

    public ListAttribute addAll(Iterable<SimpleValue<?>> values) {
        values.forEach(this::add);
        return this;
    }

    @Override
    public ListAttribute withKey(String key) {
        return new ListAttribute(key, this.values);
    }

    @Override
    public void write(XMLStreamWriter writer, XMLOutputFactory outputFactory) throws XMLStreamException {
        writer.writeStartElement("listAttribute");
        writer.writeAttribute("key", key);
        for (SimpleValue<?> entry : values) {
            writer.writeStartElement("listEntry");
            writer.writeAttribute("value", entry.valueAsString());
            writer.writeEndElement();
        }
        writer.writeEndElement();
    }
}
