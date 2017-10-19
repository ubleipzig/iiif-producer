package org.ubl.iiifproducer.producer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.ubl.iiifproducer.producer.StaticIRIBuilder.buildImageServiceContext;

import org.junit.jupiter.api.Test;

/**
 * ImageServiceContextTest.
 *
 * @author christopher-johnson
 */
public class ImageServiceContextTest {

    @Test
    void testBuildImageServiceContext() {
        String imageServiceContext = buildImageServiceContext("12345");
        assertEquals(
                "https://iiif.ub.uni-leipzig.de/fcgi-bin/iipsrv.fcgi?iiif=/j2k/0000/0123/12345",
                imageServiceContext);
    }
}
