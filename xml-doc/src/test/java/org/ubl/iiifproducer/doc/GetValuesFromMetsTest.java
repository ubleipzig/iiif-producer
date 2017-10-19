package org.ubl.iiifproducer.doc;

import static java.lang.System.out;
import static java.nio.file.Paths.get;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.ubl.iiifproducer.doc.MetsData.Logical;
import static org.ubl.iiifproducer.doc.MetsData.Xlink;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getAttribution;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getFileResources;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getHrefForFile;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getLogical;
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
        Optional<String> id = getManuscriptIdByType(mets, "urn");
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
    void testGetLabelForRange() throws IOException {
        MetsData mets = getMets(sourceFile);
        List<Xlink> xlinks = getXlinks(mets);
        List<Logical> logDivs = getLogical(mets);
        Map<String, List<Xlink>> xlinkmap =
                xlinks.stream().collect(groupingBy(Xlink::getXLinkFrom));
        Map<String, String> logicalLabelMap = logDivs.stream().collect(
                toMap(Logical::getLogicalId, Logical::getLogicalLabel));
        for (String range : xlinkmap.keySet()) {
            String label = logicalLabelMap.get(range);
            out.println(label);
        }
    }

    @Test
    void testGetTypeForRange() throws IOException {
        MetsData mets = getMets(sourceFile);
        List<Xlink> xlinks = getXlinks(mets);
        List<Logical> logDivs = getLogical(mets);
        Map<String, List<Xlink>> xlinkmap =
                xlinks.stream().collect(groupingBy(Xlink::getXLinkFrom));
        Map<String, String> logicalLabelMap = logDivs.stream().collect(
                toMap(Logical::getLogicalId, Logical::getLogicalType));
        for (String range : xlinkmap.keySet()) {
            String type = logicalLabelMap.get(range);
            out.println(type);
        }

    }


}
