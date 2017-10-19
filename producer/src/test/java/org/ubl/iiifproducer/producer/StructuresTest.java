package org.ubl.iiifproducer.producer;

import static java.nio.file.Paths.get;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.ubl.iiifproducer.template.TemplateStructure;

/**
 * StructuresTest.
 *
 * @author christopher-johnson
 */
class StructuresTest {
    private static String sourceFile;

    @BeforeAll
    static void testBuildStructures() throws IOException {
        String path = get(".").toAbsolutePath().normalize().getParent().toString();
        sourceFile = path + "/xml-doc/src/test/resources/mets/BlhDie_004285964.xml";
    }

    @Test
    void buildStructures() throws IOException {
        Config config = new Config();
        config.setInputFile(sourceFile);
        config.setTitle("BlhDie_004285964");
        config.setOutputFile("/tmp/test.json");
        config.setViewId("004285964");
        MetsAccessor mets = new MetsImpl(config);
        List<TemplateStructure> structures = mets.buildStructures();
        assertTrue(structures.get(0) != null);
    }

    @Test
    void buildStructureList() {
    }

}

