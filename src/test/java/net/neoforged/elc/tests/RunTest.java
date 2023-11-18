package net.neoforged.elc.tests;

import net.neoforged.elc.configs.LaunchConfig;
import org.junit.jupiter.api.Assertions;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

public abstract class RunTest {
    protected final void assertExpectedConfig(LaunchConfig config, String name) throws XMLStreamException, ParserConfigurationException, IOException, SAXException {
        final StringWriter writer = new StringWriter();
        config.write(writer);
        final var factory = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        final var original = factory.parse(new ByteArrayInputStream(writer.toString().getBytes(StandardCharsets.UTF_8)));
        final var in = RunTest.class.getResourceAsStream("/" + name);
        final var fromFile = factory.parse(in);
        in.close();

        original.normalizeDocument();
        fromFile.normalizeDocument();
        Assertions.assertTrue(original.isEqualNode(fromFile), "Documents did not match!");
    }
}
