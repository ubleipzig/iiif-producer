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

package de.ubleipzig.image.metadata.templates;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * ImageMetadataManifest.
 *
 * @author christopher-johnson
 */
public class ImageDimensionManifest {

    @JsonProperty
    String collection;

    @JsonProperty
    List<ImageDimensions> images;

    /**
     * getCollection.
     *
     * @return String
     */
    @JsonIgnore
    public String getCollection() {
        return this.collection;
    }

    /**
     * setCollection.
     *
     * @param collection String
     */
    public void setCollection(final String collection) {
        this.collection = collection;
    }

    /**
     * getImageMetadata.
     *
     * @return List
     */
    @JsonIgnore
    public List<ImageDimensions> getImageMetadata() {
        return this.images;
    }

    /**
     * setImageMetadata.
     *
     * @param images List
     */
    public void setImageMetadata(final List<ImageDimensions> images) {
        this.images = images;
    }
}
