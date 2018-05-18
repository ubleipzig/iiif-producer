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
import static org.junit.jupiter.api.Assertions.assertEquals;

import de.ubleipzig.iiifproducer.template.TemplateManifest;
import de.ubleipzig.iiifproducer.template.TemplateMetadata;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AnchorFileTest {

    private static Config config = new Config();

    @BeforeAll
    static void setup() {
        final String path = get(".").toAbsolutePath().normalize().getParent().toString();
        final String sourceFile = path + "/xml-doc/src/test/resources/mets/BntItin_021340072.xml";
        config.setXmlFile(sourceFile);
        config.setOutputFile("/tmp/test.json");
        config.setViewId("004285964");
        config.setAnchorKey("Part of");
    }

    @Test
    void testGetAnchorFileMetadata() {
        final MetsAccessor mets = new MetsImpl(config);
        final TemplateMetadata metadata = mets.getAnchorFileMetadata();
        assertEquals("Part of", metadata.getLabel());
        assertEquals(
                "Itinerarivm Sacrae Scriptvrae, Das ist: Ein Reisebuch vber die gantze heilige Schrifft; 1",
                metadata.getValue());
    }

    @Test
    void testSetAnchorFileMetadata() {
        final MetsAccessor mets = new MetsImpl(config);
        final TemplateManifest manifest = new TemplateManifest();
        mets.setManifestLabel(manifest);
        mets.setMetadata(manifest);
    }
}
