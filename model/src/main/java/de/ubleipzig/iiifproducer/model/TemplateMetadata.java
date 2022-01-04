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

import java.util.List;

/**
 * TemplateMetadata.
 *
 * @author christopher-johnson
 */
@JsonPropertyOrder({"label", "value"})
public class TemplateMetadata {

    @JsonProperty("label")
    private String label;

    @JsonProperty("value")
    private String value;

    /**
     * @param label String
     * @param value String
     */
    public TemplateMetadata(final String label, final String value) {
        this.label = label;
        this.value = value;
    }

    /**
     * @param label String
     * @param values String
     */
    public TemplateMetadata(final String label, final List<String> values) {
        for (String value : values) {
            this.label = label;
            this.value = value;
        }
    }

    /**
     * @return String
     */
    @JsonIgnore
    public String getLabel() {
        return this.label;
    }

    /**
     * @return String
     */
    @JsonIgnore
    public String getValue() {
        return this.value;
    }
}
