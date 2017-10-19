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

import static java.util.Collections.synchronizedList;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.ubl.iiifproducer.doc.MetsData.Logical;
import static org.ubl.iiifproducer.doc.MetsData.Xlink;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getAttribution;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getFileIdForDiv;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getFileResources;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getHrefForFile;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getLogical;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getLogo;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getManifestTitle;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getManuscriptType;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getNoteTypes;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getNotesByType;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getOrderLabelForDiv;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getPhysicalDivs;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getXlinks;
import static org.ubl.iiifproducer.doc.ResourceLoader.getMets;
import static org.ubl.iiifproducer.doc.ResourceLoader.getMetsAnchor;
import static org.ubl.iiifproducer.producer.Constants.ANCHOR_KEY;
import static org.ubl.iiifproducer.producer.Constants.ATTRIBUTION_KEY;
import static org.ubl.iiifproducer.producer.Constants.IIIF_RANGE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.ubl.iiifproducer.doc.ManuscriptMetadata;
import org.ubl.iiifproducer.doc.MetsData;
import org.ubl.iiifproducer.doc.StandardMetadata;
import org.ubl.iiifproducer.template.TemplateBody;
import org.ubl.iiifproducer.template.TemplateMetadata;
import org.ubl.iiifproducer.template.TemplateStructure;
import org.ubl.iiifproducer.template.TemplateTopStructure;
import org.ubl.iiifproducer.vocabulary.SC;

/**
 * MetsImpl.
 *
 * @author christopher-johnson
 */
public class MetsImpl implements MetsAccessor {
    private MetsData mets;
    private Config config;
    private Map<String, List<Xlink>> xlinkmap;

    MetsImpl(Config config) throws IOException {
        this.mets = getMets(config.getInputFile());
        this.config = config;
        this.xlinkmap = getXlinkMap();
    }

    @Override
    public Map<String, List<Xlink>> getXlinkMap() {
        List<Xlink> xlinks = getXlinks(mets);
        return xlinks.stream().collect(groupingBy(Xlink::getXLinkFrom));
    }

    @Override
    public void setManifestLabel(TemplateBody body) {
        body.setLabel(getManifestTitle(mets));
    }

    @Override
    public void setAttribution(TemplateBody body) {
        body.setAttribution(ATTRIBUTION_KEY + getAttribution(mets));
    }

    @Override
    public void setLogo(TemplateBody body) {
        body.setLogo(getLogo(mets));
    }

    @Override
    public void setHandschriftMetadata(TemplateBody body) {
        List<TemplateMetadata> metadata = new ArrayList<>();
        ManuscriptMetadata man = new ManuscriptMetadata(mets);
        List<TemplateMetadata> info = man.getInfo();
        metadata.addAll(info);
        body.setMetadata(metadata);
    }

    @Override
    public void setMetadata(TemplateBody body) {
        List<TemplateMetadata> metadata = new ArrayList<>();
        StandardMetadata man = new StandardMetadata(mets);
        List<TemplateMetadata> info = man.getInfo();
        metadata.addAll(info);
        List<String> noteTypes = getNoteTypes(mets);
        for (String nt : noteTypes) {
            metadata.add(new TemplateMetadata(nt, getNotesByType(mets, nt).trim()));
        }
        body.setMetadata(metadata);
    }

    //TODO get an anchorFile to Test with
    @Override
    public void setAnchorfileMetadata(TemplateBody body) throws IOException {
        MetsData anchorDoc = getMetsAnchor(config.getInputFile());
        if (anchorDoc != null) {
            List<TemplateMetadata> meta = new ArrayList<>();
            if (getManifestTitle(anchorDoc) != null && getManifestTitle(mets) != null) {
                body.setLabel(getManifestTitle(anchorDoc) + ";" + getManifestTitle(mets));
                meta.add(new TemplateMetadata(
                        ANCHOR_KEY,
                        getManifestTitle(anchorDoc) + ";" + getManifestTitle(mets)));
            }
            body.setMetadata(meta);
        }
    }

    @Override
    public List<String> getCanvases(String logical) {
        IRIUtils iri = new IRIUtils(this.config);

        List<String> canvases = new ArrayList<>();
        List<String> physicals = xlinkmap.get(logical).stream().map(Xlink::getXLinkTo)
                .collect(toList());
        physicals.forEach(physical -> {
            canvases.add(iri.buildCanvasIRIfromPhysical(physical));
        });
        return canvases;
    }

    @Override
    public TemplateTopStructure buildTopStructure() {
        String resourceContext = config.getResourceContext();
        List<String> ranges = synchronizedList(new ArrayList<>());

        xlinkmap.keySet().forEach(logical -> {
            String rangeId = resourceContext + IIIF_RANGE + "/" + logical;
            ranges.add(0, rangeId);
        });

        TemplateTopStructure st = new TemplateTopStructure();
        st.setStructureId(resourceContext + IIIF_RANGE + "/" + "r0");
        st.setStructureLabel("TOC");
        st.setRanges(ranges);
        return st;
    }

    @Override
    public List<TemplateStructure> buildStructures() {
        String resourceContext = config.getResourceContext();
        List<Logical> logDivs = getLogical(mets);

        Map<String, String> logicalLabelMap = logDivs.stream().collect(
                toMap(Logical::getLogicalId, Logical::getLogicalLabel));

        Map<String, String> logicalTypeMap = logDivs.stream().collect(
                toMap(Logical::getLogicalId, Logical::getLogicalType));

        List<TemplateStructure> structures = synchronizedList(new ArrayList<>());
        List<String> ranges = synchronizedList(new ArrayList<>());

        xlinkmap.keySet().forEach(logical -> {
            TemplateStructure st = new TemplateStructure();
            String rangeId = resourceContext + IIIF_RANGE + "/" + logical;
            st.setStructureId(rangeId);
            st.setStructureLabel(logicalLabelMap.get(logical));
            st.setStructureType(SC._Range);
            st.setCanvases(getCanvases(logical));
            ranges.add(0, rangeId);
            //st.setRanges(ranges);
            structures.add(0, st);
        });
        return structures;
    }

    @Override
    public String getMtype() {
        return getManuscriptType(mets);
    }

    @Override
    public List<String> getPhysical() {
        return getPhysicalDivs(mets);
    }

    @Override
    public String getOrderLabel(String div) {
        return getOrderLabelForDiv(mets, div);
    }

    @Override
    public String getFile(String div) {
        return getFileIdForDiv(mets, div);
    }

    @Override
    public List<String> getResources() {
        return getFileResources(mets);
    }

    @Override
    public String getHref(String file) {
        return getHrefForFile(mets, file);
    }
}
