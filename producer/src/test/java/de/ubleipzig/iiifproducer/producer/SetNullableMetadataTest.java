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

package de.ubleipzig.iiifproducer.producer;

import static de.ubleipzig.iiifproducer.template.ManifestSerializer.serialize;
import static java.lang.System.out;
import static java.nio.file.Paths.get;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.ubleipzig.iiifproducer.template.TemplateManifest;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * SetNullableMetadataTest.
 *
 * @author christopher-johnson
 */
public class SetNullableMetadataTest {

    private static String sourceFile;

    @BeforeAll
    static void testBuildStructures() throws IOException {
        final String path = get(".").toAbsolutePath().normalize().getParent().toString();
        sourceFile = path + "/xml-doc/src/test/resources/mets/BlhDie_004285964.xml";
    }

    @Test
    void testSetManuscriptMetadata() throws IOException {
        final Config config = new Config();
        config.setInputFile(sourceFile);
        config.setTitle("BlhDie_004285964");
        config.setOutputFile("/tmp/test.json");
        config.setViewId("004285964");
        final MetsAccessor mets = new MetsImpl(config);
        final TemplateManifest body = new TemplateManifest();
        mets.setHandschriftMetadata(body);
        final Optional<String> json = serialize(body);
        assertTrue(json.isPresent());
        out.println(json.get());
    }

    @Test
    void testSetMetadata() throws IOException {
        final Config config = new Config();
        config.setInputFile(sourceFile);
        config.setTitle("BlhDie_004285964");
        config.setOutputFile("/tmp/test.json");
        config.setViewId("004285964");
        final MetsAccessor mets = new MetsImpl(config);
        final TemplateManifest body = new TemplateManifest();
        mets.setMetadata(body);
        final Optional<String> json = serialize(body);
        assertTrue(json.isPresent());
        out.println(json.get());
    }
}
