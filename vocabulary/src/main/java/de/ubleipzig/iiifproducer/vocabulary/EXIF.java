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

package de.ubleipzig.iiifproducer.vocabulary;

import org.apache.commons.rdf.api.IRI;

/**
 * EXIF.
 *
 * @author christopher-johnson
 */
public class EXIF extends BaseVocabulary {

    /* Namespace */
    public static final String URI = "http://www.w3.org/2003/12/exif/ns#";

    public static final IRI base = createIRI(URI);

    /* Classes */
    public static final IRI width = createIRI(URI + "width");
    public static final IRI height = createIRI(URI + "height");
}
