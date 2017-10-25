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

import static org.ubl.iiifproducer.vocabulary.SC._Manifest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;

/**
 * TemplateBody.
 *
 * @author christopher-johnson
 */
@JsonPropertyOrder(
        {"@context", "@id", "@type", "label", "attribution", "logo", "related", "metadata",
                "sequences", "structures"})
public class TemplateBody {

    @JsonProperty("@context")
    private String context;

    @JsonProperty("@id")
    private String id;

    @JsonProperty("@type")
    private String type = _Manifest;

    @JsonProperty("label")
    private String label = "unnamed";

    @JsonProperty("attribution")
    private String attribution = "Provided by Leipzig University";

    @JsonProperty("logo")
    private String logo = "http://iiif.ub.uni-leipzig.de/ubl-logo.png";

    @JsonProperty("related")
    private List<String> related;

    @JsonProperty("metadata")
    private List<TemplateMetadata> metadata;

    @JsonProperty("sequences")
    private List<TemplateSequence> sequences;

    @JsonProperty("structures")
    private List<TemplateStructure> structures;

    public TemplateBody() {
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setAttribution(String attribution) {
        this.attribution = attribution;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setRelated(List<String> related) {
        this.related = related;
    }

    public void setSequences(List<TemplateSequence> sequences) {
        this.sequences = sequences;
    }

    public void setMetadata(List<TemplateMetadata> metadata) {
        this.metadata = metadata;
    }

    public void setStructures(List<TemplateStructure> structures) {
        this.structures = structures;
    }

}

