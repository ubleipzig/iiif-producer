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

package de.ubleipzig.iiifproducer.template;

import de.ubleipzig.iiif.vocabulary.SCEnum;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * TemplateManifestTest.
 *
 * @author christopher-johnson
 */
class TemplateManifestTest {

    @Mock
    private TemplateManifest mockManifest;

    @Test
    void testSerialization() {
        mockManifest = new TemplateManifest();
        mockManifest.setId("http://test.org/001");
        mockManifest.setType(SCEnum.Manifest.compactedIRI());
        final Optional<String> json = ManifestSerializer.serialize(mockManifest);
        assertTrue(json.isPresent());
        assertTrue(json.get().contains("http://test.org/001"));
        System.out.println(json.get());
    }

    @Test
    void testLicense() {
        mockManifest = new TemplateManifest();
        mockManifest.setId("http://test.org/002");
        mockManifest.setType(SCEnum.Manifest.compactedIRI());
        mockManifest.setLicense(Collections.singletonList("http://rightsstatements.org/vocab/InC/1.0/"));
        final Optional<String> json = ManifestSerializer.serialize(mockManifest);
        assertTrue(json.isPresent());
        assertTrue(json.get().contains("\"license\" : \"http://rightsstatements.org/vocab/InC/1.0/\""));

        mockManifest = new TemplateManifest();
        mockManifest.setId("http://test.org/002");
        mockManifest.setType(SCEnum.Manifest.compactedIRI());
        final List<String> licenses = Arrays.asList(
                "http://foo.test",
                "http://bar.test",
                "http://foobar.test"
        );
        mockManifest.setLicense(licenses);
        final String expected = "\"license\" : [\n" + "    \"http://foo.test\",\n" +
                "    \"http://bar.test\",\n" +
                "    \"http://foobar.test\"\n" +
                "  ]";

        final Optional<String> multiLicenseJson = ManifestSerializer.serialize(mockManifest);
        assertTrue(multiLicenseJson.isPresent());
        assertTrue(multiLicenseJson.get().contains(expected));
    }

}
