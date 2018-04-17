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

import static java.nio.file.Paths.get;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ProducerExceptionTest {

    private static final Config config = new Config();

    @Test
    void testRuntimeException() {
        final String path = get(".").toAbsolutePath().normalize().getParent().toString();
        final String invalidPath = path + "/invalid-path";
        config.setInputFile(invalidPath);
        config.setTitle("ImageManifestTest");
        config.setSerializeImageManifest(false);
        assertThrows(RuntimeException.class, () -> {
            final IIIFProducer producer = new IIIFProducer(config);
            producer.run();
        });
    }

    @Test
    void testRuntimeExceptionOptional() {
        final String path = get(".").toAbsolutePath().normalize().getParent().toString();
        final String invalidPath = path + "/invalid-path";
        config.setInputFile(invalidPath);
        config.setTitle("ImageManifestTest");
        config.setSerializeImageManifest(true);
        assertThrows(RuntimeException.class, () -> {
            final IIIFProducer producer = new IIIFProducer(config);
            producer.run();
        });
    }

    @Test
    void testIOException() {
        final String path = get(".").toAbsolutePath().normalize().getParent().toString();
        final String testFileSource = path + "/xml-doc/src/test/resources/mets/invalid.xml";
        config.setInputFile(testFileSource);
        config.setTitle("ImageManifestTest");
        assertThrows(RuntimeException.class, () -> {
            final IIIFProducer producer = new IIIFProducer(config);
            producer.run();
        });
    }
}
