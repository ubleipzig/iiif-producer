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
import de.ubleipzig.iiifproducer.template.TemplateManifest;
import de.ubleipzig.iiifproducer.template.TemplateMetadata;
import de.ubleipzig.iiifproducer.template.TemplateStructure;
import de.ubleipzig.iiifproducer.template.TemplateTopStructure;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.*;
import static java.io.File.separator;
import static java.util.Collections.synchronizedList;
import static java.util.Comparator.comparing;
import static java.util.Comparator.naturalOrder;
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

    private MetsData mets;
    private Map<String, List<MetsData.Xlink>> xlinkmap;
    private IRIBuilder iriBuilder;
    private String anchorKey;
    private String attributionKey;
    private String attributionLicenseNote;
    private String license;
    private String rangeContext;
    private String resourceContext;
    private String xmlFile;

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

    public static Map<String, List<MetsData.Xlink>> getXlinkMap(MetsData mets) {
        final List<MetsData.Xlink> xlinks = getXlinks(mets);
        return xlinks.stream().collect(groupingBy(MetsData.Xlink::getXLinkFrom));
    }

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

    public void setLicense(final TemplateManifest body) {
        if (getRightsUrl(mets).isEmpty()) {
            body.setLicense(Collections.singletonList(license));
        } else {
            body.setLicense(getRightsUrl(mets));
        }
    }

    public void setAttribution(final TemplateManifest body) {
        // TODO HTML should be wellformed XML https://iiif.io/api/presentation/2.1/#html-markup-in-property-values
        if (getRightsValue(mets).isEmpty()) {
            body.setAttribution(
                    attributionKey + getAttribution(mets) + "<br/>" + attributionLicenseNote);
        } else {
            final StringBuilder content = new StringBuilder();
            for (String value : getRightsValue(mets)) {
                content.append(value).append("<br/>");
            }
            body.setAttribution(content.toString());
        }
    }

    public void setLogo(final TemplateManifest body) {
        body.setLogo(getLogo(mets));
    }

    public void setHandschriftMetadata(final TemplateManifest body) {
        final ManuscriptMetadata man = new ManuscriptMetadata(mets);
        final List<TemplateMetadata> info = man.getInfo();
        final List<TemplateMetadata> metadata = new ArrayList<>(info);
        body.setMetadata(metadata);
    }

    public void setHspCatalogMetadata(final TemplateManifest body) {
        final HspCatalogMetadata catalogMetadata = new HspCatalogMetadata(mets);
        final List<TemplateMetadata> info = catalogMetadata.getInfo();
        final List<TemplateMetadata> metadata = new ArrayList<>(info);
        body.setMetadata(metadata);
    }

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

    public TemplateMetadata getAnchorFileMetadata() {
        return new TemplateMetadata(anchorKey, getMultiVolumeWorkTitle(mets) + "; " + getCensus(mets));
    }

    public String getAnchorFileLabel() {
        return getMultiVolumeWorkTitle(mets) + "; " + getVolumePartTitleOrPartNumber(mets);
    }

    public List<String> getCanvases(final String logical) {
        final List<String> canvases = new ArrayList<>();
        final List<String> physicals = xlinkmap.get(logical).stream().map(MetsData.Xlink::getXLinkTo).collect(toList());
        physicals.forEach(physical -> {
            canvases.add(iriBuilder.buildCanvasIRIfromPhysical(physical));
        });
        return canvases;
    }

    public TemplateTopStructure buildTopStructure() {
        final List<String> ranges = synchronizedList(new ArrayList<>());

        final List<MetsData.Logical> logs = getTopLogicals(mets);
        logs.forEach(logical -> {
            final String rangeId = resourceContext + rangeContext + separator + logical.getLogicalId();
            ranges.add(0, rangeId);
        });

        final TemplateTopStructure st = new TemplateTopStructure();
        st.setStructureId(resourceContext + rangeContext + separator
                + MetsConstants.METS_PARENT_LOGICAL_ID);
        st.setStructureLabel("Contents");
        ranges.sort(naturalOrder());
        st.setRanges(ranges);
        return st;
    }

    public List<TemplateMetadata> buildStructureMetadata(final String logicalType) {
        final List<TemplateMetadata> metadataList = new ArrayList<>();
        final TemplateMetadata metadata = new TemplateMetadata(MetsConstants.METS_STRUCTURE_TYPE, logicalType);
        metadataList.add(metadata);
        return metadataList;
    }

    public List<TemplateStructure> buildStructures() {
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
                        final String rangeId = resourceContext + rangeContext + separator + descID;
                        final String descLabel = getLogicalLabel(mets, descID);
                        final String logType = getLogicalType(mets, descID);
                        ranges.add(0, rangeId);
                        descSt.setStructureId(rangeId);
                        descSt.setStructureLabel(descLabel);
                        if (mets.isHspCatalog()) {
                            final HspCatalogStructureMetadata hspMd = new HspCatalogStructureMetadata(mets, descID);
                            final List<TemplateMetadata> metadataList = hspMd.getInfo();
                            descSt.setMetadata(metadataList);
                        } else {
                            final List<TemplateMetadata> metadataList = buildStructureMetadata(logType);
                            descSt.setMetadata(metadataList);
                        }
                        descSt.setCanvases(getCanvases(descID));
                        descendents.add(0, descSt);
                    });
                    final TemplateStructure st = new TemplateStructure();
                    final String structureIdDesc = resourceContext + rangeContext + separator +
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
                            resourceContext + rangeContext + separator
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


    public Boolean getCatalogType() {
        return isHspCatalog(mets);
    }


    public Boolean getMtype() {
        return isManuscript(mets);
    }


    public String getUrnReference() {
        return getManuscriptIdByType(mets, MetsConstants.URN_TYPE);
    }


    public List<String> getPhysical() {
        return getPhysicalDivs(mets);
    }


    public String getOrderLabel(final String div) {
        return getOrderLabelForDiv(mets, div);
    }


    public String getFile(final String div, final String fileGrp) {
        return getFileIdForDiv(mets, div, fileGrp);
    }


    public String getHref(final String file) {
        return getHrefForFile(mets, file);
    }


    public String getFormatForFile(final String fileId) {
        return getMimeTypeForFile(mets, fileId);
    }
}
