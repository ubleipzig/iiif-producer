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

import de.ubleipzig.iiif.vocabulary.SCEnum;

import java.util.List;

/**
 * TemplateManifest.
 *
 * @author christopher-johnson
 */
@JsonPropertyOrder({"@context", "@id", "@type", "label", "license", "attribution", "logo", "related", "metadata",
        "sequences", "structures"})
public class TemplateManifest {

    @JsonProperty("@context")
    private String context;

    @JsonProperty("@id")
    private String id;

    @JsonProperty("@type")
    private String type = SCEnum.Manifest.compactedIRI();

    @JsonProperty("label")
    private String label = "unnamed";

    @JsonProperty("license")
    private String license;

    @JsonProperty("attribution")
    private String attribution;

    @JsonProperty("logo")
    private String logo;

    @JsonProperty("related")
    private List<String> related;

    @JsonProperty("metadata")
    private List<TemplateMetadata> metadata;

    @JsonProperty("sequences")
    private List<TemplateSequence> sequences;

    @JsonProperty("structures")
    private List<TemplateStructure> structures;

    /**
     * TemplateManifest.
     */
    public TemplateManifest() {
    }

    /**
     * @param context String
     */
    public void setContext(final String context) {
        this.context = context;
    }

    /**
     * @param id String
     */
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * @param type String
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * @param label String
     */
    public void setLabel(final String label) {
        this.label = label;
    }

    /**
     * @param license String
     */
    public void setLicense(final String license) {
        this.license = license;
    }

    /**
     * @param attribution String
     */
    public void setAttribution(final String attribution) {
        this.attribution = attribution;
    }

    /**
     * @param logo String
     */
    public void setLogo(final String logo) {
        this.logo = logo;
    }

    /**
     * @param related List
     */
    public void setRelated(final List<String> related) {
        this.related = related;
    }

    /**
     * @param sequences List
     */
    public void setSequences(final List<TemplateSequence> sequences) {
        this.sequences = sequences;
    }

    /**
     * @param metadata List
     */
    public void setMetadata(final List<TemplateMetadata> metadata) {
        this.metadata = metadata;
    }

    /**
     * @param structures List
     */
    public void setStructures(final List<TemplateStructure> structures) {
        this.structures = structures;
    }
}

