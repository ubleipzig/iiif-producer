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

import static org.ubl.iiifproducer.doc.MetsData.Logical;
import static org.ubl.iiifproducer.doc.MetsData.Xlink;
import static org.xmlbeam.XBProjector.Flags.TO_STRING_RENDERS_XML;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.xmlbeam.XBProjector;

/**
 * MetsManifestBuilder.
 *
 * @author christopher-johnson
 */
public class MetsManifestBuilder {
    private MetsManifestBuilder() {
    }

    /**
     * getMetsFromFile.
     *
     * @param url String
     * @return XBProjector
     * @throws IOException Exception
     */
    static MetsData getMetsFromFile(final String url) throws IOException {
        final XBProjector projector = new XBProjector(TO_STRING_RENDERS_XML);
        return projector.io().file(url).read(MetsData.class);
    }

    static MetsData getMetsFromFile(final File url) throws IOException {
        final XBProjector projector = new XBProjector(TO_STRING_RENDERS_XML);
        return projector.io().file(url).read(MetsData.class);
    }

    public static String getManifestTitle(final MetsData mets) {
        return mets.getManifestTitle();
    }

    static String getSubtitle(final MetsData mets) {
        return mets.getSubtitle().trim();
    }

    public static String getAttribution(final MetsData mets) {
        return mets.getAttribution().trim();
    }

    public static String getLogo(final MetsData mets) {
        return mets.getLogo().trim();
    }

    public static String getManuscriptType(final MetsData mets) {
        return mets.getManuscriptType();
    }

    public static String getManuscriptIdByType(final MetsData mets, String idType) {
        return mets.getManuscriptIdByType(idType);
    }

    public static String getMedium(final MetsData mets) {
        return mets.getMedium().orElse("").trim();
    }

    public static String getMaterial(final MetsData mets) {
        return mets.getMaterial().trim();
    }

    public static String getExtent(final MetsData mets) {
        return mets.getExtent().trim();
    }

    public static String getDimension(final MetsData mets) {
        return mets.getDimension().trim();
    }

    public static String getLanguage(final MetsData mets) {
        return mets.getLanguage().trim();
    }

    public static String getLocation(final MetsData mets) {
        return mets.getLocation().orElse("").trim();
    }

    public static String getRecordIdentifier(final MetsData mets) {
        return mets.getRecordIdentifier().trim();
    }

    public static String getDateCreated(final MetsData mets) {
        return mets.getDateCreated().trim();
    }

    public static List<String> getNoteTypes(final MetsData mets) {
        return mets.getNoteTypes();
    }

    public static String getNotesByType(final MetsData mets, String type) {
        return mets.getNotesByType(type);
    }

    public static String getCensus(final MetsData mets) {
        return mets.getCensus().trim();
    }

    public static String getCollection(final MetsData mets) {
        return mets.getCollection().trim();
    }

    public static String getCallNumber(final MetsData mets) {
        return mets.getCallNumber().trim();
    }

    public static String getOwner(final MetsData mets) {
        return mets.getOwner().trim();
    }

    public static String getAuthor(final MetsData mets) {
        return mets.getAuthor().trim();
    }

    public static String getPlace(final MetsData mets) {
        return mets.getPlace().trim();
    }

    public static String getDate(final MetsData mets) {
        return mets.getDate().trim();
    }

    public static String getPublisher(final MetsData mets) {
        return mets.getPublisher().trim();
    }

    public static String getPhysState(final MetsData mets) {
        return mets.getPhysState().trim();
    }

    public static String getNote(final MetsData mets) {
        return mets.getNote().trim();
    }

    public static List<String> getPhysicalDivs(final MetsData mets) {
        return mets.getPhysicalDivs();
    }

    public static List<String> getFileResources(final MetsData mets) {
        return mets.getFileResources();
    }

    public static String getOrderLabelForDiv(final MetsData mets, String div) {
        return mets.getOrderLabelForDiv(div);
    }

    public static String getFileIdForDiv(final MetsData mets, String div) {
        return mets.getFileIdForDiv(div);
    }

    public static String getHrefForFile(final MetsData mets, String file) {
        return mets.getHrefForFile(file);
    }

    public static String getMimeTypeForFile(final MetsData mets, String file) {
        return mets.getMimeTypeForFile(file);
    }

    public static Logical getLogicalLastDescendent(final MetsData mets, String id) {
        return mets.getLogicalLastDescendent(id);
    }

    public static List<Logical> getLogicalLastParent(final MetsData mets, String id) {
        return mets.getLogicalLastParent(id);
    }

    public static List<Logical> getLogicalLastChildren(final MetsData mets, String id) {
        return mets.getLogicalLastChildren(id);
    }

    public static String getLogicalLabel(final MetsData mets, String id) {
        return mets.getLogicalLabel(id);
    }

    public static List<Xlink> getXlinks(final MetsData mets) {
        return mets.getXlinks();
    }
}
