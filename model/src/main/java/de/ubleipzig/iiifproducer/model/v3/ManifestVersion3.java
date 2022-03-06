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

package de.ubleipzig.iiifproducer.model.v3;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Builder
@Setter
@Getter
@AllArgsConstructor
@JsonPropertyOrder({"@context", "id", "type", "label", "summary", "thumbnail", "viewingDirection", "behavior",
        "navDate", "rights", "requiredStatement", "logo", "homepage", "seeAlso", "metadata", "items", "structures"})
public class ManifestVersion3 {

    @JsonProperty
    private List<String> behavior;
    @JsonProperty("@context")
    private List<String> context;
    @JsonProperty
    private List<Homepage> homepage;
    @JsonProperty("id")
    private String id;
    @JsonProperty
    private List<CanvasVersion3> items;
    @JsonProperty
    private Map<String, List<String>> label;
    @JsonProperty
    private Logo logo;
    @JsonProperty
    private List<MetadataVersion3> metadata;
    @JsonProperty
    private String navDate;
    @JsonProperty
    private MetadataVersion3 requiredStatement;
    @JsonProperty
    private String rights;
    @JsonProperty
    private List<SeeAlso> seeAlso;
    @JsonProperty
    private List<Item> structures;
    @JsonProperty
    private Map<String, List<String>> summary;
    @JsonProperty("type")
    private String type;
    @JsonProperty
    private String viewingDirection;

    @Builder
    @Setter
    @Getter
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({"id", "type", "service"})
    public static class Logo {
        @JsonProperty("id")
        private String id;
        @JsonProperty
        private List<ServiceVersion3> service;
        @JsonProperty("type")
        private String type;

        public List<ServiceVersion3> getService() {
            return service;
        }

        public void setService(List<ServiceVersion3> service) {
            this.service = service;
        }
    }

}
