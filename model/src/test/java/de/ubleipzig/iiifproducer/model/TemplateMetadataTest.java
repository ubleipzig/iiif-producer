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

package de.ubleipzig.iiifproducer.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * TemplateMetadataTest.
 *
 * @author christopher-johnson
 */
class TemplateMetadataTest {

    @Test
    void testSerialization() {
        final List<Metadata> meta = new ArrayList<>();
        meta.add(Metadata.builder().label("Kitodo").value("12345").build());
        meta.add(Metadata.builder().label("URN").value("12345").build());
        meta.add(Metadata.builder().label("Source PPN (SWB)").value("12345").build());
        final Optional<String> json = ManifestSerializer.serialize(meta);
        assertTrue(json.isPresent());
        out.println(json.get());
    }

}
