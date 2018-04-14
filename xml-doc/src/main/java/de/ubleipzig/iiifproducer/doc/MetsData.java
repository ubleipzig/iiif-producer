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

import java.util.List;
import java.util.Optional;

import org.xmlbeam.annotation.XBRead;

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
    String getManifestTitle();

    /**
     * @return String
     */
    @XBRead("//*[local-name()='owner']")
    String getAttribution();

    /**
     * @return String
     */
    @XBRead("//*[local-name()='ownerLogo']")
    String getLogo();

    /**
     * @return String
     */
    @XBRead("//*[local-name()='typeOfResource'][@manuscript='yes']")
    String getManuscriptType();

    /**
     * @param idType String
     * @return String
     */
    @XBRead("//*[local-name()='identifier'][@type='{0}']")
    Optional<String> getManuscriptIdByType(String idType);

    /**
     * @return Optional
     */
    @XBRead("//*[local-name()='subtitle']")
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
    @XBRead("//*[local-name()='language']")
    Optional<String> getLanguage();

    /**
     * @return Optional
     */
    @XBRead("//*[local-name()='place'][@eventType='manufacture']")
    Optional<String> getLocation();

    /**
     * @return Optional
     */
    @XBRead("//*[local-name()='recordIdentifier']")
    Optional<String> getRecordIdentifier();

    /**
     * @return Optional
     */
    @XBRead("//*[local-name()='dateCreated']")
    Optional<String> getDateCreated();

    /**
     * @return String
     */
    @XBRead("//*[local-name()='note']")
    String getNote();

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
    String getNotesByType(String noteType);

    /**
     * @return String
     */
    @XBRead("//*[local-name()='number']")
    String getCensus();

    /**
     * @return String
     */
    @XBRead("//*[local-name()='title']")
    String getCollection();

    /**
     * @return String
     */
    @XBRead("//*[local-name()='shelfLocator']")
    String getCallNumber();

    /**
     * @return String
     */
    @XBRead("//*[local-name()='owner']")
    String getOwner();

    /**
     * @return String
     */
    @XBRead("//*[local-name()='displayForm']")
    String getAuthor();

    /**
     * @return String
     */
    @XBRead("//*[local-name()='placeTerm']")
    String getPlace();

    /**
     * @return String
     */
    @XBRead("//*[local-name()='dateOther']")
    String getDate();

    /**
     * @return String
     */
    @XBRead("//*[local-name()='publisher']")
    String getPublisher();

    /**
     * @return String
     */
    @XBRead("//*[local-name()='extent']")
    String getPhysState();

    /**
     * @return List
     */
    @XBRead("//*[local-name()='structMap'][@TYPE='PHYSICAL']/*[local-name()" + "='div']/descendant::node()/@ID")
    List<String> getPhysicalDivs();

    /**
     * @return List
     */
    @XBRead("//*[local-name()='fileGrp'][@USE='ORIGINAL']/descendant::node()/@ID")
    List<String> getFileResources();

    /**
     * @param div String
     * @return String
     */
    @XBRead("//*[local-name()='div'][@ID='{0}']/@ORDERLABEL")
    String getOrderLabelForDiv(String div);

    /**
     * @param div String
     * @return String
     */
    @XBRead("//*[local-name()='div'][@ID='{0}']/descendant::node()/@FILEID")
    String getFileIdForDiv(String div);

    /**
     * @param file String
     * @return String
     */
    @XBRead("//*[local-name()='file'][@ID='{0}']/descendant::node()/@*[local-name()='href']")
    String getHrefForFile(String file);

    /**
     * @param file String
     * @return String
     */
    @XBRead("//*[local-name()='file'][@ID='{0}']/@MIMETYPE")
    String getMimeTypeForFile(String file);

    /**
     * @return List
     */
    @XBRead("//*[local-name()='structLink']/*[local-name()='smLink']")
    List<Xlink> getXlinks();

    /**
     * @param id String
     * @return Logical
     */
    @XBRead("//*[local-name()='structMap'][@TYPE='LOGICAL']//*[local-name()" + "='div'][@ID='{0}']/*[local-name()"
            + "='div'][last()]")
    Logical getLogicalLastDescendent(String id);

    /**
     * @return List
     */
    @XBRead("//*[local-name()='structMap'][@TYPE='LOGICAL']/*[local-name()" + "='div']/*[local-name()='div'][not"
            + "(descendant::div)]")
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
}
