
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
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Lutz Helm, helm@ub.uni-leipzig.de
 */
public class HspCatalogStructureMetadataTest {

    static Metadata getMetadataByLabel(final List<Metadata> info, final String label) {
        final List<Metadata> forLabel = info.stream()
                .filter(md -> md.getLabel() != null && md.getLabel().equals(label)).collect(Collectors.toList());
        return forLabel == null || forLabel.isEmpty() ? null : forLabel.get(0);
    }

    @Test
    void testGetHspMetadata() {
        final String sourceFile
                = Objects.requireNonNull(
                        HspCatalogStructureMetadataTest.class.getResource("/mets/DieHadeT_1525437259.xml")).getPath();
        final MetsData mets = getMets(sourceFile);
        final HspCatalogStructureMetadata metadata = new HspCatalogStructureMetadata(mets, "LOG_0024");
        final List<Metadata> info = metadata.getInfo();
        assertNotNull(info);

        assertEquals("Ms. El. f. 91", getMetadataByLabel(info, "Signatur").getValue());
        assertEquals("Nicolaus Oresmius (Aristoteles; Ps.-Aristoteles)",
                getMetadataByLabel(info, "Titel").getValue());
        assertEquals("Pergament", getMetadataByLabel(info, "Beschreibstoff").getValue());
        assertEquals("358", getMetadataByLabel(info, "Umfang").getValue());
        assertEquals("34 x 24,5", getMetadataByLabel(info, "Abmessungen").getValue());
        assertEquals("Nordfrankreich (Text, Buchschmuck), Paris (?) (Text, Buchschmuck), Brügge (Miniaturen)",
                getMetadataByLabel(info, "Entstehungsort").getValue());
        assertEquals("um 1430/50\n" +
                "               (Text, Buchschmuck), um 1470/80 (Miniaturen)",
                getMetadataByLabel(info, "Entstehungszeit").getValue());
    }
}
