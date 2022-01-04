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

import de.ubleipzig.iiifproducer.model.v2.Manifest;
import de.ubleipzig.iiifproducer.model.v2.Structure;
import de.ubleipzig.iiifproducer.model.v2.TopStructure;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Paths.get;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * StructuresTest.
 *
 * @author christopher-johnson
 */
class StructuresTest {

    private static String xmlFile2;
    private static MetsAccessor mets;
    private static IRIBuilder iriBuilder;

    @BeforeAll
    static void testBuildStructures() {
        final String path = get(".").toAbsolutePath().normalize().getParent().toString();
        String xmlFile = path + "/xml-doc/src/test/resources/mets/MS_85.xml";

        iriBuilder = IRIBuilder.builder()
                .build();

        mets = MetsImpl.builder()
                .iriBuilder(iriBuilder)
                .xmlFile(xmlFile)
                .mets()
                .xlinkmap()
                .build();
        xmlFile2 = path + "/producer/src/test/resources/ArumDomi_034678301.xml";
    }

    @Test
    void buildStructures() {
        final List<Structure> structures = mets.buildStructures();
        assertNotNull(structures.get(0));
    }

    @Test
    void buildStructures2() {
        final MetsAccessor mets = MetsImpl.builder()
                .iriBuilder(iriBuilder)
                .xmlFile(xmlFile2)
                .mets()
                .xlinkmap()
                .build();
        final List<Structure> structures = mets.buildStructures();
        assertTrue(structures.isEmpty());
    }

    @Test
    void buildTopStructure() {
        final Structure structure = mets.buildTopStructure();
        assertNotNull(structure);
    }

    @Test
    void testSetStructuresIfSet() {
        final IIIFProducer producer = IIIFProducer.builder()
                .iriBuilder(iriBuilder)
                .mets(mets)
                .outputFile("/tmp/test.json")
                .viewId("004285964")
                .build();
        final TopStructure top = TopStructure.builder().build();
        final List<String> ranges = new ArrayList<>();
        ranges.add("http://some-range/r1");
        top.setRanges(ranges);
        final Manifest manifest = Manifest.builder().build();
        producer.setStructures(top, manifest);
        assertTrue(manifest.getStructures().size() > 1 );
    }

    @Test
    void testSetStructuresDoNotSet() {
        final IIIFProducer producer = IIIFProducer.builder()
                .iriBuilder(iriBuilder)
                .mets(mets)
                .outputFile("/tmp/test.json")
                .viewId("004285964")
                .build();
        final TopStructure top = TopStructure.builder().build();
        final List<String> ranges = new ArrayList<>();
        top.setRanges(ranges);
        final Manifest manifest = Manifest.builder().build();
        producer.setStructures(top, manifest);
        assertNull(manifest.getStructures());
    }

    @Test
    void testBuildStructureMetadata() {

    }
}

