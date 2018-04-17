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

package de.ubleipzig.image.metadata;

import de.ubleipzig.image.metadata.templates.ImageDimensionManifest;
import de.ubleipzig.image.metadata.templates.ImageDimensions;

import java.util.List;

public interface ImageMetadataService {


    /**
     * buildImageMetadataManifest.
     *
     * @return String
     */
    String buildImageMetadataManifest();

    /**
     * buildDimensionManifest.
     *
     * @param imageManifest imageManifest
     * @return List
     */
    ImageDimensionManifest buildDimensionManifest(String imageManifest);

    /**
     * buildDimensionManifestFromFile.
     *
     * @return List
     */
    List<ImageDimensions> unmarshallDimensionManifestFromFile();

    /**
     * buildDimensionManifestFromRemote.
     *
     * @return List
     */
    List<ImageDimensions> unmarshallDimensionManifestFromRemote();

    /**
     * @param imageMetadataManifest imageMetadataManifest
     * @return List
     */
    List<ImageDimensions> buildDimensionManifestListFromImageMetadataManifest(String imageMetadataManifest);

    /**
     * buildDimensionManifest.
     *
     * @return List
     */
    ImageDimensionManifest build();

    /**
     * buildDimensionManifest.
     *
     * @return List
     */
    List<String> getFilenamesFromManifest();

    /**
     * @param dimManifest ImageDimensionManifest
     * @param outputPath outputPath
     */
    void serializeImageDimensionManifest(ImageDimensionManifest dimManifest, String outputPath);
}
