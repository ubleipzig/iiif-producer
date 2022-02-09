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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import de.ubleipzig.iiif.vocabulary.SCEnum;
import de.ubleipzig.iiifproducer.model.Metadata;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Manifest.
 *
 * @author christopher-johnson
 */
@Builder
@Setter
@Getter
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({"@context", "@id", "@type", "label", "license", "attribution", "logo", "related", "metadata",
        "sequences", "service"})
public class Manifest {

    @JsonProperty
    private String attribution;
    @JsonIgnoreProperties({"rendering", "logo"})

    @JsonProperty("@context")
    private String context;
    @JsonProperty
    private Object description;
    @JsonProperty("@id")
    private String id;
    @JsonProperty("label")
    private String label;
    @JsonProperty("license")
    @JsonFormat(with = JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)
    private List<String> license;
    @JsonProperty("logo")
    private Object logo;
    @JsonProperty("metadata")
    private List<Metadata> metadata;
    @JsonProperty
    private String navDate;
    @JsonProperty("related")
    private Object related;
    @JsonProperty
    private Object rendering;
    @JsonProperty
    private Object seeAlso;
    @JsonProperty("sequences")
    private List<Sequence> sequences;

    @JsonProperty("service")
    private Object service;
    @JsonProperty("structures")
    private List<Structure> structures;
    @JsonProperty
    private Object thumbnail;
    @Builder.Default
    @JsonProperty("@type")
    private String type = SCEnum.Manifest.compactedIRI();
    @JsonProperty
    private String viewingDirection;
    @JsonProperty
    private String viewingHint;
    @JsonProperty
    private String within;

}


