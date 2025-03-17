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
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static de.ubleipzig.iiifproducer.doc.MetsConstants.HANDSHRIFT_TYPE;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.*;

/**
 * ManuscriptMetadata.
 *
 * @author christopher-johnson
 */
@Slf4j
public class ManuscriptMetadata {

    private MetsData mets;

    /**
     * @param mets MetsData
     */
    public ManuscriptMetadata(final MetsData mets) {
        this.mets = mets;
    }

    /**
     * @return List
     */
    public List<Metadata> getInfo() {

        final List<Metadata> meta = new ArrayList<>();
        // LabelObject.multiLingual("en", "", "de", "")
        meta.add(Metadata.builder().label(LabelObject.multiLingual("en", "Title (from shelfmark)", "de", "Titel (aus Signatur)")).value(getManifestTitle(mets)).build());
        meta.add(Metadata.builder().label(LabelObject.multiLingual("en", "Title", "de", "Objekttitel")).value(getSubtitle(mets)).build());
        meta.add(Metadata.builder().label(LabelObject.multiLingual("en", "Media type", "de", "Medium")).value(HANDSHRIFT_TYPE).build());
        meta.add(Metadata.builder().label(LabelObject.multiLingual("en", "Material", "de", "Beschreibstoff")).value(getMaterials(mets)).build()); // TODO check mehrwertig
        meta.add(Metadata.builder().label(LabelObject.multiLingual("en", "Extent", "de", "Umfang")).value(getExtent(mets)).build());
        meta.add(Metadata.builder().label(LabelObject.multiLingual("en", "Dimensions", "de", "Abmessungen")).value(getDimension(mets)).build());
        meta.add(Metadata.builder().label(LabelObject.multiLingual("en", "Date of origin", "de", "Datierung")).value(getDatesCreated(mets)).build());
        meta.add(Metadata.builder().label(LabelObject.multiLingual("en", "Place of origin", "de", "Lokalisierung")).value(getManufactureLocations(mets)).build());
        meta.add(Metadata.builder().label(LabelObject.multiLingual("en", "Language", "de", "Sprache")).value(getLanguageTexts(mets)).build());
        meta.add(Metadata.builder().label(LabelObject.multiLingual("en", "Source", "de", "Quelle der Angaben zur Handschrift")).value(getRecordIdentifier(mets)).build());
        // TODO was ist mit HSP?
        meta.add(Metadata.builder().label("Informationen zur Handschrift").value(getHspKodIdentifier(mets)).build());
        meta.add(Metadata.builder().label(LabelObject.multiLingual("en", "Shelfmark", "de", "Signatur")).value(getManuscriptIdByType(mets, "shelfmark")).build());
        meta.add(Metadata.builder().label(LabelObject.multiLingual("en", "Owner of physical object", "de", "Besitzer des Originals")).value(getOwnerOfOriginal(mets)).build());
        meta.add(Metadata.builder().label(LabelObject.multiLingual("en", "Owner of digital copy", "de", "Besitzer des Digitalisats")).value(getOwnerOfDigitalCopy(mets)).build());
        List<String> collections = getCollections(mets);
        for (String collection: collections) {
            meta.add(Metadata.builder().label(LabelObject.multiLingual("en", "Collection", "de", "Kollektion")).value(collection).build());
        }
        meta.add(Metadata.builder().label("Manifest Type").value(
                getLogicalType(mets, mets.getRootLogicalStructureId().orElse(MetsConstants.METS_PARENT_LOGICAL_ID))
        ).build());
        meta.add(Metadata.builder().label(LabelObject.multiLingual("en", "Identifier Kitodo", "de", "Kitodo-ID")).value(getManuscriptIdByType(mets, "kitodo")).build());
        meta.add(Metadata.builder().label("URN").value(getManuscriptIdByType(mets, "urn")).build());
        // TODO Bemerkung / Note
        log.debug("Manuscript metadata Added");
        return meta.stream()
                .filter(Objects::nonNull)
                .filter(v -> v.getValue() != null && (
                        (v.getValue() instanceof String && !((String) v.getValue()).isEmpty()) ||
                                (v.getValue() instanceof List && !((List)v.getValue()).isEmpty())
                ))
                .collect(Collectors.toList());
    }
}
