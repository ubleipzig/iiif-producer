package org.ubl.iiifproducer.template;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.ubl.iiifproducer.template.ManifestSerializer.serialize;

import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

/**
 * TemplateMetadataTest.
 *
 * @author christopher-johnson
 */
class TemplateMetadataTest {

    @Mock
    private TemplateBody mockBody = new TemplateBody();

    @Test
    void testSerialization() {
        ArrayList<TemplateMetadata> meta = new ArrayList<>();
        meta.add(new TemplateMetadata("Kitodo", "12345"));
        meta.add(new TemplateMetadata("URN", "12345"));
        meta.add(new TemplateMetadata("Source PPN (SWB)", "12345"));
        final Optional<String> json = serialize(meta);
        assertTrue(json.isPresent());
        out.println(json.get());
    }

}
