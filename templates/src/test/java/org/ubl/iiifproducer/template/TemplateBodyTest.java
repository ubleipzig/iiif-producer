package org.ubl.iiifproducer.template;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.ubl.iiifproducer.template.ManifestSerializer.serialize;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

/**
 * TemplateBodyTest.
 *
 * @author christopher-johnson
 */
class TemplateBodyTest {

    @Mock
    private TemplateBody mockBody;

    @Test
    void testSerialization() {
        mockBody = new TemplateBody();
        mockBody.setId("http://test.org/001");
        final Optional<String> json = serialize(mockBody);
        assertTrue(json.isPresent());
        assertTrue(json.get().contains("\"@id\":\"http://test.org/001\""));
        out.println(json.get());
    }

}
