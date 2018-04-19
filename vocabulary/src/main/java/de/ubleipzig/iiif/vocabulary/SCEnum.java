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

package de.ubleipzig.iiif.vocabulary;

/**
 * SCEnum.
 *
 * <p>this is to support clients who expect compacted IRIs in JSON-LD documents</p>
 */
public enum SCEnum {

    AnnotationList("sc:AnnotationList"),

    Canvas("sc:Canvas"),

    Collection("sc:Collection"),

    Layer("sc:Layer"),

    Manifest("sc:Manifest"),

    Range("sc:Range"),

    Sequence("sc:Sequence"),

    Zone("sc:Zone"),

    attributionLabel("sc:attributionLabel"),

    hasAnnotations("sc:hasAnnotations"),

    hasCanvases("sc:hasCanvases"),

    hasCollections("sc:hasCollections"),

    hasContentLayer("sc:hasContentLayer"),

    hasImageAnnotations("sc:hasImageAnnotations"),

    hasLists("sc:hasLists"),

    hasManifests("sc:hasManifests"),

    hasRanges("sc:hasRanges"),

    hasSequences("sc:hasSequences"),

    hasStartCanvas("sc:hasStartCanvas"),

    metadataLabels("sc:metadataLabels"),

    ViewingDirection("sc:ViewingDirection"),

    ViewingHint("sc:ViewingHint"),

    bottomToTopDirection("sc:bottomToTopDirection"),

    continuousHint("sc:continuousHint"),

    facingPagesHint("sc:facingPagesHint"),

    individualsHint("sc:individualsHint"),

    leftToRightDirection("sc:leftToRightDirection"),

    multiPartHint("sc:multiPartHint"),

    nonPagedHint("sc:nonPagedHint"),

    painting("sc:painting"),

    rightToLeftDirection("sc:rightToLeftDirection"),

    topHint("sc:topHint"),

    topToBottomDirection("sc:topToBottomDirection");

    private String compactedIRI;

    SCEnum(final String compactedIRI) {
        this.compactedIRI = compactedIRI;
    }

    /**
     * @return String
     */
    public String compactedIRI() {
        return compactedIRI;
    }
}
