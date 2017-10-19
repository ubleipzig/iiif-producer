package org.ubl.iiifproducer.producer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.ubl.iiifproducer.producer.Constants.BASE_URL;
import static org.ubl.iiifproducer.producer.Constants.IIIF_CANVAS;
import static org.ubl.iiifproducer.producer.StaticIRIBuilder.buildCanvasIRI;
import static org.ubl.iiifproducer.producer.StaticIRIBuilder.buildImageServiceContext;
import static org.ubl.iiifproducer.producer.StaticIRIBuilder.buildServiceIRI;

import org.apache.commons.rdf.api.IRI;
import org.junit.jupiter.api.Test;

/**
 * StaticIRIBuilderTest.
 *
 * @author christopher-johnson
 */
public class StaticIRIBuilderTest {


    @Test
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
