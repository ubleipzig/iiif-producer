package org.ubl.iiifproducer.producer;

import static java.lang.System.out;
import static java.nio.file.Paths.get;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.ubl.iiifproducer.template.ManifestSerializer.serialize;

import java.io.IOException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.ubl.iiifproducer.template.TemplateBody;

/**
 * SetNullableMetadataTest.
 *
 * @author christopher-johnson
 */
public class SetNullableMetadataTest {
    private static String sourceFile;

    @BeforeAll
    static void testBuildStructures() throws IOException {
        String path = get(".").toAbsolutePath().normalize().getParent().toString();
        sourceFile = path + "/xml-doc/src/test/resources/mets/BlhDie_004285964.xml";
    }

    @Test
    void testSetManuscriptMetadata() throws IOException {
        Config config = new Config();
        TemplateBody body = new TemplateBody();
        config.setInputFile(sourceFile);
        config.setTitle("BlhDie_004285964");
        config.setOutputFile("/tmp/test.json");
        config.setViewId("004285964");
        MetsAccessor mets = new MetsImpl(config);
        mets.setHandschriftMetadata(body);
        final Optional<String> json = serialize(body);
        assertTrue(json.isPresent());
        out.println(json.get());
    }

    @Test
    void testSetMetadata() throws IOException {
        Config config = new Config();
        TemplateBody body = new TemplateBody();
        config.setInputFile(sourceFile);
        config.setTitle("BlhDie_004285964");
        config.setOutputFile("/tmp/test.json");
        config.setViewId("004285964");
        MetsAccessor mets = new MetsImpl(config);
        mets.setMetadata(body);
        final Optional<String> json = serialize(body);
        assertTrue(json.isPresent());
        out.println(json.get());
    }
}
