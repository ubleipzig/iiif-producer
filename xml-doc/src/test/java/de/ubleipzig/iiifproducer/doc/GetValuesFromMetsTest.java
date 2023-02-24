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

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static de.ubleipzig.iiifproducer.doc.MetsConstants.URN_TYPE;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.*;
import static de.ubleipzig.iiifproducer.doc.ResourceLoader.getMets;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * GetValuesFromMetsTest.
 *
 * @author christopher-johnson
 */
class GetValuesFromMetsTest {

    private String sourceFile = GetValuesFromMetsTest.class.getResource("/mets/MS_187.xml").getPath();

    @Test
    void testGetTitle() {
        final MetsData mets = getMets(sourceFile);
        String id = "";
        for (String title : getManifestTitles(mets)) {
            id = title;
        }
        // FIXME was ist der Sinn der Änderungen oben?
        id = getManifestTitle(mets);
        assertEquals(id, "Leipzig, Universitätsbibliothek Leipzig, Ms 187");
    }

    @Test
    void testGetNote() {
        final MetsData mets = getMets(sourceFile);
        final String note = getNote(mets);
        assertEquals(note, "Goobi");
    }

    @Test
    void testGetIdentifiers() {
        final MetsData mets = getMets(sourceFile);
        final String id = getManuscriptIdByType(mets, URN_TYPE);
        assertEquals(id, "urn:nbn:de:bsz:15-0012-161875");
    }

    @Test
    void testGetAttribution() {
        final MetsData mets = getMets(sourceFile);
        final String id = getAttribution(mets);
        assertEquals(id, "Leipzig University Library");
    }

    @Test
    void testGetUrn() {
        final MetsData mets = getMets(sourceFile);
        final String id = getUrnReference(mets);
        assertEquals(id, "urn:nbn:de:bsz:15-0012-161875");
    }

    @Test
    void testGetPhysicalDivs() {
        final MetsData mets = getMets(sourceFile);
        final List<String> divs = getPhysicalDivs(mets);
        assertEquals(380, divs.size());
    }

    @Test
    void testHrefForFile() {
        final MetsData mets = getMets(sourceFile);
        final String href = getHrefForFile(mets, "FILE_0003_ORIGINAL");
        assertEquals("MS_187_tif/00000003.tif", href);
    }

    @Test
    void testMimeTypeForFile() {
        final MetsData mets = getMets(sourceFile);
        final String mtype = getMimeTypeForFile(mets, "FILE_0003_ORIGINAL");
        assertEquals("image/tiff", mtype);
    }

    @Test
    void testGetRangesFromXlinks() {
        final MetsData mets = getMets(sourceFile);
        final List<MetsData.Xlink> xlinks = getXlinks(mets);
        final Map<String, List<MetsData.Xlink>> map = xlinks.stream().collect(groupingBy(MetsData.Xlink::getXLinkFrom));
        final Set<String> ranges = map.keySet();
        assertEquals(21, ranges.size());
    }

    @Test
    void testGetCanvasesForRange() {
        final MetsData mets = getMets(sourceFile);
        final List<MetsData.Xlink> xlinks = getXlinks(mets);
        final Map<String, List<MetsData.Xlink>> xlinkmap = xlinks.stream().collect(
                groupingBy(MetsData.Xlink::getXLinkFrom));
        final Map<String, List<String>> structures = new LinkedHashMap<>();
        for (String range : xlinkmap.keySet()) {
            final List<MetsData.Xlink> links = xlinkmap.get(range);
            final List<String> canvases = links.stream().map(MetsData.Xlink::getXLinkTo).collect(toList());
            structures.put(range, canvases);
        }
        assertEquals(209, structures.entrySet().stream().filter(x -> x.getKey().equals("LOG_0014")).collect(
                Collectors.toList()).get(0).getValue().size());
    }

    @Test
    void testGetLabelForLogical() {
        final MetsData mets = getMets(sourceFile);
        final List<MetsData.Xlink> xlinks = getXlinks(mets);
        final Map<String, List<MetsData.Xlink>> xlinkmap = xlinks.stream().collect(
                groupingBy(MetsData.Xlink::getXLinkFrom));
        xlinkmap.keySet().forEach(logical -> {
            final String descLabel = getLogicalLabel(mets, logical);
            System.out.println(descLabel);
        });
    }

    @Test
    void testGetLabelForNonExistentResourceBundleKeyLogical() {
        final String sourceFile = GetValuesFromMetsTest.class.getResource(
                "/mets/JohaSpha_1499_470272783.xml").getPath();
        final MetsData mets = getMets(sourceFile);
        final List<MetsData.Xlink> xlinks = getXlinks(mets);
        final Map<String, List<MetsData.Xlink>> xlinkmap = xlinks.stream().collect(
                groupingBy(MetsData.Xlink::getXLinkFrom));
        xlinkmap.keySet().forEach(logical -> {
            final String descLabel = getLogicalLabel(mets, logical);
            System.out.println(descLabel);
        });
    }

    @Test
    void testGetTypeForLogical() {
        final MetsData mets = getMets(sourceFile);
        final List<MetsData.Xlink> xlinks = getXlinks(mets);
        final Map<String, List<MetsData.Xlink>> xlinkmap = xlinks.stream().collect(
                groupingBy(MetsData.Xlink::getXLinkFrom));
        xlinkmap.keySet().forEach(logical -> {
            final List<MetsData.Logical> last = getLogicalLastParent(mets, logical);
            last.forEach(p -> {
                final String type = p.getLogicalType();
                System.out.println(type);
            });
        });
    }

    @Test
    void testGetTopLogical() {
        final MetsData mets = getMets(sourceFile);
        final List<MetsData.Logical> logicals = mets.getTopLogicals();
        assertEquals(8, logicals.size());
    }

    @Test
    void testGetFileIdInFileGrp() {
        final String fileWithAlto = GetValuesFromMetsTest.class.getResource("/mets/DieHadeT_1525437259.xml").getPath();
        final MetsData mets = getMets(fileWithAlto);
        assertTrue(mets.getFileIdInFileGrp("FILE_0002_ORIGINAL", "ORIGINAL"));
        assertTrue(!mets.getFileIdInFileGrp("FILE_0002_FULLTEXT", "ORIGINAL"));
        assertTrue(mets.getFileIdInFileGrp("FILE_0002_FULLTEXT", "FULLTEXT"));
        assertTrue(!mets.getFileIdInFileGrp("FILE_0002_ORIGINAL", "FULLTEXT"));
    }

    @Test
    void testGetCopyrightHolders() {
        final String sourceFile
                = Objects.requireNonNull(
                HspCatalogStructureMetadataTest.class.getResource("/mets/DieHadeT_1525437259.xml")).getPath();
        final MetsData mets = getMets(sourceFile);
        final List<String> copyrightHolders = getCopyrightHolders(mets);
        assertIterableEquals(copyrightHolders, Collections.singletonList("Otto Harrassowitz GmbH & Co. KG"));
    }
}
