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

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static uk.org.webcompere.systemstubs.SystemStubs.catchSystemExit;

public class ProducerDriverTest extends AbstractProducerTest {

    @ParameterizedTest
    @MethodSource("supplyArguments")
    public void testProducer(String[] args) throws Exception {
        catchSystemExit(() -> {
            runProducer(args);
        });
    }

    @Test
    @Disabled
    public void HSPTest() {
        String[] args = new String[]{"-v", "0000004595", "-x", testFileSource1, "-o",
                "/tmp/" + pid + ".json", "-c", configFilePath};
        runProducer(args);
    }

    @Test
    @Disabled
    public void MultiVolumeWorkTest() {
        String[] args = new String[]{"-v", "0000004595", "-x", testFileSource6, "-o",
                "/tmp/" + pid + ".json", "-c", configFilePath};
        runProducer(args);
    }

    @Test
    @Disabled
    public void HSPTestV2() {
        String[] args = new String[]{"-v", "0000004595", "-x", testFileSource1, "-o",
                "/tmp/" + pid + ".json", "-c", configFilePath, "-f", "v2"};
        runProducer(args);
    }

}
