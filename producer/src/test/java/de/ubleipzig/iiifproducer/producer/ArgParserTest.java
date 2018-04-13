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

package de.ubleipzig.iiifproducer.producer;

import static de.ubleipzig.iiifproducer.producer.Constants.MANIFEST_HTTP_DIR;
import static java.nio.file.Paths.get;

import java.io.File;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * ArgParserTest.
 *
 * @author christopher-johnson
 */
class ArgParserTest {

    private static String pid;
    private static String testFileSource;
    private ArgParser parser;

    @BeforeAll
    static void init() {
        final String path = get(".").toAbsolutePath().normalize().getParent().toString();
        testFileSource = path + "/xml-doc/src/test/resources/mets/BlhDie_004285964.xml";
        pid = "producer-test-" + UUID.randomUUID().toString();
    }

    @Test
    void testArgs() {
        parser = new ArgParser();
        final String[] args;
        if (!new File(MANIFEST_HTTP_DIR).exists()) {

            args = new String[]{"-v", "004285964", "-t", "BlhDie_004285964", "-i", testFileSource, "-o",
                    "/tmp/" + pid + ".json"};
        } else {
            args = new String[]{"-v", "021340072", "-t", "BlhDie_004285964", "-i", testFileSource, "-o",
                    MANIFEST_HTTP_DIR + pid + ".json"};
        }
        final ManifestBuilderProcess processor = parser.init(args);
        processor.run();
    }
}
