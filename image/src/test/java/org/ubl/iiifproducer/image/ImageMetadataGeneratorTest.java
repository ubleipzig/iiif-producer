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

package org.ubl.iiifproducer.image;

import static java.nio.file.Paths.get;
import static org.ubl.iiifproducer.image.JsonSerializer.serialize;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.ubl.iiifproducer.image.templates.ImageDimensionManifest;


/**
 * ImageMetadataGeneratorTest.
 *
 * @author christopher-johnson
 */
public class ImageMetadataGeneratorTest {

    private static String imageManifestPid;
    private static String dimensionManifestPid;
    private String path = get(".").toAbsolutePath().normalize().getParent().toString();
    private String imageSourceDir = path + "/image/src/test/resources";

    @BeforeAll
    static void init() {
        imageManifestPid = "image-manifest-test-" + UUID.randomUUID().toString();
        dimensionManifestPid = "dimension-manifest-test-" + UUID.randomUUID().toString();
    }

    @Test
    void testBuildImageMetadataManifest() {
        final ImageMetadataGeneratorConfig config = new ImageMetadataGeneratorConfig();
        config.setImageSourceDir(imageSourceDir);
        final ImageMetadataGenerator generator = new ImageMetadataGenerator(config);
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
        config.setMetadataFilePath("/tmp/image-manifest-test-5a7024ae-3ba4-458e-bf61-3be5fc774119");
        final ImageMetadataGenerator generator = new ImageMetadataGenerator(config);
        final ImageDimensionManifest dimManifest = generator.buildDimensionManifest(null);
        try {
            Files.write(Paths.get("/tmp/" + dimensionManifestPid), serialize(dimManifest).orElse("").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testBuildImageDimensionsFromManifest() {
        final ImageMetadataGeneratorConfig config = new ImageMetadataGeneratorConfig();
        config.setImageSourceDir(imageSourceDir);
        final ImageMetadataGenerator generator = new ImageMetadataGenerator(config);
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
