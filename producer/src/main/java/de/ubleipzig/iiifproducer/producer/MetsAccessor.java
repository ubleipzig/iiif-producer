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

package de.ubleipzig.iiifproducer.producer;

import de.ubleipzig.iiifproducer.doc.MetsData;
import de.ubleipzig.iiifproducer.model.Metadata;
import de.ubleipzig.iiifproducer.model.v2.Manifest;
import de.ubleipzig.iiifproducer.model.v2.Structure;
import de.ubleipzig.iiifproducer.model.v2.TopStructure;

import java.util.List;
import java.util.Map;

/**
 * MetsAccessor.
 *
 * @author christopher-johnson
 */
public interface MetsAccessor {

    /**
     * @return Map
     */
    static Map<String, List<MetsData.Xlink>> getXlinkMap(MetsData mets) {
        return null;
    }

    /**
     * @return Metadata
     */
    Metadata getAnchorFileMetadata();

    /**
     * @return String
     */
    String getAnchorFileLabel();

    /**
     * @param logical String
     * @return List
     */
    List<String> getCanvases(String logical);

    /**
     * @return TemplateTopStructure
     */
    TopStructure buildTopStructure();

    /**
     * @return List
     */
    List<Structure> buildStructures();

    /**
     * @param logicalType String
     * @return List
     */
    List<Metadata> buildStructureMetadata(String logicalType);

    /**
     * @return boolean
     */
    Boolean getCatalogType();

    /**
     * @return String
     */
    Boolean getMtype();

    /**
     * @return String
     */
    String getUrnReference();

    /**
     * @return List
     */
    List<String> getPhysical();

    /**
     * @param div String
     * @return String
     */
    String getOrderLabel(String div);

    /**
     * @param div     String
     * @param fileGrp String
     * @return String
     */
    String getFile(String div, String fileGrp);

    /**
     * @param file String
     * @return String
     */
    String getHref(String file);

    /**
     * @param fileId String
     * @return String media type for file
     */
    String getFormatForFile(String fileId);

    /**
     * @param body Manifest
     */
    void setAttribution(Manifest body);

    /**
     * @param body Manifest
     */
    void setHandschriftMetadata(Manifest body);

    /**
     * @param body Manifest
     */
    void setHspCatalogMetadata(Manifest body);

    /**
     * @param body Manifest
     */
    void setLicense(Manifest body);

    /**
     * @param body Manifest
     */
    void setLogo(Manifest body);

    /**
     * @param body Manifest
     */
    void setManifestLabel(Manifest body);

    /**
     * @param body Manifest
     */
    void setMetadata(Manifest body);
}
