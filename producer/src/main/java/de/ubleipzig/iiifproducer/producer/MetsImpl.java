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

import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getAttribution;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getCensus;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getCollection;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getFileIdForDiv;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getHrefForFile;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getLogicalLabel;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getLogicalLastChildren;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getLogicalLastDescendent;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getLogicalLastParent;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getLogicalType;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getLogo;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getManifestTitle;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getManifestTitles;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getManuscriptIdByType;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getMultiVolumeWorkTitle;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getNoteTypes;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getNotesByType;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getOrderLabelForDiv;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getPhysicalDivs;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getRightsUrl;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getRightsValue;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getTopLogicals;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getVolumePartTitleOrPartNumber;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getXlinks;
import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.isManuscript;
import static de.ubleipzig.iiifproducer.doc.ResourceLoader.getMets;
import static java.io.File.separator;
import static java.util.Collections.synchronizedList;
import static java.util.Comparator.comparing;
import static java.util.Comparator.naturalOrder;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import de.ubleipzig.iiifproducer.doc.ManuscriptMetadata;
import de.ubleipzig.iiifproducer.doc.MetsConstants;
import de.ubleipzig.iiifproducer.doc.MetsData;
import de.ubleipzig.iiifproducer.doc.StandardMetadata;
import de.ubleipzig.iiifproducer.template.TemplateManifest;
import de.ubleipzig.iiifproducer.template.TemplateMetadata;
import de.ubleipzig.iiifproducer.template.TemplateStructure;
import de.ubleipzig.iiifproducer.template.TemplateTopStructure;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * MetsImpl.
 *
 * @author christopher-johnson
 */
public class MetsImpl implements MetsAccessor {

    private MetsData mets;
    private Config config;
    private Map<String, List<MetsData.Xlink>> xlinkmap;

    MetsImpl(final Config config) {
        this.mets = getMets(config.getXmlFile());
        this.config = config;
        this.xlinkmap = getXlinkMap();
    }

    @Override
    public Map<String, List<MetsData.Xlink>> getXlinkMap() {
        final List<MetsData.Xlink> xlinks = getXlinks(mets);
        return xlinks.stream().collect(groupingBy(MetsData.Xlink::getXLinkFrom));
    }

    @Override
    public void setManifestLabel(final TemplateManifest body) {
        if (!getCensus(mets).equals("")) {
            body.setLabel(getAnchorFileLabel());
        } else {
            if (getCollection(mets).contains("TestCollection") ^ getCollection(mets).contains("Heisenberg")) {
                for (String title : getManifestTitles(mets)) {
                    body.setLabel(title);
                }
            } else {
                body.setLabel(getManifestTitle(mets));
            }
        }
    }

    @Override
    public void setLicense(final TemplateManifest body) {
        if (getRightsUrl(mets).isEmpty()) {
            body.setLicense(config.getLicense());
        } else {
            final StringBuilder content = new StringBuilder();
            for (String url : getRightsUrl(mets)) {
                content.append(url).append("<br/>");
            }
            body.setLicense(content.toString());
        }
    }

    @Override
    public void setAttribution(final TemplateManifest body) {
        if (getRightsValue(mets).isEmpty()) {
            body.setAttribution(
                    config.getAttributionKey() + getAttribution(mets) + "<br/>" + config.getAttributionLicenseNote());
        } else {
            final StringBuilder content = new StringBuilder();
            for (String value : getRightsValue(mets)) {
                content.append(value).append("<br/>");
            }
            body.setAttribution(content.toString());
        }
    }

    @Override
    public void setLogo(final TemplateManifest body) {
        body.setLogo(getLogo(mets));
    }

    @Override
    public void setHandschriftMetadata(final TemplateManifest body) {
        final ManuscriptMetadata man = new ManuscriptMetadata(mets);
        final List<TemplateMetadata> info = man.getInfo();
        final List<TemplateMetadata> metadata = new ArrayList<>(info);
        body.setMetadata(metadata);
    }

    @Override
    public void setMetadata(final TemplateManifest body) {
        final StandardMetadata man = new StandardMetadata(mets);
        final List<TemplateMetadata> info = man.getInfo();
        final List<TemplateMetadata> metadata = new ArrayList<>(info);
        if (!getCensus(mets).equals("")) {
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
        return new TemplateMetadata(config.getAnchorKey(), getMultiVolumeWorkTitle(mets) + "; " + getCensus(mets));
    }

    @Override
    public String getAnchorFileLabel() {
        return getMultiVolumeWorkTitle(mets) + "; " + getVolumePartTitleOrPartNumber(mets);
    }

    @Override
    public List<String> getCanvases(final String logical) {
        final IRIBuilder iri = new IRIBuilder(this.config);
        final List<String> canvases = new ArrayList<>();
        final List<String> physicals = xlinkmap.get(logical).stream().map(MetsData.Xlink::getXLinkTo).collect(toList());
        physicals.forEach(physical -> {
            canvases.add(iri.buildCanvasIRIfromPhysical(physical));
        });
        return canvases;
    }

    @Override
    public TemplateTopStructure buildTopStructure() {
        final String resourceContext = config.getResourceContext();
        final List<String> ranges = synchronizedList(new ArrayList<>());

        final List<MetsData.Logical> logs = getTopLogicals(mets);
        logs.forEach(logical -> {
            final String rangeId = resourceContext + config.getRangeContext() + separator + logical.getLogicalId();
            ranges.add(0, rangeId);
        });

        final TemplateTopStructure st = new TemplateTopStructure();
        st.setStructureId(resourceContext + config.getRangeContext() + separator
                + MetsConstants.METS_PARENT_LOGICAL_ID);
        st.setStructureLabel("Contents");
        ranges.sort(naturalOrder());
        st.setRanges(ranges);
        return st;
    }

    @Override
    public List<TemplateMetadata> buildStructureMetadata(final String logicalType) {
        final List<TemplateMetadata> metadataList = new ArrayList<>();
        final TemplateMetadata metadata = new TemplateMetadata(MetsConstants.METS_STRUCTURE_TYPE, logicalType);
        metadataList.add(metadata);
        return metadataList;
    }

    @Override
    public List<TemplateStructure> buildStructures() {
        final String resourceContext = config.getResourceContext();
        final List<TemplateStructure> structures = synchronizedList(new ArrayList<>());
        final List<TemplateStructure> descendents = synchronizedList(new ArrayList<>());
        xlinkmap.keySet().forEach(logical -> {
            final MetsData.Logical last = getLogicalLastDescendent(mets, logical);
            if (last != null) {
                final List<MetsData.Logical> logicalLastParentList = getLogicalLastParent(mets, last.getLogicalId());
                logicalLastParentList.forEach(logicalLastParent -> {
                    final String lastParentId = logicalLastParent.getLogicalId();
                    final List<MetsData.Logical> lastChildren = getLogicalLastChildren(mets, lastParentId);

                    final List<String> ranges = synchronizedList(new ArrayList<>());
                    lastChildren.forEach(desc -> {
                        final TemplateStructure descSt = new TemplateStructure();
                        final String descID = desc.getLogicalId().trim();
                        final String rangeId = resourceContext + config.getRangeContext() + separator + descID;
                        final String descLabel = getLogicalLabel(mets, descID);
                        final String logType = getLogicalType(mets, descID);
                        ranges.add(0, rangeId);
                        descSt.setStructureId(rangeId);
                        descSt.setStructureLabel(descLabel);
                        final List<TemplateMetadata> metadataList = buildStructureMetadata(logType);
                        descSt.setMetadata(metadataList);
                        descSt.setCanvases(getCanvases(descID));
                        descendents.add(0, descSt);
                    });
                    final TemplateStructure st = new TemplateStructure();
                    final String structureIdDesc = resourceContext + config.getRangeContext() + separator +
                            lastParentId;
                    st.setStructureId(structureIdDesc);
                    final String logicalLabel = getLogicalLabel(mets, lastParentId);
                    final String logType = getLogicalType(mets, lastParentId);
                    final List<TemplateMetadata> metadataList = buildStructureMetadata(logType);
                    st.setStructureLabel(logicalLabel);
                    st.setMetadata(metadataList);
                    ranges.sort(naturalOrder());
                    st.setRanges(ranges);
                    st.setCanvases(getCanvases(lastParentId));
                    if (!Objects.equals(
                            st.getStructureId(),
                            resourceContext + config.getRangeContext() + separator
                                    + MetsConstants.METS_PARENT_LOGICAL_ID)) {
                        structures.add(0, st);
                    }
                });

            }
        });
        final Comparator<TemplateStructure> c = comparing(TemplateStructure::getStructureId);
        return Stream.concat(structures.stream(), descendents.stream()).filter(
                new ConcurrentSkipListSet<>(c)::add).sorted(comparing(TemplateStructure::getStructureId)).collect(
                Collectors.toList());
    }

    @Override
    public Boolean getMtype() {
        return isManuscript(mets);
    }

    @Override
    public String getUrnReference() {
        return getManuscriptIdByType(mets, MetsConstants.URN_TYPE);
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
    public String getHref(final String file) {
        return getHrefForFile(mets, file);
    }
}
