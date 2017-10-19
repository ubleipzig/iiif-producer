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

package org.ubl.iiifproducer.vocabulary;

import org.apache.commons.rdf.api.IRI;

/**
 * IIIF.
 *
 * @author christopher-johnson
 */
public class IIIF extends BaseVocabulary {

    /* Namespace */
    public static final String URI = "http://iiif.io/api/image/2#";
    public static final String PRESENTATION_CONTEXT =
            "http://iiif.io/api/presentation/2/context.json";
    public static final String IMAGE_CONTEXT = "http://iiif.io/api/image/2/context.json";
    public static final String SERVICE_PROFILE = "http://iiif.io/api/image/2/level1.json";

    /* Classes */
    public static final IRI Feature = createIRI(URI + "Feature");
    public static final IRI Image = createIRI(URI + "Image");
    public static final IRI ImageProfile = createIRI(URI + "ImageProfile");
    public static final IRI Size = createIRI(URI + "Size");
    public static final IRI Tile = createIRI(URI + "Tile");

    /* Object Properties */
    public static final IRI size = createIRI(URI + "hasSize");
    public static final IRI tiles = createIRI(URI + "hasTiles");
    public static final IRI supports = createIRI(URI + "supports");

    /* Datatype Properties */
    public static final IRI format = createIRI(URI + "format");
    public static final IRI quality = createIRI(URI + "quality");
    public static final IRI scaleFactor = createIRI(URI + "scaleFactor");

    /* Named Individuals */
    public static final IRI arbitraryRotationFeature = createIRI(URI + "arbitraryRotationFeature");
    public static final IRI baseUriRedirectFeature = createIRI(URI + "baseUriRedirectFeature");
    public static final IRI canonicalLinkHeaderFeature =
            createIRI(URI + "canonicalLinkHeaderFeature");
    public static final IRI corsFeature = createIRI(URI + "corsFeature");
    public static final IRI jsonLdMediaTypeFeature = createIRI(URI + "jsonLdMediaTypeFeature");
    public static final IRI mirroringFeature = createIRI(URI + "mirroringFeature");
    public static final IRI profileLinkHeaderFeature = createIRI(URI + "profileLinkHeaderFeature");
    public static final IRI regionByPctFeature = createIRI(URI + "regionByPctFeature");
    public static final IRI regionByPxFeature = createIRI(URI + "regionByPxFeature");
    public static final IRI regionSquareFeature = createIRI(URI + "regionSquareFeature");
    public static final IRI rotationBy90sFeature = createIRI(URI + "rotationBy90sFeature");
    public static final IRI sizeAboveFullFeature = createIRI(URI + "sizeAboveFullFeature");
    public static final IRI sizeByHFeature = createIRI(URI + "sizeByHFeature");
    public static final IRI sizeByPctFeature = createIRI(URI + "sizeByPctFeature");
    public static final IRI sizeByWFeature = createIRI(URI + "sizeByWFeature");
    public static final IRI sizeByWHFeature = createIRI(URI + "sizeByWHFeature");
    public static final IRI sizeByWHListedFeature = createIRI(URI + "sizeByWHListedFeature");
    public static final IRI sizeByForcedWHFeature = createIRI(URI + "sizeByForcedWHFeature");

}
