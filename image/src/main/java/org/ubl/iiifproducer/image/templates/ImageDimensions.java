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

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ImageDimensions.
 *
 * @author christopher-johnson
 */
public class ImageDimensions {

    @JsonProperty
    String filename;

    @JsonProperty
    Integer height;

    @JsonProperty
    Integer width;

    /**
     *
     * @param filename String
     */
    public void setFilename(final String filename) {
        this.filename = filename;
    }

    /**
     *
     * @return String
     */
    public String getFilename() {
        return this.filename;
    }

    /**
     *
     * @param height Integer
     */
    public void setHeight(final Integer height) {
        this.height = height;
    }

    /**
     *
     * @return Integer
     */
    public Integer getHeight() {
        return this.height;
    }

    /**
     *
     * @param width Integer
     */
    public void setWidth(final Integer width) {
        this.width = width;
    }

    /**
     *
     * @return Integer
     */
    public Integer getWidth() {
        return this.width;
    }

}
