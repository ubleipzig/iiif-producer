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

import static java.io.File.separator;
import static java.util.Collections.synchronizedList;
import static java.util.Comparator.comparing;
import static java.util.Comparator.naturalOrder;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static org.ubl.iiifproducer.doc.MetsData.Logical;
import static org.ubl.iiifproducer.doc.MetsData.Xlink;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getAttribution;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getCensus;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getFileIdForDiv;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getFileResources;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getHrefForFile;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getLogicalLabel;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getLogicalLastChildren;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getLogicalLastDescendent;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getLogicalLastParent;
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
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private MetsData anchorDoc;
    private Config config;
    private Map<String, List<Xlink>> xlinkmap;

    MetsImpl(final Config config) throws IOException {
        this.mets = getMets(config.getInputFile());
        this.anchorDoc = getMetsAnchor(config.getInputFile());
        this.config = config;
        this.xlinkmap = getXlinkMap();
    }

    @Override
    public Map<String, List<Xlink>> getXlinkMap() {
        final List<Xlink> xlinks = getXlinks(mets);
        return xlinks.stream().collect(groupingBy(Xlink::getXLinkFrom));
    }

    @Override
    public void setManifestLabel(final TemplateBody body) {
        if (anchorDoc != null) {
            body.setLabel(getAnchorFileLabel());
        } else {
            body.setLabel(getManifestTitle(mets));
        }
    }

    @Override
    public void setAttribution(final TemplateBody body) {
        body.setAttribution(ATTRIBUTION_KEY + getAttribution(mets));
    }

    @Override
    public void setLogo(final TemplateBody body) {
        body.setLogo(getLogo(mets));
    }

    @Override
    public void setHandschriftMetadata(final TemplateBody body) {
        final ManuscriptMetadata man = new ManuscriptMetadata(mets);
        final List<TemplateMetadata> info = man.getInfo();
        final List<TemplateMetadata> metadata = new ArrayList<>(info);
        body.setMetadata(metadata);
    }

    @Override
    public void setMetadata(final TemplateBody body) {
        final StandardMetadata man = new StandardMetadata(mets);
        final List<TemplateMetadata> info = man.getInfo();
        final List<TemplateMetadata> metadata = new ArrayList<>(info);
        if (anchorDoc != null) {
            metadata.add(getAnchorFileMetadata());
        }
        final List<String> noteTypes = getNoteTypes(mets);
        for (String nt : noteTypes) {
            metadata.add(new TemplateMetadata(nt, getNotesByType(mets, nt).trim()));
        }
        body.setMetadata(metadata);
    }

    @Override
    public TemplateMetadata getAnchorFileMetadata() {
        if (anchorDoc != null) {
            if (getManifestTitle(anchorDoc) != null && getManifestTitle(mets) != null) {
                return new TemplateMetadata(ANCHOR_KEY, getManifestTitle(anchorDoc) + "; " + getCensus(mets));
            }
        }
        return null;
    }

    /**
     * @return String
     */
    public String getAnchorFileLabel() {
        return getManifestTitle(anchorDoc) + "; " + getManifestTitle(mets);
    }

    @Override
    public List<String> getCanvases(final String logical) {
        final IRIUtils iri = new IRIUtils(this.config);
        final List<String> canvases = new ArrayList<>();
        final List<String> physicals = xlinkmap.get(logical).stream().map(Xlink::getXLinkTo).collect(toList());
        physicals.forEach(physical -> {
            canvases.add(iri.buildCanvasIRIfromPhysical(physical));
        });
        return canvases;
    }

    @Override
    public TemplateTopStructure buildTopStructure() {
        final String resourceContext = config.getResourceContext();
        final List<String> ranges = synchronizedList(new ArrayList<>());

        final List<Logical> logs = mets.getTopLogicals();
        logs.forEach(logical -> {
            final String rangeId = resourceContext + IIIF_RANGE + separator + logical.getLogicalId();
            ranges.add(0, rangeId);
        });

        final TemplateTopStructure st = new TemplateTopStructure();
        st.setStructureId(resourceContext + IIIF_RANGE + "/" + "r0");
        st.setStructureLabel("TOC");
        ranges.sort(naturalOrder());
        st.setRanges(ranges);
        return st;
    }

    @Override
    public List<TemplateStructure> buildStructures() {
        final String resourceContext = config.getResourceContext();
        final List<TemplateStructure> structures = synchronizedList(new ArrayList<>());
        final List<TemplateStructure> descendents = synchronizedList(new ArrayList<>());
        xlinkmap.keySet().forEach(logical -> {
            final Logical last = getLogicalLastDescendent(mets, logical);
            if (last != null) {
                final List<Logical> logicalLastParentList = getLogicalLastParent(mets, last.getLogicalId());
                logicalLastParentList.forEach(logicalLastParent -> {
                    final String lastParentId = logicalLastParent.getLogicalId();
                    final List<Logical> lastChildren = getLogicalLastChildren(mets, lastParentId);

                    final List<String> ranges = synchronizedList(new ArrayList<>());
                    lastChildren.forEach(desc -> {
                        final TemplateStructure descSt = new TemplateStructure();
                        final String descID = desc.getLogicalId().trim();
                        final String rangeId = resourceContext + IIIF_RANGE + separator + descID;
                        final String descLabel = getLogicalLabel(mets, descID);
                        ranges.add(0, rangeId);
                        descSt.setStructureId(rangeId);
                        descSt.setStructureLabel(descLabel);
                        descSt.setStructureType(SC._Range);
                        descSt.setCanvases(getCanvases(descID));
                        descendents.add(0, descSt);
                    });
                    final TemplateStructure st = new TemplateStructure();
                    final String structureIdDesc = resourceContext + IIIF_RANGE + "/" + lastParentId;
                    st.setStructureId(structureIdDesc);
                    final String logicalLabel = getLogicalLabel(mets, lastParentId);
                    st.setStructureLabel(logicalLabel);
                    st.setStructureType(SC._Range);
                    ranges.sort(naturalOrder());
                    st.setRanges(ranges);
                    st.setCanvases(getCanvases(lastParentId));
                    if (!Objects.equals(st.getStructureId(), resourceContext + IIIF_RANGE + separator + "LOG_0000")) {
                        structures.add(0, st);
                    }
                });

            }
        });
        final Comparator<TemplateStructure> c = Comparator.comparing(TemplateStructure::getStructureId);
        return Stream.concat(structures.stream(), descendents.stream()).filter(
                new ConcurrentSkipListSet<>(c)::add).sorted(comparing(TemplateStructure::getStructureId)).collect(
                Collectors.toList());
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
    public String getOrderLabel(final String div) {
        return getOrderLabelForDiv(mets, div);
    }

    @Override
    public String getFile(final String div) {
        return getFileIdForDiv(mets, div);
    }

    @Override
    public List<String> getResources() {
        return getFileResources(mets);
    }

    @Override
    public String getHref(final String file) {
        return getHrefForFile(mets, file);
    }
}
