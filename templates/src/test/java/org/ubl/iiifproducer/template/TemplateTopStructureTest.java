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
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.ubl.iiifproducer.template.ManifestSerializer.serialize;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

/**
 * TemplateTopStructureTest.
 *
 * @author christopher-johnson
 */
class TemplateTopStructureTest {

    @Mock
    private TemplateTopStructure mockTopStructure = new TemplateTopStructure();

    @Test
    void testSerialization() {
        final List<String> ranges = asList(
                "https://iiif.ub.uni-leipzig.de/0000004084/range/0-0",
                "https://iiif.ub" + ".uni-leipzig.de/0000004084/range/0-1",
                "https://iiif.ub.uni-leipzig.de/0000004084/range/0-2");
        mockTopStructure.setStructureLabel("TOC");
        mockTopStructure.setStructureId("http://test.org/12345/range/0");
        mockTopStructure.setRanges(ranges);
        final Optional<String> json = serialize(mockTopStructure);
        assertTrue(json.isPresent());
        out.println(json.get());
    }

}