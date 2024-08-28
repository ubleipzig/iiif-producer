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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Objects;

public class MetsManifestBuilderTest {
    @Test
    void testGetVolumePartTitleOrPartNumber() {
        String fileK2 = Objects.requireNonNull(
                GetValuesFromMetsTest.class.getResource("/mets/multivolume_part_k1-k2.xml")).getPath();
        MetsData metsK2 = MetsManifestBuilder.getMetsFromFile(fileK2);

        assertEquals("Quod continent Iuris constituti singula materiarum genera declarationem",
                MetsManifestBuilder.getVolumePartTitleOrPartNumber(metsK2));


        String fileK3 = Objects.requireNonNull(
                GetValuesFromMetsTest.class.getResource("/mets/multivolume_part_k3.xml")).getPath();
        MetsData metsK3 = MetsManifestBuilder.getMetsFromFile(fileK3);
        assertEquals("Exhibens Epistolas Apostolicas Universas, Et Apocalypsin Johanneam",
                MetsManifestBuilder.getVolumePartTitleOrPartNumber(metsK3));
    }

    @Test
    void testGetRightsUrl() {
        String fileWithWrongHref = Objects.requireNonNull(GetValuesFromMetsTest.class.getResource("/mets/ProMSiG_1800085370.xml")).getPath();
        MetsData metsWithWrongHref = MetsManifestBuilder.getMetsFromFile(fileWithWrongHref);
        List<String> urisFromWrongHref = MetsManifestBuilder.getRightsUrl(metsWithWrongHref);
        assertEquals(2, urisFromWrongHref.size());
        assertTrue(urisFromWrongHref.contains("http://creativecommons.org/publicdomain/mark/1.0/"));
        assertTrue(urisFromWrongHref.contains("http://purl.org/coar/access_right/c_abf2"));

        String fileWithXlinkHref = Objects.requireNonNull(GetValuesFromMetsTest.class.getResource("/mets/AktezuGed_1121300006.xml")).getPath();
        MetsData metsWithXlinkHref = MetsManifestBuilder.getMetsFromFile(fileWithXlinkHref);
        List<String> urisFromXlinkHref = MetsManifestBuilder.getRightsUrl(metsWithXlinkHref);
        assertEquals(2, urisFromXlinkHref.size());
        assertTrue(urisFromXlinkHref.contains("http://creativecommons.org/publicdomain/mark/1.0/"));
        assertTrue(urisFromXlinkHref.contains("http://purl.org/coar/access_right/c_abf2"));
    }
}
