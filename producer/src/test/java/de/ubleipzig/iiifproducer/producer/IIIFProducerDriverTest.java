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

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class IIIFProducerDriverTest {

    private static String imageSourceDir;
    private static String testFileSource1;
    private static String testFileSource2;
    private static String pid;

    @BeforeAll
    static void init() {
        imageSourceDir = ArgParserTest.class.getResource("/MS_187_tif").getPath();
        testFileSource1 = ArgParserTest.class.getResource("/BlhDie_004285964.xml").getPath();
        testFileSource2 = ArgParserTest.class.getResource("/MS_187.xml").getPath();
        pid = "producer-test-" + UUID.randomUUID().toString();
    }

    @Test
    public void testStandardType() {
        final String[] args = new String[]{"-v", "004285964", "-x", testFileSource1, "-i", imageSourceDir, "-o",
                "/tmp/" + pid + ".json"};
        IIIFProducerDriver.main(args);
    }

    @Test
    public void testHandschriftType() {
        final String[] args = new String[]{"-v", "004285964", "-x", testFileSource2, "-i", imageSourceDir, "-o",
                "/tmp/" + pid + ".json"};
        IIIFProducerDriver.main(args);
    }
}
