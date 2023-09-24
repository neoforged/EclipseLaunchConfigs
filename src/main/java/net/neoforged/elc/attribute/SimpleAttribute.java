package net.neoforged.elc.attribute;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.function.Function;

@Data
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public final class SimpleAttribute<T> implements ELCAttribute<SimpleAttribute<T>> {
    private final String type;
    private final String key;
    private final SimpleValue<T> value;

    @Override
    public SimpleAttribute<T> withKey(String key) {
        return new SimpleAttribute<>(this.type, key, this.value);
    }

    @Override
    public void write(XMLStreamWriter writer, XMLOutputFactory outputFactory) throws XMLStreamException {
        writer.writeStartElement(type);
        writer.writeAttribute("key", key);
        writer.writeAttribute("value", valueAsString());
        writer.writeEndElement();
    }

    public String valueAsString() {
        return value.valueAsString();
    }

}
