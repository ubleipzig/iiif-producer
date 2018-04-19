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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import de.ubleipzig.iiif.vocabulary.IIIFEnum;

/**
 * TemplateService.
 *
 * @author christopher-johnson
 */
@JsonPropertyOrder({"@context", "@id", "profile"})
public class TemplateService {

    @JsonProperty("@context")
    private String context = IIIFEnum.IMAGE_CONTEXT.IRIString();

    @JsonProperty("@id")
    private String id;

    @JsonProperty
    private String profile = IIIFEnum.SERVICE_PROFILE.IRIString();

    /**
     * @param id String
     */
    public TemplateService(final String id) {
        this.id = id;
    }
}
