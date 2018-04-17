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

import static de.ubleipzig.iiifproducer.producer.UUIDType5.NAMESPACE_URL;
import static java.io.File.separator;
import static java.nio.file.Paths.get;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.ubleipzig.image.metadata.templates.ImageDimensions;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ImageManifestTest {

    private static final Config config = new Config();
    private static final String path = get(".").toAbsolutePath().normalize().getParent().toString();
    private String pid;

    private String getImageManifestPid() {
        return "image-manifest-" + UUIDType5.nameUUIDFromNamespaceAndString(NAMESPACE_URL, config.getTitle()) + ".json";
    }

    @BeforeEach
    void setup() {
        pid = "image-manifest-test-" + UUID.randomUUID().toString();
    }

    @Test
    void testGetExistingDimensionManifestFromOutputPath() {
        config.setTitle("BlhDie_004285964");
        config.setSerializeImageManifest(false);
        final IIIFProducer producer = new IIIFProducer(config);
        final String dimResource = separator + getImageManifestPid();
        final String dimensionManifestOutputPath = ImageManifestTest.class.getResource(dimResource).getPath();
        final List<ImageDimensions> dimlist = producer.getImageDimensions(null, dimensionManifestOutputPath);
        assertEquals(dimlist.get(1).getDigest(), "+IjI1aM57A/e4jY2HHBxf47rLHo=");
    }

    @Test
    void testGetDimensionsFromBinariesNoSerialization() {
        final String sourceFile = ImageManifestTest.class.getResource("/MS_187.xml").getPath();
        config.setInputFile(sourceFile);
        config.setTitle("ImageManifestTest");
        config.setSerializeImageManifest(false);
        final IIIFProducer producer = new IIIFProducer(config);
        final String imageSourceDir = ImageManifestTest.class.getResource("/MS_187_tif").getPath();
        final List<ImageDimensions> dimlist = producer.getImageDimensions(imageSourceDir, null);
        assertEquals(dimlist.get(1).getFilename(), "00000002.tif");
    }

    @Test
    void testSerializeManifestandGetDimensions() {
        final String sourceFile = ImageManifestTest.class.getResource("/MS_187.xml").getPath();
        config.setInputFile(sourceFile);
        config.setTitle("ImageManifestTest");
        config.setSerializeImageManifest(true);
        final String imageManifestOutputPath = config.getBaseDir() + separator + getImageManifestPid();
        final IIIFProducer producer = new IIIFProducer(config);
        final String imageSourceDir = ImageManifestTest.class.getResource("/MS_187_tif").getPath();
        final List<ImageDimensions> dimlist = producer.getImageDimensions(imageSourceDir, imageManifestOutputPath);
        assertEquals(dimlist.get(1).getFilename(), "00000002.tif");
    }

    @Test
    void testFileNotFoundExceptionNoSerialization() {
        final IIIFProducer producer = new IIIFProducer(config);
        final String imageSourceDir = path + "/image/src/test/non-existing-directory";
        assertThrows(RuntimeException.class, () -> {
            producer.getImageDimensions(imageSourceDir, null);
        });
    }

    @Test
    void testFileNotFoundExceptionSerialization() {
        final IIIFProducer producer = new IIIFProducer(config);
        config.setSerializeImageManifest(true);
        final String imageSourceDir = path + "/image/src/test/non-existing-directory";
        assertThrows(RuntimeException.class, () -> {
            producer.getImageDimensions(imageSourceDir, null);
        });
    }
}
