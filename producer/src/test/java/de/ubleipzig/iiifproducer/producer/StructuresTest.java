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

import static java.nio.file.Paths.get;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import de.ubleipzig.iiifproducer.template.TemplateStructure;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * StructuresTest.
 *
 * @author christopher-johnson
 */
class StructuresTest {

    private static String sourceFile;

    @BeforeAll
    static void testBuildStructures() throws IOException {
        final String path = get(".").toAbsolutePath().normalize().getParent().toString();
        sourceFile = path + "/xml-doc/src/test/resources/mets/MS_85.xml";
    }

    @Test
    void buildStructures() throws IOException {
        final Config config = new Config();
        config.setInputFile(sourceFile);
        config.setTitle("BlhDie_004285964");
        config.setOutputFile("/tmp/test.json");
        config.setViewId("004285964");
        final MetsAccessor mets = new MetsImpl(config);
        final List<TemplateStructure> structures = mets.buildStructures();
        assertNotNull(structures.get(0));
    }

    @Test
    void buildTopStructure() throws IOException {
        final Config config = new Config();
        config.setInputFile(sourceFile);
        config.setTitle("BlhDie_004285964");
        config.setOutputFile("/tmp/test.json");
        config.setViewId("004285964");
        final MetsAccessor mets = new MetsImpl(config);
        final TemplateStructure structure = mets.buildTopStructure();
        assertNotNull(structure);
    }

}

