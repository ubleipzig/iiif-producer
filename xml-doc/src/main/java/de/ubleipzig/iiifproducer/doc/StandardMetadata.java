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

import static de.ubleipzig.iiifproducer.doc.MetsConstants.*;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.*;

/**
 * StandardMetadata.
 *
 * @author christopher-johnson
 */
@Slf4j
public class StandardMetadata {

    private final MetsData mets;

    /**
     * @param mets MetsData
     */
    public StandardMetadata(final MetsData mets) {
        this.mets = mets;
    }

    /**
     * @return List
     */
    public List<Metadata> getInfo() {
        // N.B. that additional metadata is added in de.ubleipzig.iiifproducer.producer.MetsImpl.setMetadata
        final List<Metadata> meta = new ArrayList<>();
        meta.add(Metadata.builder().label(LabelObject.multiLingual("en", "Author", "de", "Person / Körperschaft")).value(getAuthor(mets)).build());
        meta.add(Metadata.builder().label(LabelObject.multiLingual("en", "Addressee", "de", "Adressierte Person")).value(getAddressee(mets)).build());
        meta.add(Metadata.builder().label(LabelObject.multiLingual("en", "Date of publication", "de", "Erscheinungsjahr")).value(getDate(mets)).build());
        meta.add(Metadata.builder().label(LabelObject.multiLingual("en", "Place of publication", "de", "Erscheinungsort")).value(getPlaces(mets)).build());
        meta.add(Metadata.builder().label(LabelObject.multiLingual("en", "Publisher", "de", "Verlag")).value(getPublisher(mets)).build());
        meta.add(Metadata.builder().label(LabelObject.multiLingual("en", "Physical description", "de", "Umfang")).value(getPhysState(mets)).build());
        final String vd16 = getManuscriptIdByType(mets, "vd16");
        final String vd17 = getManuscriptIdByType(mets, "vd17");
        final String vd18 = getManuscriptIdByType(mets, "vd18");
        if (!vd16.isBlank()) {
            meta.add(Metadata.builder().label("VD16").value(vd16).build());
        }
        if (!vd17.isBlank()) {
            meta.add(Metadata.builder().label("VD17").value(vd17).build());
        }
        if (!vd18.isBlank()) {
            meta.add(Metadata.builder().label("VD18").value(vd18).build());
        }
        meta.add(Metadata.builder().label("URN").value(getManuscriptIdByType(mets, URN_TYPE)).build());
        meta.add(Metadata.builder().label(LabelObject.multiLingual("en", "Call number", "de", "Signatur")).value(getCallNumber(mets)).build());
        meta.add(Metadata.builder().label(LabelObject.multiLingual("en", "Source (K10Plus)", "de", "Quelle (K10Plus)")).value(getRecordIdentifierByAttribute(mets, "source", "https://digital.ub.uni-leipzig.de/ppn/")).build());
        meta.add(Metadata.builder().label(LabelObject.multiLingual("en", "Source (SWB)", "de", "Quelle (SWB)")).value(getManuscriptIdByType(mets, SWB_TYPE)).build());
        boolean isProjectHeisenberg = getCollections(mets).stream().filter(col -> col.contains("Heisenberg")).collect(Collectors.toList()).size() > 0;
        if (isProjectHeisenberg) {
            meta.add(Metadata.builder().label(LabelObject.multiLingual("en", "Source (Kalliope ID)", "de", "Quelle (Kalliope-ID)")).value(getKalliopeID(mets)).build());
        }
        List<String> collections = getCollections(mets);
        for (String collection : collections) {
            if (!collection.contains("VD16") || !collection.contains("VD17")) {
                meta.add(Metadata.builder().label(LabelObject.multiLingual("en", "Collection", "de", "Kollektion")).value(collection).build());
            }
        }
        meta.add(Metadata.builder().label(LabelObject.multiLingual("en", "Owner of digital copy", "de", "Besitznachweis der Reproduktion")).value(getOwnerOfDigitalCopy(mets)).build());
        meta.add(Metadata.builder().label(LabelObject.multiLingual("en", "Owner of original", "de", "Besitznachweis des reproduzierten Exemplars")).value(getOwnerOfOriginal(mets)).build());
//        meta.add(Metadata.builder().label("Kitodo").value(getManuscriptIdByType(mets, GOOBI_TYPE)).build());
//        meta.add(Metadata.builder().label("Manifest Type").value(
//                getLogicalType(mets, mets.getRootLogicalStructureId().orElse(MetsConstants.METS_PARENT_LOGICAL_ID))).build()
//        );
        log.debug("Standard Metadata Added");
        meta.stream().forEach(m -> System.err.println(m.getLabel() + ": '" + m.getValue() + "'"));
        return meta.stream()
                .filter(Objects::nonNull)
                .filter(v -> v.getValue() != null && (
                        (v.getValue() instanceof String && !((String) v.getValue()).isEmpty()) ||
                                (v.getValue() instanceof List && !((List)v.getValue()).isEmpty())
                ))
                .collect(Collectors.toList());
    }
}
