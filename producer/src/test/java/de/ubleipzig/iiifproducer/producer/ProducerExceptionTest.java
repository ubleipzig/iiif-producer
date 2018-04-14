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

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ProducerExceptionTest {

    private static final Config config = new Config();

    @BeforeAll
    static void setup() {
        final String path = get(".").toAbsolutePath().normalize().getParent().toString();
        final String invalidPath = path + "/invalid-path";
        config.setInputFile(invalidPath);
        config.setTitle("ImageManifestTest");
    }

    @Test
    void testRuntimeException() {
        assertThrows(RuntimeException.class, () -> {
            final IIIFProducer producer = new IIIFProducer(config);
            producer.run();
        });
    }

    @Test
    void testIOException() {
        assertThrows(IOException.class, () -> new MetsImpl(config));
    }
}
