package net.neoforged.elc.attributes;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * A Primitive Attribute is one that converts an object to one of the three E-Attribute primitive types.
 * <ul>
 * <li>Values of type {@link Integer} are converted to <code>intAttribute</code>.</li>
 * <li>Values of type {@link Boolean} are converted to <code>booleanAttribute</code>.</li>
 * <li>Values of any other type are converted to <code>stringAttribute</code>.</li>
 * </ul>
 * This attribute type cannot produce collections in the form that Eclipse expects them.
 * For collections, see {@link ListAttribute}, {@link SetAttribute}, and {@link MapAttribute}.
 * 
 * @param <T>         The stored type.
 * @param key         The E-Attribute key attribute.
 * @param value       The E-Attribute value attribute.
 */
public record PrimitiveAttribute<T>(String key, EValue<T> value) implements EAttribute {

    @Override
    public void write(XMLStreamWriter writer, XMLOutputFactory outputFactory) throws XMLStreamException {
        writer.writeStartElement(this.value.getTypeId());
        writer.writeAttribute("key", this.key);
        writer.writeAttribute("value", this.value.serialize());
        writer.writeEndElement();
    }

}
