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

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.UUID;

public class ProducerMonographTest {

    private static final String configFilePath = Objects.requireNonNull(ProducerMonographTest.class.getResource(
            "/producer-config-test.yml")).getPath();
    private static final String pid = "producer-test-" + UUID.randomUUID();
    private static String testFileSource1;

    @Test
    @Order(1)
    public void testStandardType() {
        testFileSource1 = Objects.requireNonNull(
                ProducerMonographTest.class.getResource("/BlhDie_004285964.xml")).getPath();
        final String[] args = new String[]{"-v", "0000004595", "-x", testFileSource1, "-o",
                "/tmp/" + pid + ".json", "-c", configFilePath};
        IIIFProducerDriver.main(args);
    }

    @Test
    @Order(2)
    public void testStandardTypeV2() {
        final String[] args = new String[]{"-v", "0000004595", "-x", testFileSource1, "-o",
                "/tmp/" + pid + ".json", "-c", configFilePath, "-f", "v2"};
        IIIFProducerDriver.main(args);
    }

    @Test
    public void testStandardType2() {
        String testFileSource1 = Objects.requireNonNull(
                ProducerHSPTest.class.getResource("/JohaSpha_1499_470272783.xml")).getPath();
        final String[] args = new String[]{"-v", "0000004595", "-x", testFileSource1, "-o",
                "/tmp/" + pid + ".json", "-c", configFilePath};
        IIIFProducerDriver.main(args);
    }


}
