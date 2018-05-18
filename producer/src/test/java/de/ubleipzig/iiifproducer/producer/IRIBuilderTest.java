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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.commons.rdf.api.IRI;
import org.junit.jupiter.api.Test;

/**
 * IRIBuilderTest.
 *
 * @author christopher-johnson
 */
public class IRIBuilderTest {


    @Test
    void testBuildServiceIRI() {
        final Config config = new Config();
        config.setViewId("0000004057");
        config.setImageServiceBaseUrl("https://iiif.ub.uni-leipzig.de/fcgi-bin/iipsrv.fcgi?iiif=");
        config.setImageServiceImageDirPrefix("/j2k/0000/0000/");
        config.setImageServiceFileExtension(".jpx");
        final IRIBuilder iriBuilder = new IRIBuilder(config);
        final String imageServiceContext = iriBuilder.buildImageServiceContext();
        final String resourceIdString = "00000002";
        final IRI serviceIri = iriBuilder.buildServiceIRI(imageServiceContext, resourceIdString);
        assertNotNull(serviceIri);
        assertEquals("https://iiif.ub.uni-leipzig.de/fcgi-bin/iipsrv" + "" + ""
                + ".fcgi?iiif=/j2k/0000/0040/0000004057/00000002.jpx", serviceIri.getIRIString());
    }

    @Test
    void testBuildCanvasIRI() {
        final Config config = new Config();
        config.setBaseUrl("http://example.org/");
        config.setViewId("12345");
        config.setImageServiceBaseUrl("https://iiif.ub.uni-leipzig.de/fcgi-bin/iipsrv.fcgi?iiif=");
        config.setImageServiceImageDirPrefix("/j2k/0000/0000/");
        config.setCanvasContext("/canvas");
        final IRIBuilder iriBuilder = new IRIBuilder(config);
        final String viewId = "12345";
        final String resourceFileId = "00000001";
        final String resourceIdString = config.getBaseUrl() + viewId + config.getCanvasContext() + "/" + resourceFileId;
        final IRI canvasIRI = iriBuilder.buildCanvasIRI(resourceIdString);
        assertEquals("http://example.org/12345/canvas/00000001", canvasIRI.getIRIString());
    }
}
