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

/**
 * ImageMetadataTag.
 *
 * @author christopher-johnson
 */
public class ImageMetadataTag {

    @JsonProperty
    String tagName;

    @JsonProperty
    String tagDescription;

    /**
     * getTagName.
     *
     * @return String
     */
    @JsonIgnore
    public String getTagName() {
        return this.tagName;
    }

    /**
     * setTagName.
     *
     * @param tagName String
     */
    public void setTagName(final String tagName) {
        this.tagName = tagName;
    }

    /**
     * getTagDescription.
     *
     * @return String
     */
    @JsonIgnore
    public String getTagDescription() {
        return this.tagDescription;
    }

    /**
     * setTagDescription.
     *
     * @param tagDescription String
     */
    public void setTagDescription(final String tagDescription) {
        this.tagDescription = tagDescription;
    }
}
