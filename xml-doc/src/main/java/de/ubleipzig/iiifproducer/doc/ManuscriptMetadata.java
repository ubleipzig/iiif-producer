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

import java.util.ArrayList;
import java.util.List;

import static de.ubleipzig.iiifproducer.doc.MetsConstants.HANDSHRIFT_TYPE;
import static de.ubleipzig.iiifproducer.doc.MetsConstants.METS_PARENT_LOGICAL_ID;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.*;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * ManuscriptMetadata.
 *
 * @author christopher-johnson
 */
public class ManuscriptMetadata {

    private static Logger logger = getLogger(ManuscriptMetadata.class);

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
    public List<TemplateMetadata> getInfo() {

        final List<TemplateMetadata> meta = new ArrayList<>();
        meta.add(new TemplateMetadata("Titel (aus Signatur)", getManifestTitle(mets)));
        meta.add(new TemplateMetadata("Objekttitel", getSubtitle(mets)));
        meta.add(new TemplateMetadata("Collection", getCollection(mets)));
        meta.add(new TemplateMetadata("Medium", HANDSHRIFT_TYPE));
        meta.add(new TemplateMetadata("Beschreibstoff", getMaterial(mets)));
        meta.add(new TemplateMetadata("Umfang", getExtent(mets)));
        meta.add(new TemplateMetadata("Abmessungen", getDimension(mets)));
        meta.add(new TemplateMetadata("Sprache", getLanguageDescription(mets)));
        meta.add(new TemplateMetadata("Lokalisierung", getLocation(mets)));
        meta.add(new TemplateMetadata("Manuscripta Mediaevalia", getRecordIdentifier(mets)));
        meta.add(new TemplateMetadata("Datierung", getDateCreated(mets)));
        meta.add(new TemplateMetadata("Kitodo", getManuscriptIdByType(mets, "goobi")));
        meta.add(new TemplateMetadata("URN", getManuscriptIdByType(mets, "urn")));
        meta.add(new TemplateMetadata("Signatur", getManuscriptIdByType(mets, "shelfmark")));
        meta.add(new TemplateMetadata("Manifest Type", getLogicalType(mets, METS_PARENT_LOGICAL_ID)));
        logger.debug("Manuscript metadata Added");
        return meta;
    }
}
