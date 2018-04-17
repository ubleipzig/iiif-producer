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

package de.ubleipzig.scb.image.metadata;

import static de.ubleipzig.image.metadata.JsonSerializer.serialize;
import static org.junit.jupiter.api.Assertions.assertEquals;

import de.ubleipzig.image.metadata.FileBinaryService;
import de.ubleipzig.image.metadata.ImageMetadataGeneratorConfig;
import de.ubleipzig.image.metadata.ImageMetadataServiceImpl;
import de.ubleipzig.image.metadata.templates.ImageDimensionManifest;
import de.ubleipzig.image.metadata.templates.ImageDimensions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * ImageMetadataServiceTest.
 *
 * @author christopher-johnson
 */
public class ImageMetadataServiceTest {

    private static String imageManifestPid;
    private static String dimensionManifestPid;
    private static URL resource;
    private static String imageSourceDir;

    //Note: this is for testing large directories, only enable if you can wait ...
    private static String gigabyteSourceDir = "file:///media/christopher/OVAUBIMG/UBiMG/images/ubleipzig_sk";

    @BeforeAll
    static void init() {
        {
            try {
                resource = new URL(gigabyteSourceDir);
                System.out.println(Paths.get(resource.toURI()));
                if (Paths.get(resource.toURI()).toFile().exists()) {
                    imageSourceDir = Paths.get(resource.toURI()).toFile().getPath();
                } else {
                    imageSourceDir = Paths.get(
                            ImageMetadataServiceTest.class.getResource("/00000001.jpg").toURI()).toFile().getParent();
                }
            } catch (URISyntaxException | MalformedURLException e) {
                e.printStackTrace();
            }
        }
        imageManifestPid = "image-manifest-test-" + UUID.randomUUID().toString();
        dimensionManifestPid = "dimension-manifest-test-" + UUID.randomUUID().toString();
    }

    @Test
    void testBuildImageMetadataManifest() {
        final ImageMetadataGeneratorConfig config = new ImageMetadataGeneratorConfig();
        config.setImageSourceDir(imageSourceDir);
        final ImageMetadataServiceImpl generator = new ImageMetadataServiceImpl(config);
        try {
            Files.write(Paths.get("/tmp/" + imageManifestPid), generator.buildImageMetadataManifest().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Disabled
    void testBuildImageDimensionsFromManifestFile() {
        final ImageMetadataGeneratorConfig config = new ImageMetadataGeneratorConfig();
        config.setImageSourceDir(imageSourceDir);
        config.setImageMetadataFilePath("/tmp/" + imageManifestPid);
        final ImageMetadataServiceImpl generator = new ImageMetadataServiceImpl(config);
        final ImageDimensionManifest dimManifest = generator.buildDimensionManifest(null);
        final String out = serialize(dimManifest).orElse("");
        System.out.println(out);
        try {
            Files.write(Paths.get("/tmp/" + dimensionManifestPid), out.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testBuildImageDimensionsFromManifest() {
        final ImageMetadataGeneratorConfig config = new ImageMetadataGeneratorConfig();
        config.setImageSourceDir(imageSourceDir);
        final ImageMetadataServiceImpl generator = new ImageMetadataServiceImpl(config);
        final ImageDimensionManifest dimManifest = generator.build();
        final String dimensionManifest = serialize(dimManifest).orElse("");
        System.out.println(dimensionManifest);
        try {
            Files.write(Paths.get("/tmp/" + dimensionManifestPid), dimensionManifest.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testBuildImageDimensionManifestListFromImageMetadataManifest() {
        final ImageMetadataGeneratorConfig config = new ImageMetadataGeneratorConfig();
        config.setImageSourceDir(imageSourceDir);
        final ImageMetadataServiceImpl generator = new ImageMetadataServiceImpl(config);
        final String imageMetadataManifest = generator.buildImageMetadataManifest();
        final List<ImageDimensions> dimList = generator.buildDimensionManifestListFromImageMetadataManifest(
                imageMetadataManifest);
        assertEquals(dimList.get(0).getFilename(), "00000001.jpg");
    }

    @Test
    void testGetFilenamesFromImageManifest() {
        final ImageMetadataGeneratorConfig config = new ImageMetadataGeneratorConfig();
        config.setImageSourceDir(imageSourceDir);
        final ImageMetadataServiceImpl generator = new ImageMetadataServiceImpl(config);
        final List<String> filenameList;
        filenameList = generator.getFilenamesFromManifest();
        System.out.println(filenameList);
    }

    @Test
    void testBase64Digest() throws IOException {
        final FileBinaryService service = new FileBinaryService();
        final Stream<Path> paths = Files.walk(Paths.get(imageSourceDir)).filter(Files::isRegularFile);
        paths.forEach(p -> {
            final File file = new File(String.valueOf(p.toAbsolutePath()));
            final InputStream targetStream;
            try {
                targetStream = new FileInputStream(file);
                System.out.println(service.digest("SHA-1", targetStream).orElse(null));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
}
