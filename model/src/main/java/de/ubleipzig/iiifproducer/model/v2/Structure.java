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

package de.ubleipzig.iiifproducer.model.v2;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import de.ubleipzig.iiif.vocabulary.SCEnum;
import de.ubleipzig.iiifproducer.model.Metadata;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * TemplateStructure.
 *
 * @author christopher-johnson
 */
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({"@id", "@type", "label", "viewingHint", "ranges", "canvases"})
public class Structure {

    @JsonProperty("attribution")
    private String attribution;
    @JsonProperty("canvases")
    private List<String> canvases;
    @JsonProperty("description")
    private String description;
    @JsonProperty("@id")
    private String id;
    @JsonProperty("label")
    private Object label;
    @JsonProperty("license")
    private String license;
    @JsonProperty("logo")
    private String logo;
    @JsonProperty("metadata")
    private List<Metadata> metadata;
    @JsonProperty("ranges")
    private List<String> ranges;
    @Builder.Default
    @JsonProperty("@type")
    private String type = SCEnum.Range.compactedIRI();
    @JsonProperty("viewingHint")
    private String viewingHint;
    @JsonProperty
    private String within;
}
