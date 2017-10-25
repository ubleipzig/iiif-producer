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

package org.ubl.iiifproducer.producer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.ubl.iiifproducer.producer.Constants.BASE_URL;
import static org.ubl.iiifproducer.producer.Constants.IIIF_CANVAS;
import static org.ubl.iiifproducer.producer.StaticIRIBuilder.buildCanvasIRI;
import static org.ubl.iiifproducer.producer.StaticIRIBuilder.buildImageServiceContext;
import static org.ubl.iiifproducer.producer.StaticIRIBuilder.buildServiceIRI;

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
        String imageServiceContext = buildImageServiceContext("0000004057");
        String resourceIdString = "00000002";
        IRI serviceIri = buildServiceIRI(imageServiceContext, resourceIdString);
        assertTrue(serviceIri != null);
        assertEquals(
                "https://iiif.ub.uni-leipzig.de/fcgi-bin/iipsrv"
                        + ".fcgi?iiif=/j2k/0000/0040/0000004057/00000002.jpx",
                serviceIri.getIRIString());
    }

    @Test
    void testBuildCanvasIRI() {
        String viewId = "12345";
        String resourceFileId = "00000001";
        String resourceIdString = BASE_URL + viewId + IIIF_CANVAS + "/" + resourceFileId;
        IRI canvasIRI = buildCanvasIRI(resourceIdString);
        assertEquals(
                "https://iiif.ub.uni-leipzig.de/12345/canvas/00000001", canvasIRI.getIRIString());
    }
}
