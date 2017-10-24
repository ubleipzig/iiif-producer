package org.ubl.iiifproducer.producer;

import static java.nio.file.Paths.get;

import java.io.IOException;
import org.junit.jupiter.api.Test;

/**
 * ArgParserTest.
 *
 * @author christopher-johnson
 */
class ArgParserTest {
    private ArgParser parser;

    @Test
    void testArgs() throws IOException {
        parser = new ArgParser();
        String path = get(".").toAbsolutePath().normalize().getParent().toString();
        final String[] args = new String[]{"-v", "004285964", "-t", "BlhDie_004285964", "-i",
                path + "/xml-doc/src/test/resources/mets"
                        + "/MS_187.xml",
                "-o", "/tmp/test2.json"};
        final ManifestBuilderProcess processor = parser.init(args);
        processor.run();
    }
}
