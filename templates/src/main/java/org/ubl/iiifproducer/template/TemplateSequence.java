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

import static org.ubl.iiifproducer.vocabulary.SC._Sequence;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;

/**
 * TemplateSequence.
 *
 * @author christopher-johnson
 */
@JsonPropertyOrder({"@id", "@type", "canvases"})
public class TemplateSequence {

    @JsonProperty("@id")
    private String id;

    @JsonProperty("@type")
    private String type = _Sequence;

    @JsonProperty("canvases")
    private List<TemplateCanvas> canvases;

    public TemplateSequence(String id, List<TemplateCanvas> canvases) {
        this.id = id;
        this.canvases = canvases;
    }
}
