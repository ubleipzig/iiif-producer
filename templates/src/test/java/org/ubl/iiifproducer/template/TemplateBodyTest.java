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

package org.ubl.iiifproducer.template;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.ubl.iiifproducer.template.ManifestSerializer.serialize;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

/**
 * TemplateBodyTest.
 *
 * @author christopher-johnson
 */
class TemplateBodyTest {

    @Mock
    private TemplateBody mockBody;

    @Test
    void testSerialization() {
        mockBody = new TemplateBody();
        mockBody.setId("http://test.org/001");
        final Optional<String> json = serialize(mockBody);
        assertTrue(json.isPresent());
        assertTrue(json.get().contains("\"@id\":\"http://test.org/001\""));
        out.println(json.get());
    }

}
