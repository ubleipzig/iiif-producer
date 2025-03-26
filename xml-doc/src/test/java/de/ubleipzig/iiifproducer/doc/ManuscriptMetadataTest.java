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
import java.util.Objects;
import java.util.stream.Collectors;

import static de.ubleipzig.iiifproducer.doc.ResourceLoader.getMets;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ManuscriptMetadataTest {

    private List<String> getMetaDataAtomicValuesWithLabel(final List<Metadata> info, String label) {
        return info
                .stream()
                .filter(v -> v.getLabel() instanceof String && ((String) v.getLabel()).equals(label) || v.getLabel() instanceof LabelObject[] && Arrays.stream(((LabelObject[]) v.getLabel())).anyMatch(l -> l.getValue().equals(label)))
                .filter(v -> v.getValue() instanceof String)
                .map(v -> (String)v.getValue())
                .collect(Collectors.toList());
    }

    private List<String> getMetaDataListValuesWithLabel(final List<Metadata> info, String label) {
        return info
                .stream()
                .filter(v -> v.getLabel() instanceof String && ((String) v.getLabel()).equals(label) || v.getLabel() instanceof LabelObject[] && Arrays.stream(((LabelObject[]) v.getLabel())).anyMatch(l -> l.getValue().equals(label)))
                .filter(v -> v.getValue() instanceof List)
                .flatMap(v -> ((List<String>) v.getValue()).stream())
                .collect(Collectors.toList());
    }

    @Test
    void testManuscriptMetadataDoesNotContainOwners() {
        final String sourceFile = Objects.requireNonNull(
                GetValuesFromMetsTest.class.getResource("/mets/MS_187.xml")).getPath();
        final MetsData mets = getMets(sourceFile);
        final List<Metadata> metadata = new ManuscriptMetadata(mets).getInfo();
        List<String> owners = getMetaDataAtomicValuesWithLabel(metadata, "Owner");
        assertEquals(0, owners.size());
    }

    @Test
    void testManuscriptMetadataDoesOnlyContainGivenOwnersAsEigentuemer() {
        final String sourceFile = Objects.requireNonNull(
                GetValuesFromMetsTest.class.getResource("/mets/MS_187.xml")).getPath();
        final MetsData mets = getMets(sourceFile);
        final List<Metadata> metadata = new ManuscriptMetadata(mets).getInfo();
        List<String> eigentuemerOriginal = getMetaDataAtomicValuesWithLabel(metadata, "Besitzer des Originals");
        assertEquals(0, eigentuemerOriginal.size());
        List<String> eigentuemerDigitalisat = getMetaDataAtomicValuesWithLabel(metadata, "Besitzer des Digitalisats");
        assertEquals(1, eigentuemerDigitalisat.size());
        assertEquals("Leipzig University Library", eigentuemerDigitalisat.get(0));

        final String annabergSourceFile = Objects.requireNonNull(
                GetValuesFromMetsTest.class.getResource("/mets/C_6_FLIEGENDES_BLATT.xml")).getPath();
        final MetsData annabergMets = getMets(annabergSourceFile);
        final List<Metadata> annabergMetadata = new ManuscriptMetadata(annabergMets).getInfo();
        List<String> annabergEigentuemerOriginal = getMetaDataAtomicValuesWithLabel(annabergMetadata, "Besitzer des Originals");
        assertEquals(1, annabergEigentuemerOriginal.size());
        assertEquals("Evangelisch-lutherische Kirchgemeinde Annaberg-Buchholz", annabergEigentuemerOriginal.get(0));
        List<String> annabergEigentuemerDigitalisat = getMetaDataAtomicValuesWithLabel(annabergMetadata, "Besitzer des Digitalisats");
        assertEquals(1, annabergEigentuemerDigitalisat.size());
        assertEquals("Leipzig University Library", annabergEigentuemerDigitalisat.get(0));
    }

    @Test
    void testManuscriptMetadataDateCreated() {
        final String sourceFile = Objects.requireNonNull(
                GetValuesFromMetsTest.class.getResource("/mets/MS_187.xml")).getPath();
        final MetsData mets = getMets(sourceFile);
        final List<Metadata> metadata = new ManuscriptMetadata(mets).getInfo();
        List<String> datesCreated = getMetaDataListValuesWithLabel(metadata, "Date of origin");
        assertEquals(1, datesCreated.size());
        assertEquals("13. Jahrhundert", datesCreated.get(0));
    }
}
