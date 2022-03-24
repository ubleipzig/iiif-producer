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
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getCallNumber;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getSubtitle;

/**
 * HspCatalogStructureMetadata. Used for descriptive metadata of manuscript catalog entries.
 *
 * @author Lutz Helm, helm@ub.uni-leipzig.de
 */
@Slf4j
public class HspCatalogStructureMetadata {

    private final MetsData mets;

    private final String dmdLogId;

    /**
     * @param mets         MetsData
     * @param logicalDivId logicalDivId
     */
    public HspCatalogStructureMetadata(final MetsData mets, final String logicalDivId) {
        this.mets = mets;
        this.dmdLogId = mets.getLogicalDmdId(logicalDivId).orElse(null);
    }

    private String joinValues(final List<String> values) {
        return values == null || values.isEmpty() ? null : String.join(", ", values);
    }

    /**
     * @return List
     */
    public List<Metadata> getInfo() {
        final MetsData.HspCatalogMods mods = mets.getDmdMods(this.dmdLogId).orElse(null);
        if (mods != null) {
            final List<Metadata> meta = new ArrayList<>();
            meta.add(Metadata.builder().label("Signatur").value(getCallNumber(mods)).build());
            meta.add(Metadata.builder().label("Titel").value(getSubtitle(mods)).build());
            meta.add(Metadata.builder().label("Beschreibstoff").value(joinValues(mods.getMaterial())).build());
            meta.add(Metadata.builder().label("Umfang").value(joinValues(mods.getExtent())).build());
            meta.add(Metadata.builder().label("Abmessungen").value(joinValues(mods.getDimensions())).build());
            meta.add(Metadata.builder().label("Entstehungsort").value(joinValues(mods.getOriginPlace())).build());
            meta.add(Metadata.builder().label("Entstehungszeit").value(joinValues(mods.getOriginDate())).build());
            log.debug("HSP catalog entry metadata added");
            return meta.stream()
                    .filter(Objects::nonNull)
                    .filter(v -> v.getValue() instanceof String && !((String) v.getValue()).isEmpty())
                    .collect(Collectors.toList());
        }
        return null;
    }
}
