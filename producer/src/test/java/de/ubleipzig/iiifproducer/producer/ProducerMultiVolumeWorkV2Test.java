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

import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.UUID;

public class ProducerMultiVolumeWorkV2Test {
    private static final String configFilePath = Objects.requireNonNull(ProducerMonographTest.class.getResource(
            "/producer-config-test.yml")).getPath();
    private static final String pid = "producer-test-" + UUID.randomUUID();

    @Test
    public void testMVTypeV2() {
        String testFileSource3 = Objects.requireNonNull(
                ProducerMonographTest.class.getResource("/ArumDomi_034678301.xml")).getPath();
        final String[] args = new String[]{"-v", "0000004595", "-x", testFileSource3, "-o",
                "/tmp/" + pid + ".json", "-c", configFilePath, "-f", "v2"};
        IIIFProducerDriver.main(args);
    }
}
