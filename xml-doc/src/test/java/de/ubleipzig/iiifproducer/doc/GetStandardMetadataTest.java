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
package de.ubleipzig.iiifproducer.doc;

import de.ubleipzig.iiifproducer.model.Metadata;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

import static de.ubleipzig.iiifproducer.doc.ResourceLoader.getMets;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetStandardMetadataTest {

    @Test
    void getStandardMetadataWithOptionalCollection() {
        final String sourceFile = Objects.requireNonNull(
                GetValuesFromMetsTest.class.getResource("/mets/AllgCaHaD_045008345.xml")).getPath();
        final MetsData mets = getMets(sourceFile);
        final StandardMetadata man = new StandardMetadata(mets);
        final List<Metadata> info = man.getInfo();
        assertEquals(1, (int) info.stream().filter(
                v -> v.getLabel() instanceof String && ((String) v.getLabel()).contains("VD17")).count());
    }

    @Test
    void getStandardMetadataWithOptionalCollection2() {
        final String sourceFile = Objects.requireNonNull(
                GetValuesFromMetsTest.class.getResource("/mets/LuthEnch_029330009.xml")).getPath();
        final MetsData mets = getMets(sourceFile);
        final StandardMetadata man = new StandardMetadata(mets);
        final List<Metadata> info = man.getInfo();
        assertEquals(1, (int) info.stream().filter(
                v -> v.getLabel() instanceof String && ((String) v.getLabel()).contains("VD16")).count());
    }
}
