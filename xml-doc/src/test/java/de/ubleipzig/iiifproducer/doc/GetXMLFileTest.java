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

package de.ubleipzig.iiifproducer.doc;

import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getMetsFromFile;
import static de.ubleipzig.iiifproducer.doc.ResourceLoader.getMetsAnchor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.util.Objects;

import org.junit.jupiter.api.Test;

public class GetXMLFileTest {

    @Test
    void testGetAnchorFile() {
        final String testFileSource = GetXMLFileTest.class.getResource("/mets/BntItin_021340072.xml").getPath();
        final MetsData mets = getMetsAnchor(testFileSource);
        String anchorFileLabel = "";
        for (String title : Objects.requireNonNull(mets).getManifestTitles()) {
            anchorFileLabel = title;
        }
        // FIXME: Warum die Zeilen oberhalb?
        anchorFileLabel = Objects.requireNonNull(mets).getManifestTitle().orElse("");
        assertEquals(
                "Itinerarivm Sacrae Scriptvrae, Das ist: Ein Reisebuch vber die gantze heilige Schrifft",
                anchorFileLabel);
    }

    @Test
    void testGetInvalidAnchorFile() {
        final String testFileSource = "/non-existing.xml";
        final MetsData mets = getMetsAnchor(testFileSource);
        assertNull(mets);
    }

    @Test
    void testIOException() {
        final String testFileSource = "/non-existing.xml";
        assertThrows(RuntimeException.class, () -> {
            getMetsFromFile(testFileSource);
        });
    }

    @Test
    void testIOExceptionWithFile() {
        final File testFileSource = new File("/non-existing.xml");
        assertThrows(RuntimeException.class, () -> {
            getMetsFromFile(testFileSource);
        });
    }
}
