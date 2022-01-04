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

import de.ubleipzig.iiifproducer.model.v2.Structure;
import de.ubleipzig.iiifproducer.model.v2.TopStructure;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static de.ubleipzig.iiifproducer.model.ManifestSerializer.serialize;
import static java.lang.System.out;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * TemplateTopStructureTest.
 *
 * @author christopher-johnson
 */
class TemplateTopStructureTest {

    @Mock
    private static Structure mockTopStructure = TopStructure.builder().build();

    @BeforeAll
    static void setup() {
        final List<String> ranges = asList(
                "https://iiif.ub.uni-leipzig.de/0000004084/range/0-0",
                "https://iiif.ub" + ".uni-leipzig.de/0000004084/range/0-1",
                "https://iiif.ub.uni-leipzig.de/0000004084/range/0-2");
        mockTopStructure.setLabel("TOC");
        mockTopStructure.setId("http://test.org/12345/range/0");
        mockTopStructure.setRanges(ranges);
    }

    @Test
    void testSerialization() {
        final Optional<String> json = serialize(mockTopStructure);
        assertTrue(json.isPresent());
        out.println(json.get());
    }
}
