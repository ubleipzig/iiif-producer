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
import static org.junit.jupiter.api.Assertions.*;

public class GetStandardMetadataTest {

    private List<String> getMetaDataValuesWithLabel(StandardMetadata md, String label) {
        final List<Metadata> info = md.getInfo();
        return info
                .stream()
                .filter(v -> v.getLabel() instanceof String && ((String) v.getLabel()).equals(label))
                .filter(v -> v.getValue() instanceof String)
                .map(v -> (String)v.getValue())
                .collect(Collectors.toList());
    }

    @Test
    void testGetStandardMetadataWithOptionalCollection() {
        final String sourceFile = Objects.requireNonNull(
                GetValuesFromMetsTest.class.getResource("/mets/AllgCaHaD_045008345.xml")).getPath();
        final MetsData mets = getMets(sourceFile);
        final StandardMetadata man = new StandardMetadata(mets);
        final List<Metadata> info = man.getInfo();
        assertEquals(1, (int)  info.stream().filter(
                v -> v.getLabel() instanceof String && ((String) v.getLabel()).contains("VD17")).count());
        List<String> collections = getMetaDataValuesWithLabel(man, "Collection");
        assertEquals(1, collections.size());
        assertFalse(collections.contains("VD17"));
        assertTrue(collections.contains("Drucke des 17. Jahrhunderts"));
    }

    @Test
    void testGetStandardMetadataWithOptionalCollection2() {
        final String sourceFile = Objects.requireNonNull(
                GetValuesFromMetsTest.class.getResource("/mets/LuthEnch_029330009.xml")).getPath();
        final MetsData mets = getMets(sourceFile);
        final StandardMetadata man = new StandardMetadata(mets);
        final List<Metadata> info = man.getInfo();
        assertEquals(1, (int) info.stream().filter(
                v -> v.getLabel() instanceof String && ((String) v.getLabel()).contains("VD16")).count());
        List<String> collections = getMetaDataValuesWithLabel(man, "Collection");
        assertEquals(1, collections.size());
        assertTrue(collections.contains("VD16"));
    }

    @Test
    void testGetStandardMetadataWithMultipleCollections() {
        final String sourceFile = Objects.requireNonNull(
                GetValuesFromMetsTest.class.getResource("/mets/Heisenberg.xml")).getPath();
        final MetsData mets = getMets(sourceFile);
        final StandardMetadata man = new StandardMetadata(mets);
        List<String> collections = getMetaDataValuesWithLabel(man, "Collection");
        assertEquals(2, collections.size());
        assertTrue(collections.contains("Nachlass Werner Heisenberg"));
        assertTrue(collections.contains("9. IV. Institutionen, 1. Korrespondenz: Academy of Human Rights, Rüschlikon bei Zürich"));
    }

    @Test
    void testGetStandardMetadataWithMultipleCollections2() {
        final String sourceFile = Objects.requireNonNull(
                GetValuesFromMetsTest.class.getResource("/mets/AktezuGed_1121300006.xml")).getPath();
        final MetsData mets = getMets(sourceFile);
        final StandardMetadata man = new StandardMetadata(mets);
        List<String> collections = getMetaDataValuesWithLabel(man, "Collection");
        assertEquals(2, collections.size());
        assertTrue(collections.contains("Saxonica"));
        assertTrue(collections.contains("Veröffentlichungen des Meißner Dombauvereins"));
    }

    @Test
    void testGetStandardDataWithOwnerOfOriginal() {
        final String sourceFile = GetValuesFromMetsTest.class.getResource("/mets/ProMSiG_1800085370.xml").getPath();
        MetsData mets = getMets(sourceFile);
        StandardMetadata md = new StandardMetadata(mets);
        List<String> ownerOfOriginal = getMetaDataValuesWithLabel(md, "Owner of original");
        assertEquals(1, ownerOfOriginal.size());
        assertTrue(ownerOfOriginal.contains("Annaberg-Buchholz, Evangelisch-Lutherische Kirchgemeinde Annaberg-Buchholz"));
    }

    @Test
    void testGetStandardDataWithoutOwnerOfOriginal() {
        final String sourceFile = GetValuesFromMetsTest.class.getResource("/mets/AllgCaHaD_045008345.xml").getPath();
        MetsData mets = getMets(sourceFile);
        StandardMetadata md = new StandardMetadata(mets);
        List<String> ownerOfOriginal = getMetaDataValuesWithLabel(md, "Owner of original");
        assertEquals(0, ownerOfOriginal.size());
    }

    @Test
    void testGetStandardDataWithOwnerOfDigitalCopy() {
        final String sourceFileUBL = GetValuesFromMetsTest.class.getResource("/mets/AllgCaHaD_045008345.xml").getPath();
        MetsData metsUBL = getMets(sourceFileUBL);
        StandardMetadata mdUBL = new StandardMetadata(metsUBL);
        // Ensure that old "Owner" label is no longer used
        List<String> ownersUBL = getMetaDataValuesWithLabel(mdUBL, "Owner");
        assertEquals(0, ownersUBL.size());
        // Check current desired output
        List<String> ownersOfDigitalCopyUBL = getMetaDataValuesWithLabel(mdUBL, "Owner of digital copy");
        assertEquals(1, ownersOfDigitalCopyUBL.size());
        assertTrue(ownersOfDigitalCopyUBL.contains("Leipzig University Library"));

        final String sourceFileAnnaberg = GetValuesFromMetsTest.class.getResource("/mets/ProMSiG_1800085370.xml").getPath();
        MetsData metsAnnaberg = getMets(sourceFileAnnaberg);
        StandardMetadata mdAnnaberg = new StandardMetadata(metsAnnaberg);
        List<String> ownersAnnaberg = getMetaDataValuesWithLabel(mdAnnaberg, "Owner");
        assertEquals(0, ownersAnnaberg.size());
        List<String> ownersOfDigitalCopyAnnaberg = getMetaDataValuesWithLabel(mdAnnaberg, "Owner of digital copy");
        assertEquals(1, ownersOfDigitalCopyAnnaberg.size());
        assertTrue(ownersOfDigitalCopyAnnaberg.contains("Leipzig University Library"));
    }
}
