package net.neoforged.elc.configs;

import java.io.Writer;
import java.util.List;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import net.neoforged.elc.attributes.EAttribute;

/**
 * Base interface representing any Eclipse launch configuration.
 */
public interface LaunchConfig {

    /**
     * Returns the type name that corresponds to this launch config.
     */
    String getType();

    /**
     * Serializes this launch configuration into E-Attribute form for {@link #write(Writer)}.
     * 
     * @return A list containing all E-Attributes that make up this launch config.
     */
    List<EAttribute> bakeAttributes();

    /**
     * Writes this launch config as XML to the given output stream.
     * 
     * @param outputStream The target output stream.
     */
    default void write(Writer outputStream) throws XMLStreamException {
        final XMLOutputFactory outputFactory = XMLOutputFactory.newFactory();
        final XMLStreamWriter writer = outputFactory.createXMLStreamWriter(outputStream);
        writer.writeStartDocument();
        writer.writeCharacters("\n");

        writer.writeStartElement("launchConfiguration");
        writer.writeAttribute("type", getType());
        writer.writeCharacters("\n");

        for (EAttribute attribute : bakeAttributes()) {
            writer.writeCharacters("    ");
            attribute.write(writer, outputFactory);
            writer.writeCharacters("\n");
        }

        writer.writeEndElement();
        writer.writeEndDocument();
    }
}
