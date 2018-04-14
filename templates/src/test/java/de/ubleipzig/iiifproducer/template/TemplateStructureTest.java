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

import static de.ubleipzig.iiifproducer.template.ManifestSerializer.serialize;
import static java.lang.System.out;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

/**
 * TemplateStructureTest.
 *
 * @author christopher-johnson
 */
class TemplateStructureTest {

    @Mock
    private TemplateStructure mockStructure = new TemplateStructure();

    @Test
    void testSerialization() {
        final List<String> ranges = asList(
                "https://iiif.ub.uni-leipzig.de/0000004084/range/0-0",
                "https://iiif.ub" + ".uni-leipzig.de/0000004084/range/0-1",
                "https://iiif.ub.uni-leipzig.de/0000004084/range/0-2");
        final List<String> canvases = asList(
                "https://iiif.ub.uni-leipzig.de/0000004084/canvas/1",
                "https://iiif.ub" + ".uni-leipzig.de/0000004084/canvas/2");
        mockStructure.setStructureLabel("TOC");
        mockStructure.setStructureId("http://test.org/12345/range/0");
        mockStructure.setRanges(ranges);
        mockStructure.setCanvases(canvases);
        final Optional<String> json = serialize(mockStructure);
        assertTrue(json.isPresent());
        out.println(json.get());
    }

}
