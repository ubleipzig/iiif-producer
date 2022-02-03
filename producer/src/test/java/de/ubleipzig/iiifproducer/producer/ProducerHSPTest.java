package de.ubleipzig.iiifproducer.producer;/*
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

import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.UUID;

public class ProducerHSPTest {

    private static final String configFilePath = Objects.requireNonNull(ProducerHSPTest.class.getResource(
            "/producer-config-test.yml")).getPath();
    private static final String pid = "producer-test-" + UUID.randomUUID();

    @Test
    public void testHSPType() {
        String testFileSource1 = Objects.requireNonNull(
                ProducerHSPTest.class.getResource("/DieHadeT_1525437259.xml")).getPath();
        final String[] args = new String[]{"-v", "0000004595", "-x", testFileSource1, "-o",
                "/tmp/" + pid + ".json", "-c", configFilePath};
        IIIFProducerDriver.main(args);
    }

}
