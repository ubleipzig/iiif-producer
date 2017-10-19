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
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getDateCreated;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getDimension;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getExtent;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getLanguage;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getLocation;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getManifestTitle;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getManuscriptIdByType;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getMaterial;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getMedium;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getRecordIdentifier;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getSubtitle;

import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.ubl.iiifproducer.template.TemplateMetadata;

/**
 * ManuscriptMetadata.
 *
 * @author christopher-johnson
 */
public class ManuscriptMetadata {

    private static Logger logger = getLogger(ManuscriptMetadata.class);

    private MetsData mets;

    public ManuscriptMetadata(MetsData mets) {
        this.mets = mets;
    }

    public ArrayList<TemplateMetadata> getInfo() {

        ArrayList<TemplateMetadata> meta = new ArrayList<>();
        meta.add(new TemplateMetadata("Titel (aus Signatur)", getManifestTitle(mets)));
        meta.add(new TemplateMetadata("Objekttitel", getSubtitle(mets)));
        meta.add(new TemplateMetadata("Medium", getMedium(mets).orElse(null)));
        meta.add(new TemplateMetadata("Beschreibstoff", getMaterial(mets)));
        meta.add(new TemplateMetadata("Umfang", getExtent(mets)));
        meta.add(new TemplateMetadata("Abmessungen", getDimension(mets)));
        meta.add(new TemplateMetadata("Sprache", getLanguage(mets)));
        meta.add(new TemplateMetadata("Lokalisierung", getLocation(mets)));
        meta.add(new TemplateMetadata("Manuscripta Mediaevalia", getRecordIdentifier(mets)));
        meta.add(new TemplateMetadata("Datierung", getDateCreated(mets)));
        meta.add(new TemplateMetadata("Kitodo", getManuscriptIdByType(mets, "goobi").orElse(null)));
        meta.add(new TemplateMetadata("URN", getManuscriptIdByType(mets, "urn").orElse(null)));
        meta.add(new TemplateMetadata(
                "Signatur",
                getManuscriptIdByType(mets, "shelfmark").orElse(null)));

        return meta;
    }
}