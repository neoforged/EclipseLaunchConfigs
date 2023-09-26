package net.neoforged.elc.attributes;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.github.bsideup.jabel.Desugar;

/**
 * A Map Attribute represents the E-Attribute type <code>mapAttribute</code>.
 * Due to limitations set by Eclipse, the serialized form must be a string-keyed map of strings.
 * 
 * @param key  The E-Attribute key attribute.
 * @param data The internal map representing this E-Attribute.
 * @implNote Do not rename <code>data</code> to <code>values</code>, as it will shadow {@link Map#values()}.
 */
@Desugar
public record MapAttribute(String key, Map<String, EValue<?>> data) implements EAttribute, Map<String, EValue<?>> {

    /**
     * Constructs a {@link MapAttribute} backed by an empty {@link HashMap}.
     * 
     * @param key The key of the E-Attribute.
     */
    public MapAttribute(String key) {
        this(key, new HashMap<>());
    }

    @Override
    public void write(XMLStreamWriter writer, XMLOutputFactory outputFactory) throws XMLStreamException {
        writer.writeStartElement("mapAttribute");
        writer.writeAttribute("key", key);
        writer.writeCharacters("\n");
        for (Map.Entry<String, EValue<?>> entry : this.data.entrySet()) {
            writer.writeCharacters("        ");
            writer.writeStartElement("mapEntry");
            writer.writeAttribute("key", entry.getKey());
            writer.writeAttribute("value", entry.getValue().serialize());
            writer.writeEndElement();
            writer.writeCharacters("\n");
        }
        writer.writeCharacters("    ");
        writer.writeEndElement();
    }

    @Override
    public int size() {
        return this.data.size();
    }

    @Override
    public boolean isEmpty() {
        return this.data.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.data.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.data.containsValue(value);
    }

    @Override
    public EValue<?> get(Object key) {
        return this.data.get(key);
    }

    @Override
    public EValue<?> put(String key, EValue<?> value) {
        return this.data.put(key, value);
    }

    @Override
    public EValue<?> remove(Object key) {
        return this.data.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends EValue<?>> m) {
        this.data.putAll(m);
    }

    @Override
    public void clear() {
        this.data.clear();
    }

    @Override
    public Set<String> keySet() {
        return this.data.keySet();
    }

    @Override
    public Collection<EValue<?>> values() {
        return this.data.values();
    }

    @Override
    public Set<Entry<String, EValue<?>>> entrySet() {
        return this.data.entrySet();
    }
}
