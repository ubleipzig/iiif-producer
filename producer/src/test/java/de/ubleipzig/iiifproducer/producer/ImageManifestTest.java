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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.ubleipzig.image.metadata.templates.ImageDimensions;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ImageManifestTest {

    private static final Config config = new Config();
    private static String imageManifestPid;
    private static String imageSourceDir;
    private static String seed;

    @BeforeEach
    void init() {
        imageSourceDir = ImageManifestTest.class.getResource("/MS_187_tif").getPath();
        imageManifestPid = "image-manifest-" + UUIDType5.nameUUIDFromNamespaceAndString(
                NAMESPACE_URL, imageSourceDir) + ".json";
    }

    @Test
    void testGetDimensionsFromBinariesNoSerialization() {
        final String xmlFile = ImageManifestTest.class.getResource("/MS_187.xml").getPath();
        config.setXmlFile(xmlFile);
        config.setImageSourceDir(imageSourceDir);
        config.setSerializeImageManifest(false);
        final IIIFProducer producer = new IIIFProducer(config);
        final List<ImageDimensions> dimlist = producer.getImageDimensions(imageSourceDir, null);
        assertEquals("00000002.tif", dimlist.get(1).getFilename());
    }

    @Test
    void testGetDimensionsFromJP2NoSerialization() {
        final String xmlFile = ImageManifestTest.class.getResource("/MS_187.xml").getPath();
        final String imageSourceDir = ImageManifestTest.class.getResource("/jp2").getPath();
        config.setXmlFile(xmlFile);
        config.setSerializeImageManifest(false);
        config.setImageSourceDir(imageSourceDir);
        final IIIFProducer producer = new IIIFProducer(config);
        final List<ImageDimensions> dimlist = producer.getImageDimensions(imageSourceDir, null);
        assertEquals("00000039.jpx", dimlist.get(1).getFilename());
        assertEquals("2747", dimlist.get(1).getHeight().toString());
    }

    @Test
    void testSerializeManifestandGetDimensions() {
        final String xmlFile = ImageManifestTest.class.getResource("/MS_187.xml").getPath();
        config.setXmlFile(xmlFile);
        config.setImageSourceDir(imageSourceDir);
        config.setSerializeImageManifest(true);
        final String imageManifestOutputPath = config.getImageSourceDir() + separator + imageManifestPid;
        final IIIFProducer producer = new IIIFProducer(config);
        final List<ImageDimensions> dimlist = producer.getImageDimensions(imageSourceDir, imageManifestOutputPath);
        assertEquals( "00000002.tif", dimlist.get(1).getFilename());
    }

    @Test
    void testGetDimensionsFromUrl() throws URISyntaxException, MalformedURLException {
        final String xmlFile = ImageManifestTest.class.getResource("/MS_187.xml").getPath();
        config.setXmlFile(xmlFile);
        final IIIFProducer producer = new IIIFProducer(config);
        final String imageManifestUrl = ArgParserTest.class.getResource("/dimManifest.json").toURI().toURL().toString();
        final List<ImageDimensions> dimlist = producer.getImageDimensionsFromUrl(imageManifestUrl);
        assertEquals("00000002.jpx", dimlist.get(1).getFilename());
    }

    @Test
    void testFileNotFoundExceptionNoSerialization() {
        final IIIFProducer producer = new IIIFProducer(config);
        final String imageSourceDir = "/image/src/test/non-existing-directory";
        assertThrows(RuntimeException.class, () -> {
            producer.getImageDimensions(imageSourceDir, null);
        });
    }

    @Test
    void testFileNotFoundExceptionSerialization() {
        final IIIFProducer producer = new IIIFProducer(config);
        config.setSerializeImageManifest(true);
        final String imageSourceDir = "/image/src/test/non-existing-directory";
        assertThrows(RuntimeException.class, () -> {
            producer.getImageDimensions(imageSourceDir, null);
        });
    }
}
