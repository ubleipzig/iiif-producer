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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * IRIBuilderTest.
 *
 * @author christopher-johnson
 */
public class IRIBuilderTest {
    Properties config;
    String resourceContext;

    @BeforeEach
    void init() {
        config = new Properties();
        config.setProperty("annotationContext","/anno");
        config.setProperty("baseUrl", "https://iiif.ub.uni-leipzig.de/");
        config.setProperty("canvasContext","/canvas");
        config.setProperty("imageServiceBaseUrl", "https://iiif.ub.uni-leipzig.de/iiif");
        config.setProperty("imageServiceFileExtension", ".jpx");
        config.setProperty("imageServiceImageDirPrefix", "/j2k/");
        config.setProperty("isUBLImageService", "true");
        config.setProperty("viewId", "0000004057");
        config.setProperty("imageServiceBaseUrl", "https://iiif.ub.uni-leipzig.de/iiif");
        config.setProperty("imageServiceImageDirPrefix","/j2k/");
        resourceContext = config.getProperty("baseUrl") + config.getProperty("viewId");
    }

    @Test
    void testBuildServiceIRI() {
        final IRIBuilder iriBuilder = IRIBuilder.builder()
                .config(config)
                .resourceContext(resourceContext)
                .build();
        final String imageServiceContext = iriBuilder.buildImageServiceContext("0000004057");
        final String resourceIdString = "00000002";
        final IRI serviceIri = iriBuilder.buildServiceIRI(imageServiceContext, resourceIdString);
        assertNotNull(serviceIri);
        assertEquals(
                "https://iiif.ub.uni-leipzig.de/iiif/j2k/0000/0040/0000004057/00000002.jpx", serviceIri.getIRIString());
    }

    @Test
    void testBuildNonDomainServiceIRI() {
        config.setProperty("isUBLImageService", "false");
        config.setProperty("imageServiceBaseUrl", "http://localhost:5000/iiif/");
        config.setProperty("imageServiceFileExtension", ".tif");
        final IRIBuilder iriBuilder = IRIBuilder.builder()
                .config(config)
                .resourceContext(resourceContext)
                .build();
        final String imageServiceContext = iriBuilder.buildImageServiceContext("0000004057");
        final String resourceIdString = "00000002";
        final IRI serviceIri = iriBuilder.buildServiceIRI(imageServiceContext, resourceIdString);
        assertNotNull(serviceIri);
        assertEquals("http://localhost:5000/iiif/0000004057/00000002.tif", serviceIri.getIRIString());
    }

    @Test
    void testBuildCanvasIRI() {
        config.setProperty("baseUrl","http://example.org/");
        config.setProperty("viewId", "12345");
        config.setProperty("canvasContext", "/canvas");
        final IRIBuilder iriBuilder = IRIBuilder.builder()
                .config(config)
                .resourceContext(resourceContext)
                .build();
        final String resourceFileId = "00000001";
        final String resourceIdString = resourceContext + config.getProperty("canvasContext") + "/" + resourceFileId;
        final IRI canvasIRI = iriBuilder.buildCanvasIRI(resourceIdString);
        assertEquals("http://example.org/12345/canvas/00000001", canvasIRI.getIRIString());
    }

    @Test
    void testBuildImageServiceContext() {
        config.setProperty("viewId", "12345");
        final IRIBuilder iriBuilder = IRIBuilder.builder()
                .config(config)
                .resourceContext(resourceContext)
                .build();
        final String imageServiceContext = iriBuilder.buildImageServiceContext("12345");
        assertEquals("https://iiif.ub.uni-leipzig.de/iiif/j2k/0000/0123/0000012345", imageServiceContext);
    }
}
