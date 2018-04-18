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

import static de.ubleipzig.iiifproducer.vocabulary.VocabUtils.createIRI;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.rdf.api.IRI;

/**
 * ANNO.
 *
 * @author christopher-johnson
 */
public final class ANNO {

    /* CONTEXT */
    public static final String CONTEXT = "http://www.w3.org/ns/anno.jsonld";

    private static final String AS = "AS";
    private static final String DC = "DC";
    private static final String DCTERMS = "DCTERMS";
    private static final String DCTYPES = "DCTYPES";
    private static final String FOAF = "FOAF";
    private static final String OA = "OA";
    private static final String RDF = "RDF";
    private static final String RDFS = "RDFS";
    private static final String SCHEMA = "SCHEMA";
    /* Classes */
    public static final IRI AnnotationPage = createIRI(namespaces().get(AS) + "OrderedCollectionPage");
    public static final IRI Software = createIRI(namespaces().get(AS) + "Application");
    public static final IRI Dataset = createIRI(namespaces().get(DCTYPES) + "Dataset");
    public static final IRI Image = createIRI(namespaces().get(DCTYPES) + "StillImage");
    public static final IRI Video = createIRI(namespaces().get(DCTYPES) + "MovingImage");
    public static final IRI Audio = createIRI(namespaces().get(DCTYPES) + "Sound");
    public static final IRI Text = createIRI(namespaces().get(DCTYPES) + "Text");
    public static final IRI Person = createIRI(namespaces().get(FOAF) + "Person");
    public static final IRI Organization = createIRI(namespaces().get(FOAF) + "Organization");
    public static final IRI Annotation = createIRI(namespaces().get(OA) + "Annotation");
    public static final IRI Choice = createIRI(namespaces().get(OA) + "Choice");
    public static final IRI CssSelector = createIRI(namespaces().get(OA) + "CssSelector");
    public static final IRI CssStylesheet = createIRI(namespaces().get(OA) + "CssStyle");
    public static final IRI DataPositionSelector = createIRI(namespaces().get(OA) + "DataPositionSelector");
    public static final IRI FragmentSelector = createIRI(namespaces().get(OA) + "FragmentSelector");
    public static final IRI HttpRequestState = createIRI(namespaces().get(OA) + "HttpRequestState");
    public static final IRI Motivation = createIRI(namespaces().get(OA) + "Motivation");
    public static final IRI RangeSelector = createIRI(namespaces().get(OA) + "RangeSelector");
    public static final IRI ResourceSelection = createIRI(namespaces().get(OA) + "ResourceSelection");
    public static final IRI SpecificResource = createIRI(namespaces().get(OA) + "SpecificResource");
    public static final IRI SvgSelector = createIRI(namespaces().get(OA) + "SvgSelector");
    public static final IRI TextualBody = createIRI(namespaces().get(OA) + "TextualBody");
    public static final IRI TextPositionSelector = createIRI(namespaces().get(OA) + "TextPositionSelector");
    public static final IRI TextQuoteSelector = createIRI(namespaces().get(OA) + "TextQuoteSelector");
    public static final IRI TimeState = createIRI(namespaces().get(OA) + "TimeState");
    public static final IRI XPathSelector = createIRI(namespaces().get(OA) + "XPathSelector");
    public static final IRI Audience = createIRI(namespaces().get(SCHEMA) + "Audience");
    /* Relationships */
    public static final IRI first = createIRI(namespaces().get(AS) + "first");
    public static final IRI generator = createIRI(namespaces().get(AS) + "generator");
    public static final IRI items = createIRI(namespaces().get(AS) + "items");
    public static final IRI last = createIRI(namespaces().get(AS) + "last");
    public static final IRI next = createIRI(namespaces().get(AS) + "next");
    public static final IRI partOf = createIRI(namespaces().get(AS) + "partOf");
    public static final IRI prev = createIRI(namespaces().get(AS) + "prev");
    public static final IRI conformsTo = createIRI(namespaces().get(DCTYPES) + "conformsTo");
    public static final IRI creator = createIRI(namespaces().get(DCTYPES) + "creator");
    public static final IRI rights = createIRI(namespaces().get(DCTYPES) + "rights");
    public static final IRI email = createIRI(namespaces().get(FOAF) + "mbox");
    public static final IRI homepage = createIRI(namespaces().get(FOAF) + "homepage");
    public static final IRI body = createIRI(namespaces().get(OA) + "hasBody");
    public static final IRI cached = createIRI(namespaces().get(OA) + "cachedSource");
    public static final IRI canonical = createIRI(namespaces().get(OA) + "canonical");
    public static final IRI endSelector = createIRI(namespaces().get(OA) + "hasEndSelector");
    public static final IRI motivation = createIRI(namespaces().get(OA) + "motivatedBy");
    public static final IRI purpose = createIRI(namespaces().get(OA) + "hasPurpose");
    public static final IRI refinedBy = createIRI(namespaces().get(OA) + "refinedBy");
    public static final IRI renderedVia = createIRI(namespaces().get(OA) + "renderedVia");
    public static final IRI scope = createIRI(namespaces().get(OA) + "hasScope");
    public static final IRI selector = createIRI(namespaces().get(OA) + "hasSelector");
    public static final IRI startSelector = createIRI(namespaces().get(OA) + "hasStartSelector");
    public static final IRI source = createIRI(namespaces().get(OA) + "hasSource");
    public static final IRI state = createIRI(namespaces().get(OA) + "hasState");
    public static final IRI stylesheet = createIRI(namespaces().get(OA) + "styledBy");
    public static final IRI target = createIRI(namespaces().get(OA) + "hasTarget");
    public static final IRI textDirection = createIRI(namespaces().get(OA) + "textDirection");
    public static final IRI via = createIRI(namespaces().get(OA) + "via");
    public static final IRI audience = createIRI(namespaces().get(SCHEMA) + "audience");
    /* Properties */
    public static final IRI startIndex = createIRI(namespaces().get(AS) + "startIndex");
    public static final IRI total = createIRI(namespaces().get(AS) + "total");
    public static final IRI format = createIRI(namespaces().get(DC) + "format");
    public static final IRI language = createIRI(namespaces().get(DC) + "language");
    public static final IRI created = createIRI(namespaces().get(DCTERMS) + "created");
    public static final IRI generated = createIRI(namespaces().get(DCTERMS) + "generated");
    public static final IRI modified = createIRI(namespaces().get(DCTERMS) + "modified");
    public static final IRI name = createIRI(namespaces().get(FOAF) + "name");
    public static final IRI nickname = createIRI(namespaces().get(FOAF) + "nick");
    public static final IRI bodyValue = createIRI(namespaces().get(OA) + "bodyValue");
    public static final IRI end = createIRI(namespaces().get(OA) + "end");
    public static final IRI exact = createIRI(namespaces().get(OA) + "exact");
    public static final IRI prefix = createIRI(namespaces().get(OA) + "prefix");
    public static final IRI processingLanguage = createIRI(namespaces().get(OA) + "processingLanguage");
    public static final IRI suffix = createIRI(namespaces().get(OA) + "suffix");
    public static final IRI styleClass = createIRI(namespaces().get(OA) + "styleClass");
    public static final IRI sourceDate = createIRI(namespaces().get(OA) + "sourceDate");
    public static final IRI sourceDateEnd = createIRI(namespaces().get(OA) + "sourceDateEnd");
    public static final IRI sourceDateStart = createIRI(namespaces().get(OA) + "sourceDateStart");
    public static final IRI start = createIRI(namespaces().get(OA) + "start");
    public static final IRI label = createIRI(namespaces().get(RDFS) + "label");
    public static final IRI value = createIRI(namespaces().get(RDF) + "value");
    public static final IRI accessibility = createIRI(namespaces().get(SCHEMA) + "accessibilityFeature");
    /* Named Individuals */
    public static final IRI bookmarking = createIRI(namespaces().get(OA) + "bookmarking");
    public static final IRI classifying = createIRI(namespaces().get(OA) + "classifying");
    public static final IRI commenting = createIRI(namespaces().get(OA) + "commenting");
    public static final IRI describing = createIRI(namespaces().get(OA) + "describing");
    public static final IRI editing = createIRI(namespaces().get(OA) + "editing");
    public static final IRI highlighting = createIRI(namespaces().get(OA) + "highlighting");
    public static final IRI identifying = createIRI(namespaces().get(OA) + "identifying");
    public static final IRI linking = createIRI(namespaces().get(OA) + "linking");
    public static final IRI moderating = createIRI(namespaces().get(OA) + "moderating");
    public static final IRI questioning = createIRI(namespaces().get(OA) + "questioning");
    public static final IRI replying = createIRI(namespaces().get(OA) + "replying");
    public static final IRI reviewing = createIRI(namespaces().get(OA) + "reviewing");
    public static final IRI tagging = createIRI(namespaces().get(OA) + "tagging");
    public static final IRI auto = createIRI(namespaces().get(OA) + "autoDirection");
    public static final IRI ltr = createIRI(namespaces().get(OA) + "ltrDirection");
    public static final IRI rtl = createIRI(namespaces().get(OA) + "rtlDirection");

    private ANNO() {
    }

    /**
     * @return Map
     */
    public static Map<String, String> namespaces() {
        final Map<String, String> namespaces = new HashMap<>();
        namespaces.put(AS, "http://www.w3.org/ns/activitystreams#");
        namespaces.put(DC, "http://purl.org/dc/elements/1.1/");
        namespaces.put(DCTERMS, "http://purl.org/dc/terms/");
        namespaces.put(DCTYPES, "http://purl.org/dc/dcmitype/");
        namespaces.put(FOAF, "http://xmlns.com/foaf/0.1/");
        namespaces.put(OA, "http://www.w3.org/ns/oa#");
        namespaces.put(RDF, "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
        namespaces.put(RDFS, "http://www.w3.org/2000/01/rdf-schema#");
        namespaces.put(SCHEMA, "http://schema.org/");
        return namespaces;
    }
}
