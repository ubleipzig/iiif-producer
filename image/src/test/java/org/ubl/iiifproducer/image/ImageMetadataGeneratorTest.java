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

import java.util.List;

import org.junit.jupiter.api.Test;
import org.ubl.iiifproducer.image.templates.ImageDimensions;

/**
 * ImageMetadataGeneratorTest.
 *
 * @author christopher-johnson
 */
public class ImageMetadataGeneratorTest {
    private String path = get(".").toAbsolutePath().normalize().getParent().toString();
    private String imageSourceDir = path + "/image/src/test/resources/";

    @Test
    void buildImageMetadataManifestTest() {
        final ImageMetadataGenerator generator = new ImageMetadataGenerator(imageSourceDir);
        System.out.println(generator.buildImageMetadataManifest());
    }

    @Test
    void buildImageDimensions() {
        final ImageMetadataGenerator generator = new ImageMetadataGenerator(imageSourceDir);
        final List<ImageDimensions> dimList = generator.buildImageDimensions();
        System.out.println(serialize(dimList).orElse(null));
    }
}
