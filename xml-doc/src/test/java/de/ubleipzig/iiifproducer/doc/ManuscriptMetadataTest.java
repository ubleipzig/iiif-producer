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
import java.util.stream.Collectors;

import static de.ubleipzig.iiifproducer.doc.ResourceLoader.getMets;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ManuscriptMetadataTest {
    @Test
    void testManuscriptMetadataDoesNotContainOwners() {
        final String sourceFile = Objects.requireNonNull(
                GetValuesFromMetsTest.class.getResource("/mets/MS_187.xml")).getPath();
        final MetsData mets = getMets(sourceFile);
        final List<Metadata> metadata = new ManuscriptMetadata(mets).getInfo();
        List<Metadata> owners = metadata.stream().filter(md -> ((String)md.getLabel()).contains("Owner")).collect(Collectors.toList());
        assertEquals(0, owners.size());
    }

    @Test
    void testManuscriptMetadataDoesOnlyContainGivenOwnersAsEigentuemer() {
        final String sourceFile = Objects.requireNonNull(
                GetValuesFromMetsTest.class.getResource("/mets/MS_187.xml")).getPath();
        final MetsData mets = getMets(sourceFile);
        final List<Metadata> metadata = new ManuscriptMetadata(mets).getInfo();
        List<Metadata> eigentuemerOriginal = metadata.stream().filter(md -> ((String)md.getLabel()).contains("Besitzer des Originals")).collect(Collectors.toList());
        assertEquals(0, eigentuemerOriginal.size());
        List<Metadata> eigentuemerDigitalisat = metadata.stream().filter(md -> ((String)md.getLabel()).contains("Besitzer des Digitalisats")).collect(Collectors.toList());
        assertEquals(1, eigentuemerDigitalisat.size());
        assertEquals("Leipzig University Library", eigentuemerDigitalisat.get(0).getValue());

        final String annabergSourceFile = Objects.requireNonNull(
                GetValuesFromMetsTest.class.getResource("/mets/C_6_FLIEGENDES_BLATT.xml")).getPath();
        final MetsData annabergMets = getMets(annabergSourceFile);
        final List<Metadata> annabergMetadata = new ManuscriptMetadata(annabergMets).getInfo();
        List<Metadata> annabergEigentuemerOriginal = annabergMetadata.stream().filter(md -> ((String)md.getLabel()).contains("Besitzer des Originals")).collect(Collectors.toList());
        assertEquals(1, annabergEigentuemerOriginal.size());
        assertEquals("Evangelisch-lutherische Kirchgemeinde Annaberg-Buchholz", annabergEigentuemerOriginal.get(0).getValue());
        List<Metadata> annabergEigentuemerDigitalisat = annabergMetadata.stream().filter(md -> ((String)md.getLabel()).contains("Besitzer des Digitalisats")).collect(Collectors.toList());
        assertEquals(1, annabergEigentuemerDigitalisat.size());
        assertEquals("Leipzig University Library", annabergEigentuemerDigitalisat.get(0).getValue());
    }
}
