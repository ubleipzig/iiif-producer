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

import static de.ubleipzig.iiifproducer.producer.Constants.BASE_URL;
import static de.ubleipzig.iiifproducer.producer.Constants.IIIF_CANVAS;
import static de.ubleipzig.iiifproducer.producer.StaticIRIBuilder.buildCanvasIRI;
import static de.ubleipzig.iiifproducer.producer.StaticIRIBuilder.buildImageServiceContext;
import static de.ubleipzig.iiifproducer.producer.StaticIRIBuilder.buildServiceIRI;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.rdf.api.IRI;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * StaticIRIBuilderTest.
 *
 * @author christopher-johnson
 */
public class StaticIRIBuilderTest {


    @Test
    @Disabled
    void testBuildServiceIRI() {
        final String imageServiceContext = buildImageServiceContext("0000004057");
        final String resourceIdString = "00000002";
        final IRI serviceIri = buildServiceIRI(imageServiceContext, resourceIdString);
        assertTrue(serviceIri != null);
        assertEquals("https://iiif.ub.uni-leipzig.de/fcgi-bin/iipsrv" + ""
                + ".fcgi?iiif=/j2k/0000/0040/0000004057/00000002.jpx", serviceIri.getIRIString());
    }

    @Test
    void testBuildCanvasIRI() {
        final String viewId = "12345";
        final String resourceFileId = "00000001";
        final String resourceIdString = BASE_URL + viewId + IIIF_CANVAS + "/" + resourceFileId;
        final IRI canvasIRI = buildCanvasIRI(resourceIdString);
        assertEquals("https://iiif.ub.uni-leipzig.de/12345/canvas/00000001", canvasIRI.getIRIString());
    }
}
