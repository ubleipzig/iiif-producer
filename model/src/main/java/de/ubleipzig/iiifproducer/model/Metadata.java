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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import de.ubleipzig.iiifproducer.model.v2.LabelObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Metadata.
 *
 * @author christopher-johnson
 */
@Builder
@Setter
@Getter
@AllArgsConstructor
@JsonPropertyOrder({"label", "value"})
public class Metadata {

    @JsonIgnore
    private Integer displayOrder;
    @JsonProperty("label")
    private Object label;
    @JsonProperty("value")
    @JsonFormat(with = JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)
    private Object value;

    @JsonIgnore
    public String getFirstAvailableLabel() {
        if (label instanceof String) {
            return (String) label;
        }
        if (label instanceof LabelObject[]) {
            return ((LabelObject[]) label)[0].getValue();
        }
        return null;
    }
}
