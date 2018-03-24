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

package org.ubl.iiifproducer.vocabulary;

import org.apache.commons.rdf.api.IRI;

/**
 * ANNO.
 *
 * @author christopher-johnson
 */
public final class ANNO extends BaseVocabulary {

    /* CONTEXT */
    public static final String CONTEXT = "http://www.w3.org/ns/anno.jsonld";

    /* NAMESPACES */

    public static final String AS = "http://www.w3.org/ns/activitystreams#";
    public static final String DC = "http://purl.org/dc/elements/1.1/";
    public static final String DCTERMS = "http://purl.org/dc/terms/";
    public static final String DCTYPES = "http://purl.org/dc/dcmitype/";
    public static final String FOAF = "http://xmlns.com/foaf/0.1/";
    public static final String OA = "http://www.w3.org/ns/oa#";
    public static final String RDF = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    public static final String RDFS = "http://www.w3.org/2000/01/rdf-schema#";
    public static final String SCHEMA = "http://schema.org/";


    /* Classes */

    public static final IRI AnnotationPage = createIRI(AS + "OrderedCollectionPage");
    public static final IRI Software = createIRI(AS + "Application");

    public static final IRI Dataset = createIRI(DCTYPES + "Dataset");
    public static final IRI Image = createIRI(DCTYPES + "StillImage");
    public static final IRI Video = createIRI(DCTYPES + "MovingImage");
    public static final IRI Audio = createIRI(DCTYPES + "Sound");
    public static final IRI Text = createIRI(DCTYPES + "Text");

    public static final IRI Person = createIRI(FOAF + "Person");
    public static final IRI Organization = createIRI(FOAF + "Organization");

    public static final IRI Annotation = createIRI(OA + "Annotation");
    public static final IRI Choice = createIRI(OA + "Choice");
    public static final IRI CssSelector = createIRI(OA + "CssSelector");
    public static final IRI CssStylesheet = createIRI(OA + "CssStyle");
    public static final IRI DataPositionSelector = createIRI(OA + "DataPositionSelector");
    public static final IRI FragmentSelector = createIRI(OA + "FragmentSelector");
    public static final IRI HttpRequestState = createIRI(OA + "HttpRequestState");
    public static final IRI Motivation = createIRI(OA + "Motivation");
    public static final IRI RangeSelector = createIRI(OA + "RangeSelector");
    public static final IRI ResourceSelection = createIRI(OA + "ResourceSelection");
    public static final IRI SpecificResource = createIRI(OA + "SpecificResource");
    public static final IRI SvgSelector = createIRI(OA + "SvgSelector");
    public static final IRI TextualBody = createIRI(OA + "TextualBody");
    public static final IRI TextPositionSelector = createIRI(OA + "TextPositionSelector");
    public static final IRI TextQuoteSelector = createIRI(OA + "TextQuoteSelector");
    public static final IRI TimeState = createIRI(OA + "TimeState");
    public static final IRI XPathSelector = createIRI(OA + "XPathSelector");

    public static final IRI Audience = createIRI(SCHEMA + "Audience");


    /* Relationships */

    public static final IRI first = createIRI(AS + "first");
    public static final IRI generator = createIRI(AS + "generator");
    public static final IRI items = createIRI(AS + "items");
    public static final IRI last = createIRI(AS + "last");
    public static final IRI next = createIRI(AS + "next");
    public static final IRI partOf = createIRI(AS + "partOf");
    public static final IRI prev = createIRI(AS + "prev");

    public static final IRI conformsTo = createIRI(DCTERMS + "conformsTo");
    public static final IRI creator = createIRI(DCTERMS + "creator");
    public static final IRI rights = createIRI(DCTERMS + "rights");

    public static final IRI email = createIRI(FOAF + "mbox");
    public static final IRI homepage = createIRI(FOAF + "homepage");

    public static final IRI body = createIRI(OA + "hasBody");
    public static final IRI cached = createIRI(OA + "cachedSource");
    public static final IRI canonical = createIRI(OA + "canonical");
    public static final IRI endSelector = createIRI(OA + "hasEndSelector");
    public static final IRI motivation = createIRI(OA + "motivatedBy");
    public static final IRI purpose = createIRI(OA + "hasPurpose");
    public static final IRI refinedBy = createIRI(OA + "refinedBy");
    public static final IRI renderedVia = createIRI(OA + "renderedVia");
    public static final IRI scope = createIRI(OA + "hasScope");
    public static final IRI selector = createIRI(OA + "hasSelector");
    public static final IRI startSelector = createIRI(OA + "hasStartSelector");
    public static final IRI source = createIRI(OA + "hasSource");
    public static final IRI state = createIRI(OA + "hasState");
    public static final IRI stylesheet = createIRI(OA + "styledBy");
    public static final IRI target = createIRI(OA + "hasTarget");
    public static final IRI textDirection = createIRI(OA + "textDirection");
    public static final IRI via = createIRI(OA + "via");

    public static final IRI audience = createIRI(SCHEMA + "audience");

    /* Properties */
    public static final IRI startIndex = createIRI(AS + "startIndex");
    public static final IRI total = createIRI(AS + "total");

    public static final IRI format = createIRI(DC + "format");
    public static final IRI language = createIRI(DC + "language");

    public static final IRI created = createIRI(DCTERMS + "created");
    public static final IRI generated = createIRI(DCTERMS + "generated");
    public static final IRI modified = createIRI(DCTERMS + "modified");

    public static final IRI email_sha1 = createIRI(FOAF + "mbox_sha1sum");
    public static final IRI name = createIRI(FOAF + "name");
    public static final IRI nickname = createIRI(FOAF + "nick");

    public static final IRI bodyValue = createIRI(OA + "bodyValue");
    public static final IRI end = createIRI(OA + "end");
    public static final IRI exact = createIRI(OA + "exact");
    public static final IRI prefix = createIRI(OA + "prefix");
    public static final IRI processingLanguage = createIRI(OA + "processingLanguage");
    public static final IRI suffix = createIRI(OA + "suffix");
    public static final IRI styleClass = createIRI(OA + "styleClass");
    public static final IRI sourceDate = createIRI(OA + "sourceDate");
    public static final IRI sourceDateEnd = createIRI(OA + "sourceDateEnd");
    public static final IRI sourceDateStart = createIRI(OA + "sourceDateStart");
    public static final IRI start = createIRI(OA + "start");

    public static final IRI label = createIRI(RDFS + "label");
    public static final IRI value = createIRI(RDF + "value");

    public static final IRI accessibility = createIRI(SCHEMA + "accessibilityFeature");


    /* Named Individuals */

    public static final IRI bookmarking = createIRI(OA + "bookmarking");
    public static final IRI classifying = createIRI(OA + "classifying");
    public static final IRI commenting = createIRI(OA + "commenting");
    public static final IRI describing = createIRI(OA + "describing");
    public static final IRI editing = createIRI(OA + "editing");
    public static final IRI highlighting = createIRI(OA + "highlighting");
    public static final IRI identifying = createIRI(OA + "identifying");
    public static final IRI linking = createIRI(OA + "linking");
    public static final IRI moderating = createIRI(OA + "moderating");
    public static final IRI questioning = createIRI(OA + "questioning");
    public static final IRI replying = createIRI(OA + "replying");
    public static final IRI reviewing = createIRI(OA + "reviewing");
    public static final IRI tagging = createIRI(OA + "tagging");
    public static final IRI auto = createIRI(OA + "autoDirection");
    public static final IRI ltr = createIRI(OA + "ltrDirection");
    public static final IRI rtl = createIRI(OA + "rtlDirection");

    private ANNO() {
        super();
    }
}
