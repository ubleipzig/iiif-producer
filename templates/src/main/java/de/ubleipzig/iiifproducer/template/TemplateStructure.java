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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import de.ubleipzig.iiif.vocabulary.SCEnum;

import java.util.List;

/**
 * TemplateStructure.
 *
 * @author christopher-johnson
 */
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({"@id", "@type", "label", "ranges", "canvases"})
public class TemplateStructure {

    @JsonProperty("@id")
    private String id;

    @JsonProperty("@type")
    private String type = SCEnum.Range.compactedIRI();

    @JsonProperty("label")
    private String label;

    @JsonProperty("ranges")
    private List<String> ranges;

    @JsonProperty("canvases")
    private List<String> canvases;

    @JsonProperty("metadata")
    private List<TemplateMetadata> metadata;

    /**
     *
     */
    public TemplateStructure() {
    }

    /**
     * @param structureType String
     */
    public void setStructureType(final String structureType) {
        this.type = structureType;
    }

    /**
     * @param canvases List
     */
    public void setCanvases(final List<String> canvases) {
        this.canvases = canvases;
    }

    /**
     * @return String
     */
    @JsonIgnore
    public String getStructureLabel() {
        return this.label;
    }

    /**
     * @param structureLabel String
     */
    public void setStructureLabel(final String structureLabel) {
        this.label = structureLabel;
    }

    /**
     * @return String
     */
    @JsonIgnore
    public String getStructureId() {
        return this.id;
    }

    /**
     * @param id String
     */
    public void setStructureId(final String id) {
        this.id = id;
    }

    /**
     * @return List
     */
    @JsonIgnore
    public List<String> getRanges() {
        return this.ranges;
    }

    /**
     * @param ranges List
     */
    public void setRanges(final List<String> ranges) {
        this.ranges = ranges;
    }

    /**
     * @return List
     */
    @JsonIgnore
    public List<TemplateMetadata> getMetadata() {
        return this.metadata;
    }

    /**
     * @param metadata List
     */
    public void setMetadata(final List<TemplateMetadata> metadata) {
        this.metadata = metadata;
    }
}
