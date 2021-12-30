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

import org.apache.commons.rdf.api.IRI;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * IRIBuilderTest.
 *
 * @author christopher-johnson
 */
public class IRIBuilderTest {
    static String resourceContext;
    static IRIBuilder iriBuilder;

    @BeforeAll
    static void init() {
        resourceContext = "https://iiif.ub.uni-leipzig.de/" + "0000004057";
        iriBuilder = IRIBuilder.builder()
                .annotationContext("/anno")
                .canvasContext("/canvas")
                .imageServiceBaseUrl("https://iiif.ub.uni-leipzig.de/iiif")
                .imageServiceFileExtension(".jpx")
                .imageServiceImageDirPrefix("/j2k/")
                .isUBLImageService(true)
                .resourceContext(resourceContext)
                .build();
    }

    @Test
    void testBuildServiceIRI() {
        final String imageServiceContext = iriBuilder.buildImageServiceContext("0000004057");
        final String resourceIdString = "00000002";
        final IRI serviceIri = iriBuilder.buildServiceIRI(imageServiceContext, resourceIdString);
        assertNotNull(serviceIri);
        assertEquals(
                "https://iiif.ub.uni-leipzig.de/iiif/j2k/0000/0040/0000004057/00000002.jpx", serviceIri.getIRIString());
    }

    @Test
    void testBuildNonDomainServiceIRI() {
        iriBuilder.setUBLImageService(false);
        iriBuilder.setImageServiceBaseUrl("http://localhost:5000/iiif");
        iriBuilder.setImageServiceFileExtension(".tif");

        final String imageServiceContext = iriBuilder.buildImageServiceContext("0000004057");
        final String resourceIdString = "00000002";
        final IRI serviceIri = iriBuilder.buildServiceIRI(imageServiceContext, resourceIdString);
        assertNotNull(serviceIri);
        assertEquals("http://localhost:5000/iiif/0000004057/00000002.tif", serviceIri.getIRIString());
    }

    @Test
    void testBuildCanvasIRI() {
        iriBuilder.setCanvasContext("/canvas");

        final String resourceFileId = "00000001";
        final String resourceIdString = "http://example.org/" + "12345" + "/canvas" + "/" + resourceFileId;
        final IRI canvasIRI = iriBuilder.buildCanvasIRI(resourceIdString);
        assertEquals("http://example.org/12345/canvas/00000001", canvasIRI.getIRIString());
    }

    @Test
    void testBuildImageServiceContext() {
        final String imageServiceContext = iriBuilder.buildImageServiceContext("12345");
        assertEquals("https://iiif.ub.uni-leipzig.de/iiif/j2k/0000/0123/0000012345", imageServiceContext);
    }
}
