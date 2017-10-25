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

package org.ubl.iiifproducer.doc;

import static java.lang.System.out;
import static java.nio.file.Paths.get;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static org.ubl.iiifproducer.doc.MetsData.Logical;
import static org.ubl.iiifproducer.doc.MetsData.Xlink;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getAttribution;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getFileResources;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getHrefForFile;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getLogicalLabel;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getLogicalLastDescendent;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getLogicalLastParent;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getManifestTitle;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getManuscriptIdByType;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getMimeTypeForFile;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getPhysicalDivs;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getXlinks;
import static org.ubl.iiifproducer.doc.ResourceLoader.getMets;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;

/**
 * GetValuesFromMetsTest.
 *
 * @author christopher-johnson
 */
class GetValuesFromMetsTest {
    private String path = get(".").toAbsolutePath().normalize().getParent().toString();
    private String sourceFile = path + "/xml-doc/src/test/resources/mets/MS_187.xml";

    @Test
    void testGetTitle() throws IOException {
        MetsData mets = getMets(sourceFile);
        String id = getManifestTitle(mets);
    }

    @Test
    void testGetIdentifiers() throws IOException {
        MetsData mets = getMets(sourceFile);
        String id = getManuscriptIdByType(mets, "urn");
    }

    @Test
    void testGetAttribution() throws IOException {
        MetsData mets = getMets(sourceFile);
        String id = getAttribution(mets);
    }

    @Test
    void testGetPhysicalDivs() throws IOException {
        MetsData mets = getMets(sourceFile);
        List<String> divs = getPhysicalDivs(mets);
    }

    @Test
    void testGetFileResources() throws IOException {
        MetsData mets = getMets(sourceFile);
        List<String> files = getFileResources(mets);
    }

    @Test
    void testHrefForFile() throws IOException {
        MetsData mets = getMets(sourceFile);
        String href = getHrefForFile(mets, "FILE_0003_ORIGINAL");
        out.println(href);
    }

    @Test
    void testMimeTypeForFile() throws IOException {
        MetsData mets = getMets(sourceFile);
        String mtype = getMimeTypeForFile(mets, "FILE_0003_ORIGINAL");
        out.println(mtype);
    }

    @Test
    void testGetRangesFromXlinks() throws IOException {
        MetsData mets = getMets(sourceFile);
        List<Xlink> xlinks = getXlinks(mets);
        Map<String, List<Xlink>> map =
                xlinks.stream().collect(groupingBy(Xlink::getXLinkFrom));
        Set<String> ranges = map.keySet();

        out.println(xlinks);
    }

    @Test
    void testGetCanvasesForRange() throws IOException {
        MetsData mets = getMets(sourceFile);
        List<Xlink> xlinks = getXlinks(mets);
        Map<String, List<Xlink>> xlinkmap =
                xlinks.stream().collect(groupingBy(Xlink::getXLinkFrom));
        Map<String, List<String>> structures = new LinkedHashMap<>();
        for (String range : xlinkmap.keySet()) {
            List<Xlink> links = xlinkmap.get(range);
            List<String> canvases =
                    links.stream().map(Xlink::getXLinkTo).collect(toList());
            structures.put(range, canvases);
        }
    }

    @Test
    void testGetLabelForLogical() throws IOException {
        MetsData mets = getMets(sourceFile);
        List<Xlink> xlinks = getXlinks(mets);
        Map<String, List<Xlink>> xlinkmap =
                xlinks.stream().collect(groupingBy(Xlink::getXLinkFrom));
        xlinkmap.keySet().forEach(logical -> {
            String descLabel = getLogicalLabel(mets, logical);
            System.out.println(descLabel);
        });
    }

    @Test
    void testGetTypeForLogical() throws IOException {
        MetsData mets = getMets(sourceFile);
        List<Xlink> xlinks = getXlinks(mets);
        Map<String, List<Xlink>> xlinkmap =
                xlinks.stream().collect(groupingBy(Xlink::getXLinkFrom));
        xlinkmap.keySet().forEach(logical -> {
            List<Logical> last = getLogicalLastParent(mets, logical);
            last.forEach(p -> {
                String type = p.getLogicalType();
                System.out.println(type);
            });

        });
    }
}
