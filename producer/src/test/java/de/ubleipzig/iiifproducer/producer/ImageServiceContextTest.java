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

import org.junit.jupiter.api.Test;

/**
 * ImageServiceContextTest.
 *
 * @author christopher-johnson
 */
public class ImageServiceContextTest {

    @Test
        //@Disabled
    void testBuildImageServiceContext() {
        final Config config = new Config();
        config.setViewId("12345");
        config.setImageServiceBaseUrl("https://iiif.ub.uni-leipzig.de/fcgi-bin/iipsrv.fcgi?iiif=");
        config.setImageServiceImageDirPrefix("/j2k/0000/0000/");
        final IRIBuilder iriBuilder = new IRIBuilder(config);
        final String imageServiceContext = iriBuilder.buildImageServiceContext();
        assertEquals("https://iiif.ub.uni-leipzig.de/fcgi-bin/iipsrv.fcgi?iiif=/j2k/0000/0123/12345",
                imageServiceContext);
    }
}
