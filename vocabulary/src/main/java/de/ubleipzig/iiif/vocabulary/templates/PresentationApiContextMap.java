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

package de.ubleipzig.iiif.vocabulary.templates;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PresentationApiContextMap {

    @JsonProperty
    String sc;

    @JsonProperty
    String iiif;

    @JsonProperty
    String exif;

    @JsonProperty
    String oa;

    @JsonProperty
    String cnt;

    @JsonProperty
    String dc;

    @JsonProperty
    String dcterms;

    @JsonProperty
    String dctypes;

    @JsonProperty
    String doap;

    @JsonProperty
    String foaf;

    @JsonProperty
    String rdf;

    @JsonProperty
    String rdfs;

    @JsonProperty
    String xsd;

    @JsonProperty
    String svcs;

    @JsonProperty
    String as;

    @JsonProperty("left-to-right")
    String leftToRight;

    @JsonProperty("right-to-left")
    String rightToleft;

    @JsonProperty("top-to-bottom")
    String topToBottom;

    @JsonProperty("bottom-to-top")
    String bottomToTop;

    @JsonProperty
    String paged;

    @JsonProperty("non-paged")
    String nonPaged;

    @JsonProperty
    String continuous;

    @JsonProperty
    String individuals;

    @JsonProperty
    String top;

    @JsonProperty("multi-part")
    String multiPart;

    @JsonProperty("facing-pages")
    String facingPages;

    @JsonProperty
    String prefix;

    @JsonProperty
    String suffix;

    @JsonProperty
    String exact;

    @JsonProperty
    Object license;

    @JsonProperty
    Object service;

    @JsonProperty
    Object seeAlso;

    @JsonProperty
    Object within;

    @JsonProperty
    Object profile;

    @JsonProperty
    Object related;

    @JsonProperty
    Object logo;

    @JsonProperty
    Object thumbnail;

    @JsonProperty
    Object startCanvas;

    @JsonProperty
    Object contentLayer;

    @JsonProperty
    Object members;

    @JsonProperty
    Object collections;

    @JsonProperty
    Object manifests;

    @JsonProperty
    Object sequences;

    @JsonProperty
    Object canvases;

    @JsonProperty
    Object resources;

    @JsonProperty
    Object otherContent;

    @JsonProperty
    Object images;

    @JsonProperty
    Object structures;

    @JsonProperty
    Object ranges;

    @JsonProperty
    Object metadata;

    @JsonProperty
    Object description;

    @JsonProperty
    Object navDate;

    @JsonProperty
    Object rendering;

    @JsonProperty
    Object height;

    @JsonProperty
    Object width;

    @JsonProperty
    Object attribution;

    @JsonProperty
    Object viewingDirection;

    @JsonProperty
    Object viewingHint;

    @JsonProperty
    Object motivation;

    @JsonProperty
    Object resource;

    @JsonProperty
    Object on;

    @JsonProperty
    Object full;

    @JsonProperty
    Object selector;

    @JsonProperty
    Object stylesheet;

    @JsonProperty
    Object style;

    @JsonProperty("default")
    Object standard;

    @JsonProperty
    Object item;

    @JsonProperty
    Object chars;

    @JsonProperty
    Object encoding;

    @JsonProperty
    Object bytes;

    @JsonProperty
    Object format;

    @JsonProperty
    Object language;

    @JsonProperty
    Object value;

    @JsonProperty
    Object label;

    @JsonProperty
    Object first;

    @JsonProperty
    Object last;

    @JsonProperty
    Object next;

    @JsonProperty
    Object prev;

    @JsonProperty
    Object total;

    @JsonProperty
    Object startIndex;

    /**
     * @return String
     */
    public String getSc() {
        return this.sc;
    }

    /**
     * @return String
     */
    public String getIIIF() {
        return this.iiif;
    }

    /**
     * @return String
     */
    public String getEXIF() {
        return this.exif;
    }

    /**
     * @return String
     */
    public String getDC() {
        return this.dc;
    }

    /**
     * @return String
     */
    public String getDCTerms() {
        return this.dcterms;
    }

    /**
     * @return String
     */
    public String getDOAP() {
        return this.doap;
    }

    /**
     * @return String
     */
    public String getFOAF() {
        return this.foaf;
    }

    /**
     * @return String
     */
    public String getRDFS() {
        return this.rdfs;
    }

    /**
     * @return String
     */
    public String getXSD() {
        return this.xsd;
    }

    /**
     * @return String
     */
    public String getSVCS() {
        return this.svcs;
    }

    /**
     * @return String
     */
    public String getAS() {
        return this.as;
    }
}
