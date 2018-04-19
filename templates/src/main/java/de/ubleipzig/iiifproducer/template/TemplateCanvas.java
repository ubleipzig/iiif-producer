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

package de.ubleipzig.iiifproducer.template;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import de.ubleipzig.iiif.vocabulary.SCEnum;

import java.util.List;

/**
 * TemplateCanvas.
 *
 * @author christopher-johnson
 */
@JsonPropertyOrder({"@id", "@type", "label", "height", "width", "images"})
public class TemplateCanvas {

    @JsonProperty("@id")
    private String id;

    @JsonProperty("@type")
    private String type = SCEnum.Canvas.compactedIRI();

    @JsonProperty
    private String label;

    @JsonProperty
    private Integer height;

    @JsonProperty
    private Integer width;

    @JsonProperty
    private List<TemplateImage> images;

    /**
     *
     */
    public TemplateCanvas() {
    }

    /**
     * @param label String
     */
    public void setCanvasLabel(final String label) {
        this.label = label;
    }

    /**
     * @param height Integer
     */
    public void setCanvasHeight(final Integer height) {
        this.height = height;
    }

    /***
     *
     * @param width Integer
     */
    public void setCanvasWidth(final Integer width) {
        this.width = width;
    }

    /**
     * @param images List
     */
    public void setCanvasImages(final List<TemplateImage> images) {
        this.images = images;
    }

    /**
     * @return String
     */
    @JsonIgnore
    public String getCanvasId() {
        return this.id;
    }

    /**
     * @param id String
     */
    public void setCanvasId(final String id) {
        this.id = id;
    }
}


