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

import org.junit.jupiter.api.Test;

public class IIIFProducerDriverTest {

    @Test
    public void testRunDriver() {
        final String path = get(".").toAbsolutePath().normalize().getParent().toString();
        final String testFileSource = path + "/xml-doc/src/test/resources/mets/BlhDie_004285964.xml";
        final String[] args = new String[]{"-v", "004285964", "-t", "BlhDie_004285964", "-i", testFileSource, "-o",
                "/tmp/null.json"};
        IIIFProducerDriver.main(args);
    }
}
