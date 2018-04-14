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

import static java.nio.file.Paths.get;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ImageManifestTest {
    private static final Config config = new Config();
    private final String path = get(".").toAbsolutePath().normalize().getParent().toString();

    @BeforeAll
    static void setup() {
        final String path = get(".").toAbsolutePath().normalize().getParent().toString();
        final String sourceFile = path + "/xml-doc/src/test/resources/mets/BlhDie_004285964.xml";
        config.setInputFile(sourceFile);
        config.setTitle("ImageManifestTest");
    }

    @Test
    void testBuildImageDimensionManifest() {
        final IIIFProducer producer = new IIIFProducer(config);
        final String imageSourceDir = path + "/image/src/test/resources";
        producer.buildImageDimensionManifest(imageSourceDir);
    }

    @Test
    void testFileNotFoundException() {
        final IIIFProducer producer = new IIIFProducer(config);
        final String imageSourceDir = path + "/image/src/test/non-existing-directory";
        assertThrows(RuntimeException.class, () -> {
            producer.buildImageDimensionManifest(imageSourceDir);
        });
    }
}
