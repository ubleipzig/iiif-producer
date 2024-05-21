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

import org.xmlbeam.annotation.XBRead;

import java.util.List;
import java.util.Optional;

/**
 * MetsData.
 *
 * @author christopher-johnson
 */
public interface MetsData {

    /**
     * @return String
     */
    @XBRead("//*[local-name()='mods']/*[local-name()='titleInfo']/*[local-name()='title']")
    List<String> getManifestTitles();

    /**
     * @return String
     */
    @XBRead("//*[local-name()='mods']/*[local-name()='titleInfo']/*[local-name()='title']")
    Optional<String> getManifestTitle();

    /**
     * @return String
     */
    @XBRead("//*[local-name()='structMap'][@TYPE='LOGICAL']/*[local-name()='div'][@TYPE='multivolume_work']/@LABEL")
    Optional<String> getMultiVolumeWorkTitle();

    /**
     * @return String
     */
    // FIXME This will no longer work with Kitodo.Production 3, "DMDLOG_0001" is replaced with a uuid
    @XBRead("//*[local-name()='dmdSec'][@ID='DMDLOG_0001']//*[local-name()='mods']/*[local-name()" +
            "='titleInfo']/*[local-name()='title']")
    Optional<String> getVolumePartTitle();

    /**
     * @return String
     */
    @XBRead("//*[local-name()='accessCondition']/@href")
    List<String> getRightsUrl();

    /**
     * @return String
     */
    @XBRead("//*[local-name()='accessCondition']")
    List<String> getRightsValue();

    @XBRead("//*[local-name()='roleTerm'][text()='cph']/parent::node()/parent::node()/*[local-name()='displayForm']")
    List<String> getCopyrightHolders();

    /**
     * @return String
     */
    @XBRead("//*[local-name()='owner']")
    Optional<String> getAttribution();

    /**
     * @return String
     */
    @XBRead("//*[local-name()='ownerLogo']")
    Optional<String> getLogo();

    /**
     * @return Boolean
     */
    @XBRead("boolean(//*[local-name()='relatedItem']/*[local-name()='titleInfo']/*[local-name()='title']" +
            "[text()='Handschriftenkataloge des Handschriftenportals'])")
    Boolean isHspCatalog();

    /**
     * @return String
     */
    @XBRead("boolean(//*[local-name()='typeOfResource'][@manuscript='yes'])")
    Boolean isManuscript();

    /**
     * @param idType String
     * @return String
     */
    @XBRead("//*[local-name()='identifier'][@type='{0}']")
    Optional<String> getManuscriptIdByType(String idType);

    /**
     * @return Optional
     */
    @XBRead("//*[local-name()='subTitle']")
    Optional<String> getSubtitle();

    /**
     * @return Optional
     */
    @XBRead("//*[local-name()='typeOfResource']")
    Optional<String> getMedium();

    /**
     * @return Optional
     */
    @XBRead("//*[local-name()='form'][@type='material']")
    Optional<String> getMaterial();

    /**
     * @return Optional
     */
    @XBRead("//*[local-name()='extent'][@unit='leaves']")
    Optional<String> getExtent();

    /**
     * @return Optional
     */
    @XBRead("//*[local-name()='extent'][@unit='cm']")
    Optional<String> getDimension();

    /**
     * @return Optional
     */
    @XBRead("//*[local-name()='languageTerm'][@type='text']")
    Optional<String> getLanguageDescription();

    /**
     * @return Optional
     */
    @XBRead("//*[local-name()='placeTerm']")
    Optional<String> getLocation();

    /**
     * @return Optional
     */
    @XBRead("//*[local-name()='recordIdentifier']")
    Optional<String> getRecordIdentifier();

    /**
     * @return Optional
     */
    @XBRead("//*[local-name()='identifier'][@type='hsp_kod_id']")
    Optional<String> getHspKodIdentifier();

    /**
     * @return Optional
     */
    @XBRead("//*[local-name()='dateCreated']")
    Optional<String> getDateCreated();

    /**
     * @return String
     */
    @XBRead("//*[local-name()='note']")
    Optional<String> getNote();

    /**
     * @return List
     */
    @XBRead("//*[local-name()='note']/@type")
    List<String> getNoteTypes();

    /**
     * @param noteType String
     * @return String
     */
    @XBRead("//*[local-name()='note'][@type='{0}']")
    Optional<String> getNotesByType(String noteType);

    /**
     * @return String
     */
    @XBRead("//*[local-name()='number']")
    Optional<String> getCensus();

    /**
     * @return String
     */
    @XBRead("//*[local-name()='relatedItem']/*[local-name()='titleInfo']/*[local-name()='title']")
    Optional<String> getCollection();

    @XBRead("//*[local-name()='relatedItem']/*[local-name()='titleInfo']/*[local-name()='title']")
    List<String> getCollections();

    /**
     * @return String
     */
    @XBRead("//*[local-name()='mods']/*[local-name()='recordInfo']/*[local-name()='recordIdentifier'][@source='DE-611']")
    List<String> getKalliopeID();

    /**
     * @return String
     */
    @XBRead("//*[local-name()='shelfLocator']")
    List<String> getCallNumbers();

    /**
     * @return String
     */
    @XBRead("//*[local-name()='shelfLocator']")
    Optional<String> getCallNumber();

    @XBRead("//*[local-name()='links']/*[local-name()='reference']")
    Optional<String> getCatalogReference();

    /**
     * @return String
     */
    @XBRead("//*[local-name()='owner']")
    Optional<String> getOwnerOfDigitalCopy();

    /**
     * @return String
     */
    @XBRead("//*[local-name()='roleTerm'][text()='len']/parent::node()/parent::node()/*[local-name()='displayForm']")
    Optional<String> getOwnerOfOriginal();
    /**
     * @return String
     */
    @XBRead("//*[local-name()='roleTerm'][text()='aut']/parent::node()/parent::node()/*[local-name()='displayForm']")
    Optional<String> getAuthor();

    /**
     * @return String
     */
    @XBRead("//*[local-name()='roleTerm'][text()='rcp']/parent::node()/parent::node()/*[local-name()='displayForm']")
    Optional<String> getAddressee();

    /**
     * @return String
     */
    @XBRead("//*[local-name()='placeTerm']")
    Optional<String> getPlace();

    /**
     * @return String
     */
    @XBRead("//*[local-name()='dateOther'] | //*[local-name()='dateIssued']")
    List<String> getDates();

    /**
     * @return String
     */
    @XBRead("//*[local-name()='dateOther'] | //*[local-name()='dateIssued']")
    Optional<String> getDate();

    /**
     * @return String
     */
    @XBRead("//*[local-name()='publisher']")
    Optional<String> getPublisher();

    /**
     * @return String
     */
    @XBRead("//*[local-name()='extent']")
    Optional<String> getPhysState();

    /**
     * @return List
     */
    @XBRead("//*[local-name()='structMap'][@TYPE='PHYSICAL']/*[local-name()" + "='div']/descendant::node()/@ID")
    List<String> getPhysicalDivs();

    /**
     * @param div String
     * @return String
     */
    @XBRead("//*[local-name()='div'][@ID='{0}']/@ORDERLABEL")
    Optional<String> getOrderLabelForDiv(String div);

    /**
     * @param id      file id
     * @param fileGrp String USE attribute value of mets:fileGrp
     * @return Boolean
     */
    @XBRead("boolean(//*[local-name()='fileGrp'][@USE='{1}']/*[local-name()='file'][@ID='{0}'])")
    Boolean getFileIdInFileGrp(String id, String fileGrp);

    /**
     * @param div String
     * @return String
     */
    @XBRead("//*[local-name()='div'][@ID='{0}']/descendant::node()/@FILEID")
    List<String> getFileIdForDiv(String div);

    /**
     * @param file String
     * @return String
     */
    @XBRead("//*[local-name()='file'][@ID='{0}']/descendant::node()/@*[local-name()='href']")
    Optional<String> getHrefForFile(String file);

    /**
     * @param file String
     * @return String
     */
    @XBRead("//*[local-name()='file'][@ID='{0}']/@MIMETYPE")
    Optional<String> getMimeTypeForFile(String file);

    /**
     * @return List
     */
    @XBRead("//*[local-name()='structLink']/*[local-name()='smLink']")
    List<Xlink> getXlinks();

    @XBRead("//*[local-name()='structMap'][@TYPE='LOGICAL']//*[local-name()='div']/@ID")
    Optional<String> getRootLogicalStructureId();

    /**
     * @param id String
     * @return Logical
     */
    @XBRead("//*[local-name()='structMap'][@TYPE='LOGICAL']//*[local-name()" + "='div'][@ID='{0}']/*[local-name()" +
            "='div'][last()]")
    Logical getLogicalLastDescendent(String id);

    /**
     * @return List
     */
    @XBRead("//*[local-name()='structMap'][@TYPE='LOGICAL']/*[local-name()" + "='div']/*[local-name()='div'][not" +
            "(descendant::div)]")
    List<Logical> getTopLogicals();

    /**
     * @param id String
     * @return List
     */
    @XBRead("//*[local-name()='div'][@ID='{0}']/parent::node()")
    List<Logical> getLogicalLastParent(String id);

    /**
     * @param id String
     * @return List
     */
    @XBRead("//*[local-name()='div'][@ID='{0}']/*[local-name()='div']")
    List<Logical> getLogicalLastChildren(String id);

    /**
     * @param id String
     * @return Optional
     */
    @XBRead("//*[local-name()='structMap'][@TYPE='LOGICAL']//*[local-name()" + "='div'][@ID='{0}']/@LABEL")
    Optional<String> getLogicalLabel(String id);

    /**
     * @param id String
     * @return Optional
     */
    @XBRead("//*[local-name()='structMap'][@TYPE='LOGICAL']//*[local-name()" + "='div'][@ID='{0}']/@TYPE")
    Optional<String> getLogicalType(String id);

    /**
     * @param logicalDivId String
     * @return Optional
     */
    @XBRead("//*[local-name()='structMap'][@TYPE='LOGICAL']//*[local-name()='div'][@ID='{0}']/@DMDID")
    Optional<String> getLogicalDmdId(String logicalDivId);

    /**
     * @param dmdLogId String
     * @return Optional
     */
    @XBRead("//*[local-name()='dmdSec'][@ID='{0}']//*[local-name()='mods']")
    Optional<HspCatalogMods> getDmdMods(String dmdLogId);

    interface Xlink {

        @XBRead("@*[local-name()='from']")
        String getXLinkFrom();

        @XBRead("@*[local-name()='to']")
        String getXLinkTo();
    }

    interface Logical {

        @XBRead("@*[local-name()='ID']")
        String getLogicalId();

        @XBRead("@*[local-name()='LABEL']")
        String getLogicalLabel();

        @XBRead("@*[local-name()='TYPE']")
        String getLogicalType();
    }

    interface HspCatalogMods {
        @XBRead("./*[local-name()='titleInfo']/*[local-name()='title']")
        String getTitle();

        @XBRead("./*[local-name()='titleInfo']/*[local-name()='subTitle']")
        Optional<String> getSubtitle();

        @XBRead("./*[local-name()='identifier'][@type='shelfmark']")
        Optional<String> getCallNumber();

        @XBRead("./*[local-name()='physicalDescription']/*[local-name()='form'][@type='material']")
        List<String> getMaterial();

        @XBRead("./*[local-name()='physicalDescription']/*[local-name()='extent'][@unit='cm']")
        List<String> getDimensions();

        @XBRead("./*[local-name()='physicalDescription']/*[local-name()='extent'][@unit='leaves']")
        List<String> getExtent();

        @XBRead("./*[local-name()='originInfo']//*[local-name()='placeTerm'][@type='text']")
        List<String> getOriginPlace();

        @XBRead("./*[local-name()='originInfo']/*[local-name()='dateCreated'][@qualifier='inferred']")
        List<String> getOriginDate();
    }
}
