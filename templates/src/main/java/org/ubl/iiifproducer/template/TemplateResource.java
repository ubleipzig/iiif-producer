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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * TemplateResource.
 *
 * @author christopher-johnson
 */
@JsonPropertyOrder({"@id", "@type", "label", "format", "height", "width", "service"})
public class TemplateResource {

    @JsonProperty("@id")
    private String id = "";

    @JsonProperty("@type")
    private String type = "dctypes:Image";

    @JsonProperty
    private String label = "unnamed resource";

    @JsonProperty
    private String format = "image/jpeg";

    @JsonProperty
    private Integer height;

    @JsonProperty
    private Integer width;

    @JsonProperty
    private TemplateService service;

    public TemplateResource() {
    }

    public void setResourceId(String id) {
        this.id = id;
    }

    public void setResourceLabel(String label) {
        this.label = label;
    }

    public void setResourceFormat(String format) {
        this.format = format;
    }

    public void setService(TemplateService service) {
        this.service = service;
    }

    public void setResourceHeight(Integer height) {
        this.height = height;
    }

    public void setResourceWidth(Integer width) {
        this.width = width;
    }
}
