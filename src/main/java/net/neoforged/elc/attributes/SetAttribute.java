package net.neoforged.elc.attributes;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * A Set Attribute represents the E-Attribute type <code>setAttribute</code>.
 * Due to limitations set by Eclipse, it is explicitly a set of strings.
 * 
 * @param key    The E-Attribute key attribute.
 * @param values The internal set representing this E-Attribute.
 */
public record SetAttribute(String key, Set<EValue<?>> values) implements EAttribute, Set<EValue<?>> {

    /**
     * Constructs a {@link SetAttribute} backed by an empty {@link HashSet}.
     * 
     * @param key The key of the E-Attribute.
     */
    public SetAttribute(String key) {
        this(key, new HashSet<>());
    }

    @Override
    public void write(XMLStreamWriter writer, XMLOutputFactory outputFactory) throws XMLStreamException {
        writer.writeStartElement("setAttribute");
        writer.writeAttribute("key", key);
        for (EValue<?> entry : this.values) {
            writer.writeStartElement("setEntry");
            writer.writeAttribute("value", entry.serialize());
            writer.writeEndElement();
        }
        writer.writeEndElement();
    }

    @Override
    public int size() {
        return this.values.size();
    }

    @Override
    public boolean isEmpty() {
        return this.values.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.values.contains(o);
    }

    @Override
    public Iterator<EValue<?>> iterator() {
        return this.values.iterator();
    }

    @Override
    public Object[] toArray() {
        return this.values.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return this.values.toArray(a);
    }

    @Override
    public boolean add(EValue<?> e) {
        return this.values.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return this.values.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.values.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends EValue<?>> c) {
        return this.values.addAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return this.values.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return this.values.removeAll(c);
    }

    @Override
    public void clear() {
        this.values.clear();
    }
}
