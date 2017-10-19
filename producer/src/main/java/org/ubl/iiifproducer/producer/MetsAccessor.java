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

package org.ubl.iiifproducer.producer;

import static org.ubl.iiifproducer.doc.MetsData.Xlink;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.ubl.iiifproducer.template.TemplateBody;
import org.ubl.iiifproducer.template.TemplateStructure;
import org.ubl.iiifproducer.template.TemplateTopStructure;

/**
 * MetsAccessor.
 *
 * @author christopher-johnson
 */
public interface MetsAccessor {

    void setManifestLabel(TemplateBody body);

    void setAttribution(TemplateBody body);

    void setLogo(TemplateBody body);

    void setHandschriftMetadata(TemplateBody body);

    void setMetadata(TemplateBody body);

    void setAnchorfileMetadata(TemplateBody body) throws IOException;

    Map<String, List<Xlink>> getXlinkMap();

    List<String> getCanvases(String logical);

    TemplateTopStructure buildTopStructure();

    List<TemplateStructure> buildStructures();

    String getMtype();

    List<String> getPhysical();

    String getOrderLabel(String div);

    String getFile(String div);

    List<String> getResources();

    String getHref(String file);
}
