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

package org.ubl.iiifproducer.image.templates;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * ImageDimensions.
 *
 * @author christopher-johnson
 */
@JsonPropertyOrder({"filename", "digest", "height", "width"})
public class ImageDimensions {

    @JsonProperty
    String filename;

    @JsonProperty
    Integer height;

    @JsonProperty
    Integer width;

    @JsonProperty
    private String digest;

    /**
     * getFilename.
     *
     * @return String
     */
    public String getFilename() {
        return this.filename;
    }

    /**
     * setFilename.
     *
     * @param filename String
     */
    public void setFilename(final String filename) {
        this.filename = filename;
    }

    /**
     * getDigest.
     *
     * @return String
     */
    @JsonIgnore
    public String getDigest() {
        return this.digest;
    }

    /**
     * setDigest.
     *
     * @param digest String
     */
    public void setDigest(final String digest) {
        this.digest = digest;
    }

    /**
     * getHeight.
     *
     * @return Integer
     */
    public Integer getHeight() {
        return this.height;
    }

    /**
     * setHeight.
     *
     * @param height Integer
     */
    public void setHeight(final Integer height) {
        this.height = height;
    }

    /**
     * getWidth.
     *
     * @return Integer
     */
    public Integer getWidth() {
        return this.width;
    }

    /**
     * setWidth.
     *
     * @param width Integer
     */
    public void setWidth(final Integer width) {
        this.width = width;
    }

}
