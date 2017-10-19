package org.ubl.iiifproducer.producer;

import static java.nio.file.Paths.get;

import java.io.IOException;
import org.junit.jupiter.api.Test;

/**
 * AppTest.
 *
 * @author christopher-johnson
 */
class AppTest {

    @Test
    void testApp() throws IOException {
        String path = get(".").toAbsolutePath().normalize().getParent().toString();
        String sourceFile = path + "/xml-doc/src/test/resources/mets/BlhDie_004285964.xml";

        Config config = new Config();
        config.setInputFile(sourceFile);
        config.setTitle("BlhDie_004285964");
        config.setOutputFile("/tmp/test.json");
        config.setViewId("004285964");
        new IIIFProducer(config);
    }
}

