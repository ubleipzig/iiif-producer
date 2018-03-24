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

package org.ubl.iiifproducer.producer;

import static org.ubl.iiifproducer.doc.MetsData.Xlink;

import java.util.List;
import java.util.Map;

import org.ubl.iiifproducer.template.TemplateBody;
import org.ubl.iiifproducer.template.TemplateMetadata;
import org.ubl.iiifproducer.template.TemplateStructure;
import org.ubl.iiifproducer.template.TemplateTopStructure;

/**
 * MetsAccessor.
 *
 * @author christopher-johnson
 */
public interface MetsAccessor {

    /**
     * @param body TemplateBody
     */
    void setManifestLabel(TemplateBody body);

    /**
     * @param body TemplateBody
     */
    void setAttribution(TemplateBody body);

    /**
     * @param body TemplateBody
     */
    void setLogo(TemplateBody body);

    /**
     * @param body TemplateBody
     */
    void setHandschriftMetadata(TemplateBody body);

    /**
     * @param body TemplateBody
     */
    void setMetadata(TemplateBody body);

    /**
     * @return TemplateMetadata
     */
    TemplateMetadata getAnchorFileMetadata();

    /**
     * @return String
     */
    String getAnchorFileLabel();

    /**
     * @return Map
     */
    Map<String, List<Xlink>> getXlinkMap();

    /**
     * @param logical String
     * @return List
     */
    List<String> getCanvases(String logical);

    /**
     * @return TemplateTopStructure
     */
    TemplateTopStructure buildTopStructure();

    /**
     * @return List
     */
    List<TemplateStructure> buildStructures();

    /**
     * @return String
     */
    String getMtype();

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
     * @param div String
     * @return String
     */
    String getFile(String div);

    /**
     * @return List
     */
    List<String> getResources();

    /**
     * @param file String
     * @return String
     */
    String getHref(String file);
}
