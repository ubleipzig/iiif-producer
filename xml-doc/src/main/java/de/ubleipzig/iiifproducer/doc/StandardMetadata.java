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

import static de.ubleipzig.iiifproducer.doc.MetsConstants.GOOBI_TYPE;
import static de.ubleipzig.iiifproducer.doc.MetsConstants.SWB_TYPE;
import static de.ubleipzig.iiifproducer.doc.MetsConstants.URN_TYPE;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getAuthor;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getCallNumber;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getCollection;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getDate;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getManuscriptIdByType;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getOwner;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getPhysState;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getPlace;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getPublisher;
import static org.slf4j.LoggerFactory.getLogger;

import de.ubleipzig.iiifproducer.template.TemplateMetadata;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

/**
 * StandardMetadata.
 *
 * @author christopher-johnson
 */
public class StandardMetadata {

    private static Logger logger = getLogger(StandardMetadata.class);

    private MetsData mets;

    /**
     * @param mets MetsData
     */
    public StandardMetadata(final MetsData mets) {
        this.mets = mets;
    }

    /**
     * @return List
     */
    public List<TemplateMetadata> getInfo() {
        final List<TemplateMetadata> meta = new ArrayList<>();
        meta.add(new TemplateMetadata("Kitodo", getManuscriptIdByType(mets, GOOBI_TYPE)));
        meta.add(new TemplateMetadata("URN", getManuscriptIdByType(mets, URN_TYPE)));
        meta.add(new TemplateMetadata("Source PPN (SWB)", getManuscriptIdByType(mets, SWB_TYPE)));
        meta.add(new TemplateMetadata("Collection", getCollection(mets)));
        meta.add(new TemplateMetadata("Call number", getCallNumber(mets)));
        meta.add(new TemplateMetadata("Owner", getOwner(mets)));
        meta.add(new TemplateMetadata("Author", getAuthor(mets)));
        meta.add(new TemplateMetadata("Place of publication", getPlace(mets)));
        meta.add(new TemplateMetadata("Date of publication", getDate(mets)));
        meta.add(new TemplateMetadata("Publisher", getPublisher(mets)));
        meta.add(new TemplateMetadata("Physical description", getPhysState(mets)));
        logger.debug("Standard Metadata Added");
        return meta;
    }
}