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

package de.ubleipzig.iiifproducer.producer;

import de.ubleipzig.iiif.vocabulary.SC;
import de.ubleipzig.iiifproducer.model.v2.Manifest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * ProducerTest.
 *
 * @author christopher-johnson
 */
class ProducerTest {

    @Test
    void testApp() {
        final String xmlFile = Objects.requireNonNull(
                ProducerTest.class.getResource("/BlhDie_004285964.xml")).getPath();

        final MetsAccessor mets = MetsImpl.builder()
                .xmlFile(xmlFile)
                .mets()
                .xlinkmap()
                .build();
        final IIIFProducer producer = IIIFProducer.builder()
                .mets(mets)
                .outputFile("/tmp/test.json")
                .viewId("004285964")
                .build();
    }

    private IIIFProducer getProducer(String metsFile, String viewId) {
        final String xmlFile = Objects.requireNonNull(
                ProducerTest.class.getResource(metsFile)).getPath();

        final MetsAccessor mets = MetsImpl.builder()
                .xmlFile(xmlFile)
                .mets()
                .xlinkmap()
                .build();
        final IIIFProducer producer = IIIFProducer.builder()
                .mets(mets)
                .outputFile("/tmp/test.json")
                .viewId(viewId)
                .build();
        return producer;
    }

    @Test
    void testRelated() {
        IIIFProducer producer = getProducer("/JohaSpha_1499_470272783.xml", "00123456");
        final Manifest manifest = Manifest.builder()
                .context(SC.CONTEXT)
                .id("http://example.com/00123456")
                .build();
        producer.setRelated(manifest, "urn:1234", "00123456", false);
        List<String> related = (List)manifest.getRelated();
        assertEquals(4, related.size());
        assertTrue(related.contains("https://katalog.ub.uni-leipzig.de/urn/urn:nbn:de:bsz:15-0013-221052"));
        assertTrue(related.contains("https://digital.ub.uni-leipzig.de/object/viewid/00123456"));
        assertTrue(related.contains("https://iiif.ub.uni-leipzig.de/00123456/manifest.json"));
        assertTrue(related.contains("https://iiif.ub.uni-leipzig.de/00123456/presentation.xml"));
    }

    @Test
    void testRelatedWithoutCatalogUri() {
        IIIFProducer producer = getProducer("/MS_85.xml", "00123456");
        final Manifest manifest = Manifest.builder()
                .context(SC.CONTEXT)
                .id("http://example.com/00123456")
                .build();
        producer.setRelated(manifest, "urn:1234", "00123456", false);
        List<String> related = (List)manifest.getRelated();
        assertEquals(3, related.size());
        assertTrue(related.contains("https://digital.ub.uni-leipzig.de/object/viewid/00123456"));
        assertTrue(related.contains("https://iiif.ub.uni-leipzig.de/00123456/manifest.json"));
        assertTrue(related.contains("https://iiif.ub.uni-leipzig.de/00123456/presentation.xml"));
    }

    @Test
    void testRelatedForHspCatalogs() {
        IIIFProducer producer = getProducer("/DieHadeT_1525437259.xml", "00123456");
        final Manifest manifest = Manifest.builder()
                .context(SC.CONTEXT)
                .id("http://example.com/00123456")
                .build();
        producer.setRelated(manifest, "urn:1234", "00123456", true);
        List<String> related = (List)manifest.getRelated();
        assertEquals(1, related.size());
        assertTrue(related.contains("https://iiif.ub.uni-leipzig.de/00123456/manifest.json"));
    }

    void testAttributionWithCopyrightHolder() {
        IIIFProducer producer = getProducer("/DieHadeT_1525437259.xml", "00123456");
        final Manifest manifest = Manifest.builder()
                .context(SC.CONTEXT)
                .id("http://example.com/00123456")
                .build();
        String attribution = manifest.getAttribution();
        assertTrue(attribution.contains("Otto Harrassowitz GmbH & Co. KG"));
    }
}

