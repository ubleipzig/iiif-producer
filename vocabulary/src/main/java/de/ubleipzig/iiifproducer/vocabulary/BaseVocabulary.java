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

import static java.util.ServiceLoader.load;

import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;

/**
 * BaseVocabulary.
 *
 * @author acoburn
 */
class BaseVocabulary {

    private static RDF rdf = load(RDF.class).iterator().next();

    protected BaseVocabulary() {
        // prevent direct instantiation
    }

    protected static IRI createIRI(final String uri) {

        return rdf.createIRI(uri);
    }
}
