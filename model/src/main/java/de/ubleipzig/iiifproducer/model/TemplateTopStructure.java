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

package de.ubleipzig.iiifproducer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import de.ubleipzig.iiif.vocabulary.SCEnum;

import java.util.List;

/**
 * TemplateTopStructure.
 *
 * @author christopher-johnson
 */
@JsonPropertyOrder({"@id", "@type", "label", "viewingHint", "ranges"})
public class TemplateTopStructure extends TemplateStructure {

    @JsonProperty("@id")
    private String id;

    @JsonProperty("@type")
    private String type = SCEnum.Range.compactedIRI();

    @JsonProperty("label")
    private String label;

    @JsonProperty("viewingHint")
    private String viewingHint = "top";

    @JsonIgnore
    private List<TemplateMember> members;

    @JsonIgnore
    private List<String> canvases;

    @JsonProperty("ranges")
    private List<String> ranges;

    /**
     *
     */
    public TemplateTopStructure() {
    }

    /**
     * @param id String
     */
    public void setStructureId(final String id) {
        this.id = id;
    }

    /**
     * @param structureLabel String
     */
    public void setStructureLabel(final String structureLabel) {
        this.label = structureLabel;
    }

    /**
     * @param members List
     */
    public void setMembers(final List<TemplateMember> members) {
        this.members = members;
    }

    /**
     * @return List
     */
    public List<String> getRanges() {
        return this.ranges;
    }

    /**
     * @param ranges List
     */
    public void setRanges(final List<String> ranges) {
        this.ranges = ranges;
    }

}
