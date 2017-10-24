/*
 * IIIFProducer
 *
 * Copyright (C) 2017 Leipzig University Library <info@ub.uni-leipzig.de>
 *
 * @author Stefan Freitag <freitag@uni-leipzig.de>
 * @author Christopher Johnson <christopher_hanna.johnson@uni-leipzig.de>
 * @author Felix Kreißig <kreissig@ub.uni-leipzig.de>
 * @author Leander Seige <seige@ub.uni-leipzig.de>
 * @license http://opensource.org/licenses/gpl-2.0.php GNU GPLv2
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.ubl.iiifproducer.doc;

import static org.apache.log4j.Logger.getLogger;
import static org.ubl.iiifproducer.doc.MetsConstants.GOOBI_TYPE;
import static org.ubl.iiifproducer.doc.MetsConstants.SWB_TYPE;
import static org.ubl.iiifproducer.doc.MetsConstants.URN_TYPE;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getAuthor;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getCallNumber;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getCollection;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getDate;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getManuscriptIdByType;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getOwner;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getPhysState;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getPlace;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getPublisher;

import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.ubl.iiifproducer.template.TemplateMetadata;

/**
 * StandardMetadata.
 *
 * @author christopher-johnson
 */
public class StandardMetadata {

    private static Logger logger = getLogger(StandardMetadata.class);

    private MetsData mets;

    public StandardMetadata(MetsData mets) {
        this.mets = mets;
    }

    public ArrayList<TemplateMetadata> getInfo() {
        ArrayList<TemplateMetadata> meta = new ArrayList<>();
        meta.add(new TemplateMetadata(
                "Kitodo",
                getManuscriptIdByType(mets, GOOBI_TYPE)));
        meta.add(new TemplateMetadata("URN", getManuscriptIdByType(mets, URN_TYPE)));
        meta.add(new TemplateMetadata(
                "Source PPN (SWB)",
                getManuscriptIdByType(mets, SWB_TYPE)));
        meta.add(new TemplateMetadata("Collection", getCollection(mets).trim()));
        meta.add(new TemplateMetadata("Call number", getCallNumber(mets).trim()));
        meta.add(new TemplateMetadata("Owner", getOwner(mets).trim()));
        meta.add(new TemplateMetadata("Author", getAuthor(mets).trim()));
        meta.add(new TemplateMetadata("Place of publication", getPlace(mets).trim()));
        meta.add(new TemplateMetadata("Date of publication", getDate(mets).trim()));
        meta.add(new TemplateMetadata("Publisher", getPublisher(mets).trim()));
        meta.add(new TemplateMetadata("Physical description", getPhysState(mets).trim()));
        return meta;
    }
}