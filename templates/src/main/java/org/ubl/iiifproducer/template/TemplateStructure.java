/*
 * IIIFProducer
 *
 * Copyright (C) 2017 Leipzig University Library <info@ub.uni-leipzig.de>
 *
 * @author Stefan Freitag <freitag@uni-leipzig.de>
 * @author Christopher Johnson <christopher_hanna.johnson@uni-leipzig.de>
 * @author Felix Kreißig <kreissig@ub.uni-leipzig.de>
 * @author Leander Seige <seige@ub.uni-leipzig.de>
 * @license http://opensource.org/licenses/gpl-2.0.php GNU GPLv2
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.ubl.iiifproducer.template;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;
import java.util.Objects;

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

    public void setStructureId(String id) {
        this.id = id;
    }

    public void setStructureLabel(String structureLabel) {
        this.label = structureLabel;
    }

    public void setStructureType(String structureType) {
        this.type = structureType;
    }

    public void setRanges(List<String> ranges) {
        this.ranges = ranges;
    }

    public void setCanvases(List<String> canvases) {
        this.canvases = canvases;
    }

    @JsonIgnore
    public String getStructureLabel() {
        return this.label;
    }

    @JsonIgnore
    public String getStructureId() {
        return this.id;
    }

    @JsonIgnore
    public List<String> getRanges() {
        return this.ranges;
    }
}
