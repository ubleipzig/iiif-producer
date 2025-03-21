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

import de.ubleipzig.iiifproducer.doc.*;
import de.ubleipzig.iiifproducer.model.Metadata;
import de.ubleipzig.iiifproducer.model.v2.Manifest;
import de.ubleipzig.iiifproducer.model.v2.Structure;
import de.ubleipzig.iiifproducer.model.v2.TopStructure;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.*;
import static java.util.Collections.synchronizedList;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * MetsImpl.
 *
 * @author christopher-johnson
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
public class MetsImpl implements MetsAccessor {

    @Builder.Default
    private String anchorKey = "Part of";
    @Builder.Default
    private String attributionKey = "Provided by ";
    @Builder.Default
    private String attributionLicenseNote = "No Copyright - Public Domain Marked";
    private IRIBuilder iriBuilder;
    @Builder.Default
    private String license = "https://creativecommons.org/publicdomain/mark/1.0/";

    private MetsData mets;
    private String resourceContext;
    private Map<String, List<MetsData.Xlink>> xlinkmap;
    private String xmlFile;

    public static Map<String, List<MetsData.Xlink>> getXlinkMap(MetsData mets) {
        final List<MetsData.Xlink> xlinks = getXlinks(mets);
        return xlinks.stream().collect(groupingBy(MetsData.Xlink::getXLinkFrom));
    }

    @Override
    public Metadata getAnchorFileMetadata() {
        return Metadata.builder().label(anchorKey).value(getMultiVolumeWorkTitle(mets) + "; " + getCensusHost(mets)).build();
    }

    @Override
    public String getAnchorFileLabel() {
        return getMultiVolumeWorkTitle(mets) + "; " + getVolumePartTitleOrPartNumber(mets);
    }

    @Override
    public List<String> getCanvases(final String logical) {
        final List<String> canvases = new ArrayList<>();
        if (!xlinkmap.containsKey(logical)) {
            throw new RuntimeException("Logical structure " + logical + " is not linked to any physical structures.");
        }
        final List<String> physicals = xlinkmap.get(logical).stream().map(MetsData.Xlink::getXLinkTo).collect(toList());
        final List<String> sequencePhysicalDivIds = mets.getPhysicalDivs();
        physicals.forEach(physical -> {
            int indexOfPhysicalId = sequencePhysicalDivIds.indexOf(physical);
            canvases.add(iriBuilder.buildCanvasIRIfromPhysical(physical, indexOfPhysicalId));
        });
        return canvases;
    }

    @Override
    public TopStructure buildTopStructure() {
        final List<String> ranges = synchronizedList(new ArrayList<>());

        final List<MetsData.Logical> logs = getTopLogicals(mets);
        logs.forEach(logical -> {
            final String rangeId = iriBuilder.buildRangeId(logical.getLogicalId());
            ranges.add(rangeId);
        });

        final TopStructure st = TopStructure.builder()
                .id(iriBuilder.buildRangeId(mets.getRootLogicalStructureId().orElse(MetsConstants.METS_PARENT_LOGICAL_ID)))
                .label("Contents")
                .build();
        st.setRanges(ranges);
        return st;
    }

    @Override
    public List<Metadata> buildStructureMetadata(final String logicalType) {
        final List<Metadata> metadataList = new ArrayList<>();
        final Metadata metadata =
                Metadata.builder().label(MetsConstants.METS_STRUCTURE_TYPE).value(logicalType).build();
        metadataList.add(metadata);
        return metadataList;
    }

    @Override
    public List<Structure> buildStructures() {
        final List<Structure> structures = synchronizedList(new ArrayList<>());
        final List<Structure> descendents = synchronizedList(new ArrayList<>());
        String rootLogicalId = mets.getRootLogicalStructureId().orElse(MetsConstants.METS_PARENT_LOGICAL_ID);
        xlinkmap.keySet().forEach(logical -> {
            final MetsData.Logical last = getLogicalLastDescendent(mets, logical);
            if (last != null) {
                final List<MetsData.Logical> logicalLastParentList = getLogicalLastParent(mets, last.getLogicalId());
                logicalLastParentList.forEach(logicalLastParent -> {
                    final String lastParentId = logicalLastParent.getLogicalId();
                    final List<MetsData.Logical> lastChildren = getLogicalLastChildren(mets, lastParentId);

                    final List<String> ranges = synchronizedList(new ArrayList<>());
                    lastChildren.forEach(desc -> {
                        final String descID = desc.getLogicalId().trim();
                        final String rangeId = iriBuilder.buildRangeId(descID);
                        final String descLabel = getLogicalLabel(mets, descID);
                        final Structure descSt = Structure.builder()
                                .id(rangeId)
                                .label(descLabel)
                                .build();
                        final String logType = getLogicalType(mets, descID);
                        ranges.add(rangeId);
                        if (mets.isHspCatalog()) {
                            final HspCatalogStructureMetadata hspMd = new HspCatalogStructureMetadata(mets, descID);
                            final List<Metadata> metadataList = hspMd.getInfo();
                            descSt.setMetadata(metadataList);
                        } else {
                            final List<Metadata> metadataList = buildStructureMetadata(logType);
                            descSt.setMetadata(metadataList);
                        }
                        descSt.setCanvases(getCanvases(descID));
                        descendents.add(0, descSt);
                    });
                    final Structure st = new Structure();
                    final String structureIdDesc = iriBuilder.buildRangeId(lastParentId);
                    st.setId(structureIdDesc);
                    final String logicalLabel = getLogicalLabel(mets, lastParentId);
                    final String logType = getLogicalType(mets, lastParentId);
                    final List<Metadata> metadataList = buildStructureMetadata(logType);
                    st.setLabel(logicalLabel);
                    st.setMetadata(metadataList);
                    st.setRanges(ranges);
                    st.setCanvases(getCanvases(lastParentId));
                    if (!Objects.equals(
                            st.getId(),
                            iriBuilder.buildRangeId(rootLogicalId))) {
                        structures.add(0, st);
                    }
                });

            }
        });
        final Comparator<Structure> c = comparing(Structure::getId);
        return Stream.concat(structures.stream(), descendents.stream()).filter(
                new ConcurrentSkipListSet<>(c)::add).sorted(comparing(Structure::getId)).collect(
                Collectors.toList());
    }

    @Override
    public Boolean getCatalogType() {
        return isHspCatalog(mets);
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
    public String getCatalogReference() {
        return mets.getCatalogReference().orElse("").trim();
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
    public String getFile(final String div, final String fileGrp) {
        return getFileIdForDiv(mets, div, fileGrp);
    }

    @Override
    public String getHref(final String file) {
        return getHrefForFile(mets, file);
    }

    @Override
    public String getFormatForFile(final String fileId) {
        return getMimeTypeForFile(mets, fileId);
    }

    public static class MetsImplBuilder {
        public MetsImplBuilder mets() {
            this.mets = de.ubleipzig.iiifproducer.doc.ResourceLoader.getMets(xmlFile);
            return this;
        }

        public MetsImplBuilder xlinkmap() {
            this.xlinkmap = getXlinkMap(this.mets);
            return this;
        }
    }

    @Override
    public void setAttribution(final Manifest body) {
        // TODO HTML should be wellformed XML https://iiif.io/api/presentation/2.1/#html-markup-in-property-values
        if (getRightsValue(mets).isEmpty() && getCopyrightHolders(mets).isEmpty()) {
            body.setAttribution(
                    attributionKey + getAttribution(mets) + "<br/>" + attributionLicenseNote);
        } else {
            final StringBuilder content = new StringBuilder();
            for (String value : getRightsValue(mets)) {
                content.append(value).append("<br/>");
            }
            for (String cph : getCopyrightHolders(mets)) {
                content.append(cph).append("<br/>");
            }
            body.setAttribution(content.toString());
        }
    }

    @Override
    public void setHandschriftMetadata(final Manifest body) {
        final ManuscriptMetadata man = new ManuscriptMetadata(mets);
        final List<Metadata> info = man.getInfo();
        final List<Metadata> metadata = new ArrayList<>(info);
        body.setMetadata(metadata);
    }

    @Override
    public void setHspCatalogMetadata(final Manifest body) {
        final HspCatalogMetadata catalogMetadata = new HspCatalogMetadata(mets);
        final List<Metadata> info = catalogMetadata.getInfo();
        final List<Metadata> metadata = new ArrayList<>(info);
        body.setMetadata(metadata);
    }

    @Override
    public void setLicense(final Manifest body) {
        if (getRightsUrl(mets).isEmpty()) {
            body.setLicense(Collections.singletonList(license));
        } else {
            body.setLicense(getRightsUrl(mets));
        }
    }

    @Override
    public void setLogo(final Manifest body) {
        body.setLogo(getLogo(mets));
    }

    @Override
    public void setManifestLabel(final Manifest body) {
        if (!getCensusHost(mets).isEmpty()) {
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
    public void setMetadata(final Manifest body) {
        final StandardMetadata man = new StandardMetadata(mets);
        final List<Metadata> info = man.getInfo();
        final List<Metadata> metadata = new ArrayList<>(info);
        if (!getCensusHost(mets).isEmpty()) {
            // FIXME move to StandardMetadata
            metadata.add(getAnchorFileMetadata());
        }
        // FIXME move to StandardMetadata
        final List<String> noteTypesNonUnique = getNoteTypes(mets);
        final Set<String> noteTypes = new LinkedHashSet<>(noteTypesNonUnique);
        for (String nt : noteTypes) {
            if (nt.equals("comment")) {
                // this is already in StandardMetadata
                continue;
            }
            // TODO https://projekte.ub.uni-leipzig.de/issues/25028
            for (String value: getNotesByType(mets, nt)) {
                metadata.add(Metadata.builder().label(nt).value(value.trim()).build());
            }
        }
        body.setMetadata(metadata);
    }
}
