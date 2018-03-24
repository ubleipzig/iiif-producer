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

package org.ubl.iiifproducer.template;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * TemplateResource.
 *
 * @author christopher-johnson
 */
@JsonPropertyOrder({"@id", "@type", "label", "format", "height", "width", "service"})
public class TemplateResource {

    @JsonProperty("@id")
    private String id = "";

    @JsonProperty("@type")
    private String type = "dctypes:Image";

    @JsonProperty
    private String label = "unnamed resource";

    @JsonProperty
    private String format = "image/jpeg";

    @JsonProperty
    private Integer height;

    @JsonProperty
    private Integer width;

    @JsonProperty
    private TemplateService service;

    /**
     *
     */
    public TemplateResource() {
    }

    /**
     *
     * @param id String
     */
    public void setResourceId(final String id) {
        this.id = id;
    }

    /**
     *
     * @param label String
     */
    public void setResourceLabel(final String label) {
        this.label = label;
    }

    /**
     *
     * @param format String
     */
    public void setResourceFormat(final String format) {
        this.format = format;
    }

    /**
     *
     * @param service TemplateService
     */
    public void setService(final TemplateService service) {
        this.service = service;
    }

    /**
     *
     * @param height Integer
     */
    public void setResourceHeight(final Integer height) {
        this.height = height;
    }

    /**
     *
     * @param width Integer
     */
    public void setResourceWidth(final Integer width) {
        this.width = width;
    }
}
