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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;

/**
 * TemplateStructure.
 *
 * @author christopher-johnson
 */
@JsonPropertyOrder({"@id", "@type", "label", "ranges", "canvases"})
public class TemplateStructure {

    @JsonProperty("@id")
    private String id;

    @JsonProperty("@type")
    private String type;

    @JsonProperty("label")
    private String label;

    @JsonProperty("ranges")
    private List<String> ranges;

    @JsonProperty("canvases")
    private List<String> canvases;

    public TemplateStructure() {
        this.id = id;
        this.type = type;
        this.label = label;
        this.ranges = ranges;
        this.canvases = canvases;
    }

    public void setStructureType(String structureType) {
        this.type = structureType;
    }

    public void setCanvases(List<String> canvases) {
        this.canvases = canvases;
    }

    @JsonIgnore
    public String getStructureLabel() {
        return this.label;
    }

    public void setStructureLabel(String structureLabel) {
        this.label = structureLabel;
    }

    @JsonIgnore
    public String getStructureId() {
        return this.id;
    }

    public void setStructureId(String id) {
        this.id = id;
    }

    @JsonIgnore
    public List<String> getRanges() {
        return this.ranges;
    }

    public void setRanges(List<String> ranges) {
        this.ranges = ranges;
    }
}
