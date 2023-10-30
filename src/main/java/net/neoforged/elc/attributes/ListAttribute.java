package net.neoforged.elc.attributes;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.github.bsideup.jabel.Desugar;

/**
 * A List Attribute represents the E-Attribute type <code>listAttribute</code>.
 * Due to limitations set by Eclipse, the serialized form must be a list of strings.
 * 
 * @param key    The E-Attribute key attribute.
 * @param values The internal list representing this E-Attribute.
 */
@Desugar
public record ListAttribute(String key, List<EValue<?>> values) implements EAttribute {

    /**
     * Constructs a {@link ListAttribute} backed by an empty {@link ArrayList}.
     * 
     * @param key The key of the E-Attribute.
     */
    public ListAttribute(String key) {
        this(key, new ArrayList<>());
    }

    @Override
    public void write(XMLStreamWriter writer, XMLOutputFactory outputFactory) throws XMLStreamException {
        writer.writeStartElement("listAttribute");
        writer.writeAttribute("key", key);
        writer.writeCharacters("\n");
        for (EValue<?> entry : this.values) {
            writer.writeCharacters("        ");
            writer.writeStartElement("listEntry");
            writer.writeAttribute("value", entry.serialize());
            writer.writeEndElement();
            writer.writeCharacters("\n");
        }
        writer.writeCharacters("    ");
        writer.writeEndElement();
    }
}
