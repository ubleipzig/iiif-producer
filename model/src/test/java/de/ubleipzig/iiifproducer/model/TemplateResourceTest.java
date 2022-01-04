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
package de.ubleipzig.iiifproducer.model;

import de.ubleipzig.iiifproducer.model.v2.Body;
import de.ubleipzig.iiifproducer.model.v2.Service;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TemplateResourceTest {

    @Mock
    private Body mockBody = Body.builder().build();

    @Mock
    private Service mockService = Service.builder().id("trellis:data/testservice").build();

    @Test
    void testTemplateResource() {
        mockBody.setFormat("image/tiff");
        mockBody.setId("trellis:data/12345");
        mockBody.setLabel("00001111");
        mockBody.setService(mockService);
        mockBody.setHeight(200);
        mockBody.setWidth(200);
        final Optional<String> json = ManifestSerializer.serialize(mockBody);
        assertTrue(json.isPresent());
        out.println(json.get());
    }
}
