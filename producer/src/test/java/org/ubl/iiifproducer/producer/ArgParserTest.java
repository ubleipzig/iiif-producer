/*
 * IIIFProducer
 * Copyright (C) 2017 Leipzig University Library <info@ub.uni-leipzig.de>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package org.ubl.iiifproducer.producer;

import static java.nio.file.Paths.get;
import static org.ubl.iiifproducer.producer.Constants.MANIFEST_HTTP_DIR;

import java.io.File;
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
        final String[] args;
        if (! new File(MANIFEST_HTTP_DIR).exists()) {
            String path = get(".").toAbsolutePath().normalize().getParent().toString();
             args = new String[]{"-v", "004285964", "-t", "BlhDie_004285964", "-i",
                     path + "/xml-doc/src/test/resources/mets/MS_187.xml",
                     "-o", "/tmp/test33.json"};
        } else {
            args = new String[]{"-v", "MS_187_tif", "-t", "test-manifest", "-i",
                    "/mnt/serialization/binaries/BntItin_021340072.xml",
                    "-o", MANIFEST_HTTP_DIR + "/test4.json"};
        }
        final ManifestBuilderProcess processor = parser.init(args);
        processor.run();
    }
}
