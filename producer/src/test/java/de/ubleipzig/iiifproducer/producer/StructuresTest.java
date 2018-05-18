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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.ubleipzig.iiifproducer.template.TemplateManifest;
import de.ubleipzig.iiifproducer.template.TemplateStructure;
import de.ubleipzig.iiifproducer.template.TemplateTopStructure;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * StructuresTest.
 *
 * @author christopher-johnson
 */
class StructuresTest {

    private static String xmlFile;

    @BeforeAll
    static void testBuildStructures() {
        final String path = get(".").toAbsolutePath().normalize().getParent().toString();
        xmlFile = path + "/xml-doc/src/test/resources/mets/MS_85.xml";
    }

    @Test
    void buildStructures() {
        final Config config = new Config();
        config.setXmlFile(xmlFile);
        config.setOutputFile("/tmp/test.json");
        config.setViewId("004285964");
        final MetsAccessor mets = new MetsImpl(config);
        final List<TemplateStructure> structures = mets.buildStructures();
        assertNotNull(structures.get(0));
    }

    @Test
    void buildTopStructure() {
        final Config config = new Config();
        config.setXmlFile(xmlFile);
        config.setOutputFile("/tmp/test.json");
        config.setViewId("004285964");
        final MetsAccessor mets = new MetsImpl(config);
        final TemplateStructure structure = mets.buildTopStructure();
        assertNotNull(structure);
    }

    @Test
    void testSetStructuresIfSet() {
        final Config config = new Config();
        config.setXmlFile(xmlFile);
        config.setOutputFile("/tmp/test.json");
        config.setViewId("004285964");
        final MetsAccessor mets = new MetsImpl(config);
        final IIIFProducer producer = new IIIFProducer(config);
        final TemplateTopStructure top = new TemplateTopStructure();
        final List<String> ranges = new ArrayList<>();
        ranges.add("http://some-range/r1");
        top.setRanges(ranges);
        final TemplateManifest manifest = new TemplateManifest();
        producer.setStructures(top, manifest, mets);
        assertTrue(manifest.getStructures().size() > 1 );
    }

    @Test
    void testSetStructuresDoNotSet() {
        final Config config = new Config();
        config.setXmlFile(xmlFile);
        config.setOutputFile("/tmp/test.json");
        config.setViewId("004285964");
        final MetsAccessor mets = new MetsImpl(config);
        final IIIFProducer producer = new IIIFProducer(config);
        final TemplateTopStructure top = new TemplateTopStructure();
        final List<String> ranges = new ArrayList<>();
        top.setRanges(ranges);
        final TemplateManifest manifest = new TemplateManifest();
        producer.setStructures(top, manifest, mets);
        assertNull(manifest.getStructures());
    }
}

