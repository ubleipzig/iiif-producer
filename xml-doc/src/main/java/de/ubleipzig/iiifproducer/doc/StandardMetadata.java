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
import static de.ubleipzig.iiifproducer.doc.MetsConstants.METS_PARENT_LOGICAL_ID;
import static de.ubleipzig.iiifproducer.doc.MetsConstants.SWB_TYPE;
import static de.ubleipzig.iiifproducer.doc.MetsConstants.URN_TYPE;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getAddressee;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getAuthor;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getCallNumber;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getCallNumbers;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getCollection;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getDate;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getDates;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getKalliopeID;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getLogicalType;
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
        //this is ugly, but this is the way that collections are tagged in the XML
        final String vd16 = getManuscriptIdByType(mets, "vd16");
        final String vd17 = getManuscriptIdByType(mets, "vd17");
        if (!vd16.equals("")) {
            meta.add(new TemplateMetadata("VD16", vd16));
            meta.add(new TemplateMetadata("Collection", "VD16"));
        }
        if (!vd17.equals("")) {
            meta.add(new TemplateMetadata("VD17", vd17));
            meta.add(new TemplateMetadata("Collection", "VD17"));
        }
        meta.add(new TemplateMetadata("Source PPN (SWB)", getManuscriptIdByType(mets, SWB_TYPE)));
        meta.add(new TemplateMetadata("Collection", getCollection(mets)));
        if (getCollection(mets).contains("TestCollection") ^ getCollection(mets).contains("Heisenberg")) {
            meta.add(new TemplateMetadata("Call number", getCallNumbers(mets)));
            meta.add(new TemplateMetadata("Date of publication", getDates(mets)));
            meta.add(new TemplateMetadata("Kalliope-ID", getKalliopeID(mets)));
        } else {
            // TODO no call number for catalog
            meta.add(new TemplateMetadata("Call number", getCallNumber(mets)));
            meta.add(new TemplateMetadata("Date of publication", getDate(mets)));
        }
        meta.add(new TemplateMetadata("Owner", getOwner(mets)));
        meta.add(new TemplateMetadata("Author", getAuthor(mets)));
        meta.add(new TemplateMetadata("Addressee", getAddressee(mets)));
        meta.add(new TemplateMetadata("Place of publication", getPlace(mets)));
        meta.add(new TemplateMetadata("Publisher", getPublisher(mets)));
        meta.add(new TemplateMetadata("Physical description", getPhysState(mets)));
        meta.add(new TemplateMetadata("Manifest Type", getLogicalType(mets, METS_PARENT_LOGICAL_ID)));

        logger.debug("Standard Metadata Added");
        return meta;
    }
}