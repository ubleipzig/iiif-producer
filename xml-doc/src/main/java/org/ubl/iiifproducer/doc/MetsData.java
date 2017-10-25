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

package org.ubl.iiifproducer.doc;

import java.util.List;
import java.util.Optional;
import org.xmlbeam.annotation.XBRead;

/**
 * MetsData.
 *
 * @author christopher-johnson
 */
public interface MetsData {

    @XBRead("//*[local-name()='mods']/*[local-name()='titleInfo']/*[local-name()='title']")
    String getManifestTitle();

    @XBRead("//*[local-name()='owner']")
    String getAttribution();

    @XBRead("//*[local-name()='ownerLogo']")
    String getLogo();

    @XBRead("//*[local-name()='typeOfResource'][@manuscript='yes']")
    String getManuscriptType();

    @XBRead("//*[local-name()='identifier'][@type='{0}']")
    String getManuscriptIdByType(String idType);

    @XBRead("//*[local-name()='subtitle']")
    Optional<String> getSubtitle();

    @XBRead("//*[local-name()='typeOfResource']")
    Optional<String> getMedium();

    @XBRead("//*[local-name()='form'][@type='material']")
    Optional<String> getMaterial();

    @XBRead("//*[local-name()='extent'][@unit='leaves']")
    Optional<String> getExtent();

    @XBRead("//*[local-name()='extent'][@unit='cm']")
    Optional<String> getDimension();

    @XBRead("//*[local-name()='language']")
    Optional<String> getLanguage();

    @XBRead("//*[local-name()='place'][@eventType='manufacture']")
    Optional<String> getLocation();

    @XBRead("//*[local-name()='recordIdentifier']")
    Optional<String> getRecordIdentifier();

    @XBRead("//*[local-name()='dateCreated']")
    Optional<String> getDateCreated();

    @XBRead("//*[local-name()='note']")
    String getNote();

    @XBRead("//*[local-name()='note']/@type")
    List<String> getNoteTypes();

    @XBRead("//*[local-name()='note'][@type='{0}']")
    String getNotesByType(String noteType);

    @XBRead("//*[local-name()='number']")
    String getCensus();

    @XBRead("//*[local-name()='title']")
    String getCollection();

    @XBRead("//*[local-name()='shelfLocator']")
    String getCallNumber();

    @XBRead("//*[local-name()='owner']")
    String getOwner();

    @XBRead("//*[local-name()='displayForm']")
    String getAuthor();

    @XBRead("//*[local-name()='placeTerm']")
    String getPlace();

    @XBRead("//*[local-name()='dateOther']")
    String getDate();

    @XBRead("//*[local-name()='publisher']")
    String getPublisher();

    @XBRead("//*[local-name()='extent']")
    String getPhysState();

    @XBRead("//*[local-name()='structMap'][@TYPE='PHYSICAL']/*[local-name()"
            + "='div']/descendant::node()/@ID")
    List<String> getPhysicalDivs();

    @XBRead("//*[local-name()='fileGrp'][@USE='ORIGINAL']/descendant::node()/@ID")
    List<String> getFileResources();

    @XBRead("//*[local-name()='div'][@ID='{0}']/@ORDERLABEL")
    String getOrderLabelForDiv(String div);

    @XBRead("//*[local-name()='div'][@ID='{0}']/descendant::node()/@FILEID")
    String getFileIdForDiv(String div);

    @XBRead("//*[local-name()='file'][@ID='{0}']/descendant::node()/@*[local-name()='href']")
    String getHrefForFile(String file);

    @XBRead("//*[local-name()='file'][@ID='{0}']/@MIMETYPE")
    String getMimeTypeForFile(String file);

    @XBRead("//*[local-name()='structLink']/*[local-name()='smLink']")
    List<Xlink> getXlinks();

    @XBRead("//*[local-name()='structMap'][@TYPE='LOGICAL']//*[local-name()"
            + "='div'][@ID='{0}']/*[local-name()='div'][last()]")
    Logical getLogicalLastDescendent(String id);

    @XBRead("//*[local-name()='div'][@ID='{0}']/parent::node()")
    List<Logical> getLogicalLastParent(String id);

    @XBRead("//*[local-name()='div'][@ID='{0}']/*[local-name()='div']")
    List<Logical> getLogicalLastChildren(String id);

    @XBRead("//*[local-name()='structMap'][@TYPE='LOGICAL']//*[local-name()"
            + "='div'][@ID='{0}']/@LABEL")
    String getLogicalLabel(String id);

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
