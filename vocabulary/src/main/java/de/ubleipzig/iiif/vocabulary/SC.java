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

import static de.ubleipzig.iiif.vocabulary.VocabUtils.createIRI;

import org.apache.commons.rdf.api.IRI;

/**
 * SC.
 *
 * @author christopher-johnson
 */
public final class SC {

    /* Namespace */
    public static final String URI = "http://iiif.io/api/presentation/2#";

    /* Context */
    public static final String CONTEXT = "http://iiif.io/api/presentation/2/context.json";

    /* Classes */
    public static final IRI AnnotationList = createIRI(URI + "AnnotationList");
    public static final IRI Canvas = createIRI(URI + "Canvas");
    public static final IRI Collection = createIRI(URI + "Collection");
    public static final IRI Layer = createIRI(URI + "Layer");
    public static final IRI Manifest = createIRI(URI + "Manifest");
    public static final IRI Range = createIRI(URI + "Range");
    public static final IRI Sequence = createIRI(URI + "Sequence");
    public static final IRI Zone = createIRI(URI + "Zone");
    public static final IRI ViewingDirection = createIRI(URI + "ViewingDirection");
    public static final IRI ViewingHint = createIRI(URI + "ViewingHint");

    /* Properties */
    public static final IRI attributionLabel = createIRI(URI + "attributionLabel");
    public static final IRI hasAnnotations = createIRI(URI + "hasAnnotations");
    public static final IRI hasCanvases = createIRI(URI + "hasCanvases");
    public static final IRI hasCollections = createIRI(URI + "hasCollections");
    public static final IRI hasContentLayer = createIRI(URI + "hasContentLayer");
    public static final IRI hasImageAnnotations = createIRI(URI + "hasImageAnnotations");
    public static final IRI hasLists = createIRI(URI + "hasLists");
    public static final IRI hasManifests = (IRI) createIRI(URI + "hasManifests");
    public static final IRI hasRanges = createIRI(URI + "hasRanges");
    public static final IRI hasSequences = createIRI(URI + "hasSequences");
    public static final IRI hasStartCanvas = createIRI(URI + "hasStartCanvas");
    public static final IRI metadataLabels = createIRI(URI + "metadataLabels");
    public static final IRI viewingDirection = createIRI(URI + "viewingDirection");
    public static final IRI viewingHint = createIRI(URI + "viewingHint");

    /* Named Individuals */
    public static final IRI bottomToTopDirection = createIRI(URI + "bottomToTopDirection");
    public static final IRI continuousHint = createIRI(URI + "continuousHint");
    public static final IRI facingPagesHint = createIRI(URI + "facingPagesHint");
    public static final IRI individualsHint = createIRI(URI + "individualsHint");
    public static final IRI leftToRightDirection = createIRI(URI + "leftToRightDirection");
    public static final IRI multiPartHint = createIRI(URI + "multiPartHint");
    public static final IRI nonPagedHint = createIRI(URI + "nonPagedHint");
    public static final IRI pagedHint = createIRI(URI + "pagedHint");
    public static final IRI painting = createIRI(URI + "painting");
    public static final IRI rightToLeftDirection = createIRI(URI + "rightToLeftDirection");
    public static final IRI topHint = createIRI(URI + "topHint");
    public static final IRI topToBottomDirection = createIRI(URI + "topToBottomDirection");

    private SC() {
    }
}
