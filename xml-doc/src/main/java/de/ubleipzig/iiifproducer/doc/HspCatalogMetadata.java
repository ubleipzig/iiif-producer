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

import de.ubleipzig.iiifproducer.template.TemplateMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static de.ubleipzig.iiifproducer.doc.MetsConstants.URN_TYPE;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.*;

/**
 * HspCatalogMetadata.
 *
 * @author Lutz Helm, helm@ub.uni-leipzig.de
 */
public class HspCatalogMetadata {

    static final Logger logger = LoggerFactory.getLogger(HspCatalogMetadata.class);

    MetsData mets;

    /**
     * @param mets String
     */
    public HspCatalogMetadata(final MetsData mets) {
        this.mets = mets;
    }

    /**
     * Get metadata for HSP catalog manifests.
     * @return String
     */
    public List<TemplateMetadata> getInfo() {
        final List<TemplateMetadata> metadata = new ArrayList<>();
        metadata.add(new TemplateMetadata("URN", getManuscriptIdByType(mets, URN_TYPE)));
        metadata.add(new TemplateMetadata("Autor:in", getAuthor(mets)));
        metadata.add(new TemplateMetadata("Erscheinungsort", getPlace(mets)));
        metadata.add(new TemplateMetadata("Verlag", getPublisher(mets)));
        metadata.add(new TemplateMetadata("Erscheinungsjahr", getDate(mets)));
        metadata.add(new TemplateMetadata("Umfang", getPhysState(mets)));
        logger.debug("Added HSP catalog metadata.");
        return metadata;
    }
}
