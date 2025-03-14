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

import org.slf4j.Logger;
import org.xmlbeam.XBProjector;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;
import static org.xmlbeam.XBProjector.Flags.TO_STRING_RENDERS_XML;

/**
 * MetsManifestBuilder.
 *
 * @author christopher-johnson
 */
public final class MetsManifestBuilder {

    private static Logger logger = getLogger(MetsManifestBuilder.class);

    private MetsManifestBuilder() {
    }

    /**
     * getMetsFromFile.
     *
     * @param url String
     * @return XBProjector
     */
    static MetsData getMetsFromFile(final String url) {
        try {
            final XBProjector projector = new XBProjector(TO_STRING_RENDERS_XML);
            return projector.io().file(url).read(MetsData.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot Read XML: " + e.getMessage());
        }
    }

    /**
     * @param url File
     * @return MetsData
     */
    static MetsData getMetsFromFile(final File url) {
        try {
            final XBProjector projector = new XBProjector(TO_STRING_RENDERS_XML);
            return projector.io().file(url).read(MetsData.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot Read XML: " + e.getMessage());
        }
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static List<String> getManifestTitles(final MetsData mets) {
        return mets.getManifestTitles();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getManifestTitle(final MetsData mets) {
        return mets.getManifestTitle().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getMultiVolumeWorkTitle(final MetsData mets) {
        return mets.getMultiVolumeWorkTitle().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getVolumePartTitleOrPartNumber(final MetsData mets) {
        String volumeDmdId = mets.getVolumePartDmdId().orElse(null);
        if (volumeDmdId != null) {
            return mets.getVolumePartTitle(volumeDmdId).orElse(getCensusHost(mets)).trim();
        }
        return getCensusHost(mets);
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getUrnReference(final MetsData mets) {
        return mets.getManuscriptIdByType("urn").orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    static String getSubtitle(final MetsData mets) {
        return mets.getSubtitle().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static List<String> getRightsUrl(final MetsData mets) {
        return mets.getRightsUrl();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static List<String> getRightsValue(final MetsData mets) {
        return mets.getRightsValue();
    }


    /**
     * @param mets MetsData
     * @return List
     */
    public static List<String> getCopyrightHolders(final MetsData mets) {
        return mets.getCopyrightHolders();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getAttribution(final MetsData mets) {
        return mets.getAttribution().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getLogo(final MetsData mets) {
        return mets.getLogo().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return Boolean
     */
    public static Boolean isHspCatalog(final MetsData mets) {
        return mets.isHspCatalog();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static Boolean isManuscript(final MetsData mets) {
        return mets.isManuscript();
    }

    /**
     * @param mets   MetsData
     * @param idType String
     * @return String
     */
    public static String getManuscriptIdByType(final MetsData mets, final String idType) {
        return mets.getManuscriptIdByType(idType).orElse("").trim();
    }

    public static String getIdentifierByAttribute(final MetsData mets, final String attribute, final String value) {
        return mets.getIdentifierByAttribute(attribute, value).orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getMedium(final MetsData mets) {
        return mets.getMedium().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getMaterial(final MetsData mets) {
        return mets.getMaterial().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static List<String> getMaterials(final MetsData mets) {
        return mets.getMaterials();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getExtent(final MetsData mets) {
        return mets.getExtent().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getDimension(final MetsData mets) {
        return mets.getDimension().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getLanguageDescription(final MetsData mets) {
        return mets.getLanguageDescription().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getLocation(final MetsData mets) {
        return mets.getLocation().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getRecordIdentifier(final MetsData mets) {
        return mets.getRecordIdentifier().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getHspKodIdentifier(final MetsData mets) {
        return mets.getHspKodIdentifier().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getDateCreated(final MetsData mets) {
        return mets.getDateCreated().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return List
     */
    public static List<String> getNoteTypes(final MetsData mets) {
        return mets.getNoteTypes();
    }

    /**
     * @param mets MetsData
     * @param type String
     * @return String
     */
    public static String getNotesByType(final MetsData mets, final String type) {
        return mets.getNotesByType(type).orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getCensusHost(final MetsData mets) {
        return mets.getCensusHost().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getCollection(final MetsData mets) {
        return mets.getCollection().orElse("").trim();
    }

    public static List<String> getCollections(final MetsData mets) {
        return mets.getCollections().stream().map(col -> col.trim()).collect(Collectors.toList());
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static List<String> getKalliopeID(final MetsData mets) {
        return mets.getKalliopeID();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static List<String> getCallNumbers(final MetsData mets) {
        return mets.getCallNumbers();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getCallNumber(final MetsData mets) {
        return mets.getCallNumber().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getOwnerOfDigitalCopy(final MetsData mets) {
        return mets.getOwnerOfDigitalCopy().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getOwnerOfOriginal(final MetsData mets) {
        return mets.getOwnerOfOriginal().orElse("");
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getAuthor(final MetsData mets) {
        return mets.getAuthor().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getAddressee(final MetsData mets) {
        return mets.getAddressee().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static List<String> getPlaces(final MetsData mets) {
        return mets.getPlaces();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static List<String> getDates(final MetsData mets) {
        return mets.getDates();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getDate(final MetsData mets) {
        return mets.getDate().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getPublisher(final MetsData mets) {
        return mets.getPublisher().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getPhysState(final MetsData mets) {
        return mets.getPhysState().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getNote(final MetsData mets) {
        return mets.getNote().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return List
     */
    public static List<String> getPhysicalDivs(final MetsData mets) {
        return mets.getPhysicalDivs();
    }

    /**
     * @param mets MetsData
     * @param div  String
     * @return String
     */
    public static String getOrderLabelForDiv(final MetsData mets, final String div) {
        return mets.getOrderLabelForDiv(div).orElse("").trim();
    }

    /**
     * @param mets    MetsData
     * @param div     String
     * @param fileGrp String
     * @return String
     */
    public static String getFileIdForDiv(final MetsData mets, final String div, final String fileGrp) {
        final List<String> fileIds = mets.getFileIdForDiv(div);
        if (fileIds != null && !fileIds.isEmpty()) {
            for (String fileId : fileIds) {
                if (mets.getFileIdInFileGrp(fileId, fileGrp)) {
                    return fileId;
                }
            }
        }
        return "";
    }

    /**
     * @param mets MetsData
     * @param file String
     * @return String
     */
    public static String getHrefForFile(final MetsData mets, final String file) {
        return mets.getHrefForFile(file).orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @param file String
     * @return String
     */
    public static String getMimeTypeForFile(final MetsData mets, final String file) {
        return mets.getMimeTypeForFile(file).orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @param id   String
     * @return Logical
     */
    public static MetsData.Logical getLogicalLastDescendent(final MetsData mets, final String id) {
        return mets.getLogicalLastDescendent(id);
    }

    /**
     * @param mets MetsData
     * @param id   String
     * @return List
     */
    public static List<MetsData.Logical> getLogicalLastParent(final MetsData mets, final String id) {
        return mets.getLogicalLastParent(id);
    }

    /**
     * @param mets MetsData
     * @return List
     */
    public static List<MetsData.Logical> getTopLogicals(final MetsData mets) {
        return mets.getTopLogicals();
    }

    /**
     * @param mets MetsData
     * @param id   String
     * @return List
     */
    public static List<MetsData.Logical> getLogicalLastChildren(final MetsData mets, final String id) {
        return mets.getLogicalLastChildren(id);
    }

    /**
     * @param mets MetsData
     * @param id   String
     * @return String
     */
    public static String getLogicalLabel(final MetsData mets, final String id) {
        final ResourceBundle labels = ResourceBundle.getBundle("LabelsBundle", Locale.GERMAN);
        final String type = mets.getLogicalType(id).orElse("");
        final String label;
        if (labels.containsKey(type)) {
            final String translatedType = labels.getString(type);
            label = mets.getLogicalLabel(id).orElse(translatedType);
        } else {
            label = mets.getLogicalLabel(id).orElse("");
            logger.warn("Missing Resource Bundle Mapping for Key \"{}\"", type);
        }
        return label;
    }

    /**
     * @param mets MetsData
     * @param id   String
     * @return String
     */
    public static String getLogicalType(final MetsData mets, final String id) {
        return mets.getLogicalType(id).orElse("multivolume_work");
    }

    /**
     * @param mets MetsData
     * @return List
     */
    public static List<MetsData.Xlink> getXlinks(final MetsData mets) {
        return mets.getXlinks();
    }

    /**
     * @param mods MetaData.HspCatalogMods
     * @return String
     */
    public static String getSubtitle(final MetsData.HspCatalogMods mods) {
        return mods.getSubtitle().orElse("");
    }

    /**
     * @param mods MetaData.HspCatalogMods
     * @return String
     */
    public static String getCallNumber(final MetsData.HspCatalogMods mods) {
        return mods.getCallNumber().orElse("");
    }
}
