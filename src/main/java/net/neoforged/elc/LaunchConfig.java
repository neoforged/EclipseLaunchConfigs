package net.neoforged.elc;

import net.neoforged.elc.attribute.ELCAttribute;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.Writer;
import java.util.List;

public abstract class LaunchConfig {
    public abstract String getType();

    public abstract List<ELCAttribute> finishChildren();

    public void write(Writer outputStream) throws XMLStreamException {
        final XMLOutputFactory outputFactory = XMLOutputFactory.newFactory();
        final XMLStreamWriter writer = outputFactory.createXMLStreamWriter(outputStream);
        writer.writeStartDocument();

        writer.writeStartElement("launchConfiguration");
        writer.writeAttribute("type", getType());
        for (final ELCAttribute attribute : finishChildren()) {
            attribute.write(writer, outputFactory);
        }

        writer.writeEndElement();
        writer.writeEndDocument();
    }

    @Override
    public String toString() {
        return "LaunchConfig{" +
                "type='" + getType() + '\'' +
                '}';
    }
}
