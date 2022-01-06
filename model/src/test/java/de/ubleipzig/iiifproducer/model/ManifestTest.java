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

package de.ubleipzig.iiifproducer.model;

import de.ubleipzig.iiif.vocabulary.SCEnum;
import de.ubleipzig.iiifproducer.model.v2.Manifest;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * ManifestTest.
 *
 * @author christopher-johnson
 */
class ManifestTest {

    @Mock
    private Manifest mockManifest;

    @Test
    void testSerialization() {
        mockManifest = Manifest.builder().build();
        mockManifest.setId("http://test.org/001");
        mockManifest.setType(SCEnum.Manifest.compactedIRI());
        final Optional<String> json = ManifestSerializer.serialize(mockManifest);
        assertTrue(json.isPresent());
        assertTrue(json.get().contains("http://test.org/001"));
        System.out.println(json.get());
    }

    @Test
    void testLicense() {
        mockManifest = Manifest.builder().build();;
        mockManifest.setId("http://test.org/002");
        mockManifest.setType(SCEnum.Manifest.compactedIRI());
        mockManifest.setLicense(Collections.singletonList("http://rightsstatements.org/vocab/InC/1.0/"));
        final Optional<String> json = ManifestSerializer.serialize(mockManifest);
        assertTrue(json.isPresent());
        assertTrue(json.get().contains("\"license\" : \"http://rightsstatements.org/vocab/InC/1.0/\""));

        mockManifest = Manifest.builder().build();;
        mockManifest.setId("http://test.org/002");
        mockManifest.setType(SCEnum.Manifest.compactedIRI());
        final List<String> licenses = Arrays.asList(
                "http://foo.test",
                "http://bar.test",
                "http://foobar.test"
        );
        mockManifest.setLicense(licenses);
        StringBuilder expected = new StringBuilder("\"license\" : [\n");
        expected.append("    \"http://foo.test\",\n");
        expected.append("    \"http://bar.test\",\n");
        expected.append("    \"http://foobar.test\"\n");
        expected.append("  ]");

        final Optional<String> multiLicenseJson = ManifestSerializer.serialize(mockManifest);
        assertTrue(multiLicenseJson.isPresent());
        assertTrue(multiLicenseJson.get().contains(expected));
    }

}
