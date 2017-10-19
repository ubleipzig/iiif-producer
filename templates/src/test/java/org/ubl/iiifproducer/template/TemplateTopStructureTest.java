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
 * TemplateTopStructureTest.
 *
 * @author christopher-johnson
 */
class TemplateTopStructureTest {

    @Mock
    private TemplateTopStructure mockTopStructure = new TemplateTopStructure();

    @Test
    void testSerialization() {
        List<String> ranges = asList(
                "https://iiif.ub.uni-leipzig.de/0000004084/range/0-0",
                "https://iiif.ub.uni-leipzig.de/0000004084/range/0-1",
                "https://iiif.ub.uni-leipzig.de/0000004084/range/0-2");
        mockTopStructure.setStructureLabel("TOC");
        mockTopStructure.setStructureId("http://test.org/12345/range/0");
        mockTopStructure.setRanges(ranges);
        final Optional<String> json = serialize(mockTopStructure);
        assertTrue(json.isPresent());
        out.println(json.get());
    }

}