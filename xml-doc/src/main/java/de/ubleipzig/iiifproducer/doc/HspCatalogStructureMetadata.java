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

import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getCallNumber;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getSubtitle;

import de.ubleipzig.iiifproducer.template.TemplateMetadata;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HspCatalogStructureMetadata. Used for descriptive metadata of manuscript catalog entries.
 *
 * @author Lutz Helm, helm@ub.uni-leipzig.de
 */
public class HspCatalogStructureMetadata {

    private static Logger logger = LoggerFactory.getLogger(HspCatalogStructureMetadata.class);

    private MetsData mets;

    private String dmdLogId;

    /**
     * @param mets MetsData
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
    public List<TemplateMetadata> getInfo() {
        final MetsData.HspCatalogMods mods = mets.getDmdMods(this.dmdLogId).orElse(null);

        final List<TemplateMetadata> meta = new ArrayList<>();
        meta.add(new TemplateMetadata("Signatur", getCallNumber(mods)));
        meta.add(new TemplateMetadata("Titel", getSubtitle(mods)));
        meta.add(new TemplateMetadata("Beschreibstoff", joinValues(mods.getMaterial())));
        meta.add(new TemplateMetadata("Umfang", joinValues(mods.getExtent())));
        meta.add(new TemplateMetadata("Abmessungen", joinValues(mods.getDimensions())));
        meta.add(new TemplateMetadata("Entstehungsort", joinValues(mods.getOriginPlace())));
        meta.add(new TemplateMetadata("Enstehungszeit", joinValues(mods.getOriginDate())));
        logger.debug("HSP catalog entry metadata added");
        return meta;
    }
}
