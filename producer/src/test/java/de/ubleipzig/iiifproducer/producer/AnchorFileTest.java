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

import de.ubleipzig.iiifproducer.model.Metadata;
import de.ubleipzig.iiifproducer.model.v2.Manifest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static java.nio.file.Paths.get;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnchorFileTest {

    private static MetsAccessor mets;

    @BeforeAll
    static void setup() {
        final String path = get(".").toAbsolutePath().normalize().getParent().toString();
        final String sourceFile = path + "/xml-doc/src/test/resources/mets/BntItin_021340072.xml";
        mets = MetsImpl.builder()
                .anchorKey("Part of")
                .xmlFile(sourceFile)
                .mets()
                .xlinkmap()
                .build();
    }

    @Test
    void testGetAnchorFileMetadata() {
        final Metadata metadata = mets.getAnchorFileMetadata();
        assertEquals("Part of", metadata.getLabel());
        assertEquals(
                "Itinerarivm Sacrae Scriptvrae, Das ist: Ein Reisebuch vber die gantze heilige Schrifft; 1",
                metadata.getValue());
    }

    @Test
    void testSetAnchorFileMetadata() {
        final Manifest manifest = Manifest.builder().build();
        mets.setManifestLabel(manifest);
        mets.setMetadata(manifest);
    }
}
