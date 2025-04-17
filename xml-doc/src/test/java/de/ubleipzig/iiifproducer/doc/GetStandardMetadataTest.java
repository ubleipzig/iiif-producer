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
import static org.junit.jupiter.api.Assertions.*;

public class GetStandardMetadataTest {

    private List<String> getMetaDataAtomicValuesWithLabel(StandardMetadata md, String label) {
        final List<Metadata> info = md.getInfo();
        return info
                .stream()
                .filter(v -> v.getLabel() instanceof String && ((String) v.getLabel()).equals(label) || v.getLabel() instanceof LabelObject[] && Arrays.stream(((LabelObject[]) v.getLabel())).anyMatch(l -> l.getValue().equals(label)))
                .filter(v -> v.getValue() instanceof String)
                .map(v -> (String)v.getValue())
                .collect(Collectors.toList());
    }

    private List<String> getMetaDataListValuesWithLabel(StandardMetadata md, String label) {
        final List<Metadata> info = md.getInfo();
        return info
                .stream()
                .filter(v -> v.getLabel() instanceof String && ((String) v.getLabel()).equals(label) || v.getLabel() instanceof LabelObject[] && Arrays.stream(((LabelObject[]) v.getLabel())).anyMatch(l -> l.getValue().equals(label)))
                .filter(v -> v.getValue() instanceof List)
                .flatMap(v -> ((List<String>) v.getValue()).stream())
                .collect(Collectors.toList());
    }

    private List<String> getMetaDataListValuesWithLabelFromManuscripts(ManuscriptMetadata md, String label) {
        final List<Metadata> info = md.getInfo();
        return info
                .stream()
                .filter(v -> v.getLabel() instanceof String && ((String) v.getLabel()).equals(label) || v.getLabel() instanceof LabelObject[] && Arrays.stream(((LabelObject[]) v.getLabel())).anyMatch(l -> l.getValue().equals(label)))
                .filter(v -> v.getValue() instanceof List)
                .flatMap(v -> ((List<String>) v.getValue()).stream())
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
        List<String> collections = getMetaDataAtomicValuesWithLabel(man, "Collection");
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
        List<String> collections = getMetaDataAtomicValuesWithLabel(man, "Collection");
        assertEquals(1, collections.size());
        assertTrue(collections.contains("VD16"));
    }

    @Test
    void testGetStandardMetadataWithMultipleCollections() {
        final String sourceFile = Objects.requireNonNull(
                GetValuesFromMetsTest.class.getResource("/mets/Heisenberg.xml")).getPath();
        final MetsData mets = getMets(sourceFile);
        final StandardMetadata man = new StandardMetadata(mets);
        List<String> collections = getMetaDataAtomicValuesWithLabel(man, "Collection");
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
        List<String> collections = getMetaDataAtomicValuesWithLabel(man, "Collection");
        assertEquals(2, collections.size());
        assertTrue(collections.contains("Saxonica"));
        assertTrue(collections.contains("Veröffentlichungen des Meißner Dombauvereins"));
    }

    @Test
    void testGetStandardDataWithOwnerOfOriginal() {
        final String sourceFile = GetValuesFromMetsTest.class.getResource("/mets/ProMSiG_1800085370.xml").getPath();
        MetsData mets = getMets(sourceFile);
        StandardMetadata md = new StandardMetadata(mets);
        List<String> ownerOfOriginal = getMetaDataAtomicValuesWithLabel(md, "Owner of original");
        assertEquals(1, ownerOfOriginal.size());
        assertTrue(ownerOfOriginal.contains("Annaberg-Buchholz, Evangelisch-Lutherische Kirchgemeinde Annaberg-Buchholz"));
    }

    @Test
    void testGetStandardDataWithoutOwnerOfOriginal() {
        final String sourceFile = GetValuesFromMetsTest.class.getResource("/mets/AllgCaHaD_045008345.xml").getPath();
        MetsData mets = getMets(sourceFile);
        StandardMetadata md = new StandardMetadata(mets);
        List<String> ownerOfOriginal = getMetaDataAtomicValuesWithLabel(md, "Owner of original");
        assertEquals(0, ownerOfOriginal.size());
    }

    @Test
    void testGetStandardDataWithOwnerOfDigitalCopy() {
        final String sourceFileUBL = GetValuesFromMetsTest.class.getResource("/mets/AllgCaHaD_045008345.xml").getPath();
        MetsData metsUBL = getMets(sourceFileUBL);
        StandardMetadata mdUBL = new StandardMetadata(metsUBL);
        // Ensure that old "Owner" label is no longer used
        List<String> ownersUBL = getMetaDataAtomicValuesWithLabel(mdUBL, "Owner");
        assertEquals(0, ownersUBL.size());
        // Check current desired output
        List<String> ownersOfDigitalCopyUBL = getMetaDataAtomicValuesWithLabel(mdUBL, "Owner of digital copy");
        assertEquals(1, ownersOfDigitalCopyUBL.size());
        assertTrue(ownersOfDigitalCopyUBL.contains("Leipzig University Library"));

        final String sourceFileAnnaberg = GetValuesFromMetsTest.class.getResource("/mets/ProMSiG_1800085370.xml").getPath();
        MetsData metsAnnaberg = getMets(sourceFileAnnaberg);
        StandardMetadata mdAnnaberg = new StandardMetadata(metsAnnaberg);
        List<String> ownersAnnaberg = getMetaDataAtomicValuesWithLabel(mdAnnaberg, "Owner");
        assertEquals(0, ownersAnnaberg.size());
        List<String> ownersOfDigitalCopyAnnaberg = getMetaDataAtomicValuesWithLabel(mdAnnaberg, "Owner of digital copy");
        assertEquals(1, ownersOfDigitalCopyAnnaberg.size());
        assertTrue(ownersOfDigitalCopyAnnaberg.contains("Leipzig University Library"));
    }

    @Test
    void testGetPlaces() {
        final String sourceFileWithSinglePlace = GetValuesFromMetsTest.class.getResource("/mets/ProMSiG_1800085370.xml").getPath();
        MetsData metsWithSinglePlace = getMets(sourceFileWithSinglePlace);
        StandardMetadata mdWithSinglePlace = new StandardMetadata(metsWithSinglePlace);
        List<String> placesSingle = getMetaDataListValuesWithLabel(mdWithSinglePlace, "Place of publication");
        assertEquals(1, placesSingle.size());
        assertEquals("Lignici[i]", placesSingle.get(0));

        final String sourceFileWithMultiplePlaces = GetValuesFromMetsTest.class.getResource("/mets/StimTePaf_1105086895.xml").getPath();
        MetsData metsWithMultiplePlaces = getMets(sourceFileWithMultiplePlaces);
        StandardMetadata mdWithMultiplePlaces = new StandardMetadata(metsWithMultiplePlaces);
        List<String> placesMultiple = getMetaDataListValuesWithLabel(mdWithMultiplePlaces, "Place of publication");
        assertEquals(2, placesMultiple.size());
        assertEquals("Gera]", placesMultiple.get(0));
        assertEquals("[Leipzig", placesMultiple.get(1));

        final String sourceFileWithSinglePlaceButMultiplePlaceTerms = GetValuesFromMetsTest.class.getResource("/mets/DieHadeT_1525437259.xml").getPath();
        MetsData metsWithSinglePlaceButMultiplePlaceTerms = getMets(sourceFileWithSinglePlaceButMultiplePlaceTerms);
        StandardMetadata mdWithSinglePlaceButMultiplePlaceTerms = new StandardMetadata(metsWithSinglePlaceButMultiplePlaceTerms);
        List<String> singlePlaceFromMultiplePlaceTerms = getMetaDataListValuesWithLabel(mdWithSinglePlaceButMultiplePlaceTerms, "Place of publication");
        assertEquals(1, singlePlaceFromMultiplePlaceTerms.size());
        assertEquals("Wiesbaden", singlePlaceFromMultiplePlaceTerms.get(0));
    }

    @Test
    void testGetMaterials() {
        final String sourceFileWithSingleMaterial = GetValuesFromMetsTest.class.getResource("/mets/MS_85.xml").getPath();
        MetsData metsWithSingleMaterial = getMets(sourceFileWithSingleMaterial);
        ManuscriptMetadata mdWithSingleMaterial = new ManuscriptMetadata(metsWithSingleMaterial);
        List<String> material = getMetaDataListValuesWithLabelFromManuscripts(mdWithSingleMaterial, "Beschreibstoff");
        assertEquals(1, material.size());
        assertEquals("Pergament", material.get(0));

        final String sourceFileWithMultipleMaterials = GetValuesFromMetsTest.class.getResource("/mets/MS_187.xml").getPath();
        MetsData metsWithMultipleMaterials = getMets(sourceFileWithMultipleMaterials);
        ManuscriptMetadata mdWithMultipleMaterials = new ManuscriptMetadata(metsWithMultipleMaterials);
        List<String> materials = getMetaDataListValuesWithLabelFromManuscripts(mdWithMultipleMaterials, "Beschreibstoff");
        assertEquals(2, materials.size());
        assertEquals("Pergament", materials.get(0));
        assertEquals("Papier", materials.get(1));
    }

    @Test
    void testGetVd16Vd17Vd18() {
        final String sourceVd16 = GetStandardMetadataTest.class.getResource("/mets/BntItin_021340072.xml").getPath();
        MetsData metsVd16 = getMets(sourceVd16);
        StandardMetadata mdVd16 = new StandardMetadata(metsVd16);
        List<String> vd16 = getMetaDataAtomicValuesWithLabel(mdVd16, "VD16");
        assertEquals(1, vd16.size());
        assertEquals("VD16 ZV 2668", vd16.get(0));

        final String sourceVd17 = GetStandardMetadataTest.class.getResource("/mets/ProMSiG_1800085370.xml").getPath();
        MetsData metsVd17 = getMets(sourceVd17);
        StandardMetadata mdVd17 = new StandardMetadata(metsVd17);
        List<String> vd17 = getMetaDataAtomicValuesWithLabel(mdVd17, "VD17");
        assertEquals(1, vd17.size());
        assertEquals("VD17 3086:753621E", vd17.get(0));

        final String sourceVd18 = GetStandardMetadataTest.class.getResource("/mets/AdAmEtC_1107922216.xml").getPath();
        MetsData metsVd18 = getMets(sourceVd18);
        StandardMetadata mdVd18 = new StandardMetadata(metsVd18);
        List<String> vd18 = getMetaDataAtomicValuesWithLabel(mdVd18, "VD18");
        assertEquals(1, vd18.size());
        assertEquals("VD18 14080044", vd18.get(0));
    }

    @Test
    void testIdentifiers() {
        final String sourceIdentifiers = GetStandardMetadataTest.class.getResource("/mets/AdAmEtC_1107922216.xml").getPath();
        MetsData metsIdentifiers = getMets(sourceIdentifiers);
        StandardMetadata mdIdentifiers = new StandardMetadata(metsIdentifiers);
        List<String> k10plus = getMetaDataAtomicValuesWithLabel(mdIdentifiers, "Quelle (K10Plus)");
        assertEquals(1, k10plus.size());
        assertEquals("1107922216", k10plus.get(0));

        final String sourcePartOfMultipart = GetStandardMetadataTest.class.getResource("/mets/multivolume_part_k3.xml").getPath();
        MetsData metsPartOfMultipart = getMets(sourcePartOfMultipart);
        StandardMetadata mdPartOfMultipart = new StandardMetadata(metsPartOfMultipart);
        List<String> k10plusVolume = getMetaDataAtomicValuesWithLabel(mdPartOfMultipart, "Quelle (K10Plus)");
        assertEquals(1, k10plusVolume.size());
        assertEquals("536169683", k10plusVolume.get(0));
    }
}
