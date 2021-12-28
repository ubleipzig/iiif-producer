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
package de.ubleipzig.iiifproducer.template;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TemplateResourceTest {

    @Mock
    private TemplateResource mockResource = new TemplateResource();

    @Mock
    private TemplateService mockService = new TemplateService("trellis:data/testservice");

    @Test
    void testTemplateResource() {
        mockResource.setResourceFormat("image/tiff");
        mockResource.setResourceId("trellis:data/12345");
        mockResource.setResourceLabel("00001111");
        mockResource.setService(mockService);
        mockResource.setResourceHeight(200);
        mockResource.setResourceWidth(200);
        final Optional<String> json = ManifestSerializer.serialize(mockResource);
        assertTrue(json.isPresent());
        out.println(json.get());
    }
}
