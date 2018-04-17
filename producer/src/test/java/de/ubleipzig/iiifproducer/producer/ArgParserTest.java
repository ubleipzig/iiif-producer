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
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    private static String testFileSource1;
    private static String testFileSource2;
    private ArgParser parser;

    @BeforeAll
    static void init() {
        testFileSource1 = ArgParserTest.class.getResource("/MS_187.xml").getPath();
        testFileSource2 = ArgParserTest.class.getResource("/BlhDie_004285964.xml").getPath();
        pid = "producer-test-" + UUID.randomUUID().toString();
    }

    @Test
    void testRequiredArgs1() {
        parser = new ArgParser();
        final String[] args;
        args = new String[]{"-v", "004285964", "-t", "MS_187", "-i", testFileSource1, "-o", "/tmp/" + pid + ".json"};
        final ManifestBuilderProcess processor = parser.init(args);
        processor.run();
    }

    @Test
    void testRequiredArgs2() {
        parser = new ArgParser();
        final String[] args;
        args = new String[]{"-v", "004285964", "-t", "BlhDie_004285964", "-i", testFileSource2, "-o", "/tmp/" + pid +
                ".json"};
        final ManifestBuilderProcess processor = parser.init(args);
        processor.run();
    }

    @Test
    void testOptionalArgs() {
        parser = new ArgParser();
        final String[] args;
        args = new String[]{"-v", "021340072", "-t", "MS_187", "-i", testFileSource1, "-o", "/tmp/" + pid + ".json",
                "-s"};
        final ManifestBuilderProcess processor = parser.init(args);
        processor.run();
    }

    @Test
    void testInvalidArgs() {
        parser = new ArgParser();
        final String[] args;
        args = new String[]{"-q", "021340072", "-y", "MS_187", "-l", testFileSource1, "-p", MANIFEST_HTTP_DIR + pid +
                ".json"};
        assertThrows(RuntimeException.class, () -> {
            parser.parseConfiguration(args);
        });
    }
}
