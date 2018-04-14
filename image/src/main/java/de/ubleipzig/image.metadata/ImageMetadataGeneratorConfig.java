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
/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.ubleipzig.image.metadata;

/**
 * ImageMetadataGeneratorConfig.
 *
 * @author christopher-johnson
 */
public class ImageMetadataGeneratorConfig {


    private String imageSourceDir;
    private String imageMetadataFilePath;
    private String dimensionManifestFilePath;
    private String dimensionManifest;

    /**
     * getImageSourceDir.
     *
     * @return {@link String}
     */
    public String getImageSourceDir() {
        return this.imageSourceDir;
    }

    /**
     * setImageSourceDir.
     *
     * @param imageSourceDir imageSourceDir
     */
    public final void setImageSourceDir(final String imageSourceDir) {
        this.imageSourceDir = imageSourceDir;
    }

    /**
     * getDimensionManifestFilePath.
     *
     * @return {@link String}
     */
    public String getDimensionManifestFilePath() {
        return this.dimensionManifestFilePath;
    }

    /**
     * setDimensionManifestFilePath.
     *
     * @param dimensionManifestFilePath dimensionManifestFilePath
     */
    public final void setDimensionManifestFilePath(final String dimensionManifestFilePath) {
        this.dimensionManifestFilePath = dimensionManifestFilePath;
    }

    /**
     * getDimensionManifest.
     *
     * @return {@link String}
     */
    public final String getDimensionManifest() {
        return this.dimensionManifest;
    }

    /**
     * setDimensionManifest.
     *
     * @param dimensionManifest dimensionManifest
     */
    public final void setDimensionManifest(final String dimensionManifest) {
        this.dimensionManifest = dimensionManifest;
    }


    /**
     * getImageMetadataFilePath.
     *
     * @return {@link String}
     */
    public String getImageMetadataFilePath() {
        return this.imageMetadataFilePath;
    }

    /**
     * setMetadataFile.
     *
     * @param imageMetadataFilePath imageMetadataFilePath
     */
    public final void setImageMetadataFilePath(final String imageMetadataFilePath) {
        this.imageMetadataFilePath = imageMetadataFilePath;
    }

}
