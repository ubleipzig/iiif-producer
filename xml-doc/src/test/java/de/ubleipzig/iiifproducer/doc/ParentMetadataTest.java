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
import de.ubleipzig.iiifproducer.model.v2.LabelObject;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static de.ubleipzig.iiifproducer.doc.ResourceLoader.getMets;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParentMetadataTest {

    private List<String> getMetaDataAtomicValuesWithLabel(StandardMetadata md, String label) {
        final List<Metadata> info = md.getInfo();
        return info
                .stream()
                .filter(v -> v.getLabel() instanceof String && ((String) v.getLabel()).equals(label) || v.getLabel() instanceof LabelObject[] && Arrays.stream(((LabelObject[]) v.getLabel())).anyMatch(l -> l.getValue().equals(label)))
                .filter(v -> v.getValue() instanceof String)
                .map(v -> (String)v.getValue())
                .collect(Collectors.toList());
    }

    @Test
    void testGetAnchorFileMetadata() {
        final String sourceFile = GetValuesFromMetsTest.class.getResource("/mets/BntItin_021340072.xml").getPath();
        MetsData mets = getMets(sourceFile);

        StandardMetadata metaData = new StandardMetadata(mets);

        final List<String> partOf = getMetaDataAtomicValuesWithLabel(metaData, "Part of");
        assertEquals(1, partOf.size());
        assertEquals("Itinerarivm Sacrae Scriptvrae, Das ist: Ein Reisebuch vber die gantze heilige Schrifft; 1", partOf.get(0));
    }
}
