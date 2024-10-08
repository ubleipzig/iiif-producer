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

import de.ubleipzig.iiif.vocabulary.SCEnum;
import de.ubleipzig.iiifproducer.model.v2.Member;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MemberTest {

    @Mock
    private Member mockMember = Member.builder()
            .id("trellis:data/member")
            .type(SCEnum.Canvas.compactedIRI())
            .label("a member")
            .build();

    @Test
    void testSerialization() {
        final Optional<String> json = ManifestSerializer.serialize(mockMember);
        assertTrue(json.isPresent());
        out.println(json.get());
    }
}
