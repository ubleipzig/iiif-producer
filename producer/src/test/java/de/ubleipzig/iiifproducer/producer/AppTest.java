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

import java.io.IOException;

import org.junit.jupiter.api.Test;

/**
 * AppTest.
 *
 * @author christopher-johnson
 */
class AppTest {

    @Test
    void testApp() throws IOException {
        final String path = get(".").toAbsolutePath().normalize().getParent().toString();
        final String sourceFile = path + "/xml-doc/src/test/resources/mets/BlhDie_004285964.xml";

        final Config config = new Config();
        config.setInputFile(sourceFile);
        config.setTitle("BlhDie_004285964");
        config.setOutputFile("/tmp/test.json");
        config.setViewId("004285964");
        new IIIFProducer(config);
    }
}

