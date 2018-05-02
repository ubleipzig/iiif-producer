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

import static de.ubleipzig.iiifproducer.doc.ResourceLoader.getMets;
import static org.junit.jupiter.api.Assertions.assertEquals;

import de.ubleipzig.iiifproducer.template.TemplateMetadata;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class GetStandardMetadataTest {

    @Test
    void getStandardMetadataWithOptionalCollection() {
        final String sourceFile = GetValuesFromMetsTest.class.getResource("/mets/AllgCaHaD_045008345.xml").getPath();
        final MetsData mets = getMets(sourceFile);
        final StandardMetadata man = new StandardMetadata(mets);
        final List<TemplateMetadata> info = man.getInfo();
        assertEquals(1, info.stream().filter(x -> x.getLabel().contains("VD17")).collect(Collectors.toList()).size());
    }

    @Test
    void getStandardMetadataWithOptionalCollection2() {
        final String sourceFile = GetValuesFromMetsTest.class.getResource("/mets/LuthEnch_029330009.xml").getPath();
        final MetsData mets = getMets(sourceFile);
        final StandardMetadata man = new StandardMetadata(mets);
        final List<TemplateMetadata> info = man.getInfo();
        assertEquals(1, info.stream().filter(x -> x.getLabel().contains("VD16")).collect(Collectors.toList()).size());
    }
}
