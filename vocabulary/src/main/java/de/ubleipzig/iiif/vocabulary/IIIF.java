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

import org.apache.commons.rdf.api.IRI;

/**
 * IIIF.
 *
 * @author christopher-johnson
 */
public final class IIIF {

    /* Namespace */
    public static final String URI = "http://iiif.io/api/image/2#";

    /* Classes */
    public static final IRI Feature = VocabUtils.createIRI(URI + "Feature");
    public static final IRI Image = VocabUtils.createIRI(URI + "Image");
    public static final IRI ImageProfile = VocabUtils.createIRI(URI + "ImageProfile");
    public static final IRI Size = VocabUtils.createIRI(URI + "Size");
    public static final IRI Tile = VocabUtils.createIRI(URI + "Tile");

    /* Object Properties */
    public static final IRI size = VocabUtils.createIRI(URI + "hasSize");
    public static final IRI tile = VocabUtils.createIRI(URI + "hasTiles");
    public static final IRI supports = VocabUtils.createIRI(URI + "supports");

    /* Datatype Properties */
    public static final IRI format = VocabUtils.createIRI(URI + "format");
    public static final IRI quality = VocabUtils.createIRI(URI + "quality");
    public static final IRI scaleFactor = VocabUtils.createIRI(URI + "scaleFactor");

    /* Named Individuals */
    public static final IRI arbitraryRotationFeature = VocabUtils.createIRI(URI + "arbitraryRotationFeature");
    public static final IRI baseUriRedirectFeature = VocabUtils.createIRI(URI + "baseUriRedirectFeature");
    public static final IRI canonicalLinkHeaderFeature = VocabUtils.createIRI(URI + "canonicalLinkHeaderFeature");
    public static final IRI corsFeature = VocabUtils.createIRI(URI + "corsFeature");
    public static final IRI jsonLdMediaTypeFeature = VocabUtils.createIRI(URI + "jsonLdMediaTypeFeature");
    public static final IRI mirroringFeature = VocabUtils.createIRI(URI + "mirroringFeature");
    public static final IRI profileLinkHeaderFeature = VocabUtils.createIRI(URI + "profileLinkHeaderFeature");
    public static final IRI regionByPctFeature = VocabUtils.createIRI(URI + "regionByPctFeature");
    public static final IRI regionByPxFeature = VocabUtils.createIRI(URI + "regionByPxFeature");
    public static final IRI regionSquareFeature = VocabUtils.createIRI(URI + "regionSquareFeature");
    public static final IRI rotationBy90sFeature = VocabUtils.createIRI(URI + "rotationBy90sFeature");
    public static final IRI sizeAboveFullFeature = VocabUtils.createIRI(URI + "sizeAboveFullFeature");
    public static final IRI sizeByHFeature = VocabUtils.createIRI(URI + "sizeByHFeature");
    public static final IRI sizeByPctFeature = VocabUtils.createIRI(URI + "sizeByPctFeature");
    public static final IRI sizeByWFeature = VocabUtils.createIRI(URI + "sizeByWFeature");
    public static final IRI sizeByWHFeature = VocabUtils.createIRI(URI + "sizeByWHFeature");
    public static final IRI sizeByWHListedFeature = VocabUtils.createIRI(URI + "sizeByWHListedFeature");
    public static final IRI sizeByForcedWHFeature = VocabUtils.createIRI(URI + "sizeByForcedWHFeature");

    private IIIF() {
    }
}
