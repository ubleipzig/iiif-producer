package org.ubl.iiifproducer.template;

import static java.lang.System.out;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.ubl.iiifproducer.template.ManifestSerializer.serialize;

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
        List<String> ranges = asList(
                "https://iiif.ub.uni-leipzig.de/0000004084/range/0-0",
                "https://iiif.ub.uni-leipzig.de/0000004084/range/0-1",
                "https://iiif.ub.uni-leipzig.de/0000004084/range/0-2");
        List<String> canvases = asList(
                "https://iiif.ub.uni-leipzig.de/0000004084/canvas/1",
                "https://iiif.ub.uni-leipzig.de/0000004084/canvas/2");
        mockStructure.setStructureLabel("TOC");
        mockStructure.setStructureId("http://test.org/12345/range/0");
        mockStructure.setRanges(ranges);
        mockStructure.setCanvases(canvases);
        final Optional<String> json = serialize(mockStructure);
        assertTrue(json.isPresent());
        out.println(json.get());
    }

}

