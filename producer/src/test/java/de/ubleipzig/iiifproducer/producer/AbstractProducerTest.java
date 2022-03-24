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

import org.junit.jupiter.params.provider.Arguments;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

public class AbstractProducerTest {

    public static final String configFilePath = Objects.requireNonNull(AbstractProducerTest.class.getResource(
            "/producer-config-test.yml")).getPath();
    public static final String pid = "producer-test-" + UUID.randomUUID();

    public void runProducer(String[] args) {
        IIIFProducerDriver.main(args);
    }

    static String testFileSource1 = Objects.requireNonNull(
            AbstractProducerTest.class.getResource("/DieHadeT_1525437259.xml")).getPath();
    static String testFileSource2 = Objects.requireNonNull(
            AbstractProducerTest.class.getResource("/MS_187.xml")).getPath();
    static String testFileSource3 = Objects.requireNonNull(
            AbstractProducerTest.class.getResource("/MS_1495.xml")).getPath();
    static String testFileSource4 = Objects.requireNonNull(
            AbstractProducerTest.class.getResource("/BlhDie_004285964.xml")).getPath();
    static String testFileSource5 = Objects.requireNonNull(
            ProducerDriverTest.class.getResource("/JohaSpha_1499_470272783.xml")).getPath();
    static String testFileSource6 = Objects.requireNonNull(
            AbstractProducerTest.class.getResource("/ArumDomi_034678301.xml")).getPath();

    private static Stream<Arguments> supplyArguments() {
        return Stream.of(
                Arguments.of((Object) new String[]{"-v", "0000004595", "-x", testFileSource1, "-o",
                        "/tmp/" + pid + ".json", "-c", configFilePath}),
                Arguments.of((Object) new String[]{"-v", "0000004595", "-x", testFileSource1, "-o",
                        "/tmp/" + pid + ".json", "-c", configFilePath, "-f", "v2"}),
                Arguments.of((Object) new String[]{"-v", "0000004595", "-x", testFileSource2, "-o",
                "/tmp/" + pid + ".json", "-c", configFilePath}),
                Arguments.of((Object) new String[]{"-v", "0000004595", "-x", testFileSource2, "-o",
                        "/tmp/" + pid + ".json", "-c", configFilePath, "-f", "v2"}),
                Arguments.of((Object) new String[]{"-v", "0000004595", "-x", testFileSource3, "-o",
                        "/tmp/" + pid + ".json", "-c", configFilePath}),
                Arguments.of((Object) new String[]{"-v", "0000004595", "-x", testFileSource3, "-o",
                        "/tmp/" + pid + ".json", "-c", configFilePath, "-f", "v2"}),
                Arguments.of((Object) new String[]{"-v", "0000004595", "-x", testFileSource4, "-o",
                        "/tmp/" + pid + ".json", "-c", configFilePath}),
                Arguments.of((Object) new String[]{"-v", "0000004595", "-x", testFileSource4, "-o",
                        "/tmp/" + pid + ".json", "-c", configFilePath, "-f", "v2"}),
                Arguments.of((Object) new String[]{"-v", "0000004595", "-x", testFileSource5, "-o",
                        "/tmp/" + pid + ".json", "-c", configFilePath}),
                Arguments.of((Object) new String[]{"-v", "0000004595", "-x", testFileSource5, "-o",
                        "/tmp/" + pid + ".json", "-c", configFilePath, "-f", "v2"}),
                Arguments.of((Object) new String[]{"-v", "0000004595", "-x", testFileSource6, "-o",
                        "/tmp/" + pid + ".json", "-c", configFilePath}),
                Arguments.of((Object) new String[]{"-v", "0000004595", "-x", testFileSource6, "-o",
                        "/tmp/" + pid + ".json", "-c", configFilePath, "-f", "v2"})
        );
    }
}
