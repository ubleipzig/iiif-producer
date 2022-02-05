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
/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.ubleipzig.iiifproducer.converter;

import de.ubleipzig.iiifproducer.model.Metadata;
import de.ubleipzig.iiifproducer.model.v2.Structure;
import de.ubleipzig.iiifproducer.model.v3.Item;
import de.ubleipzig.iiifproducer.model.v3.MetadataVersion3;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.MultiValuedMap;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static de.ubleipzig.iiifproducer.converter.ConverterUtils.buildLabelMap;
import static de.ubleipzig.iiifproducer.converter.ConverterUtils.buildPaddedCanvases;
import static de.ubleipzig.iiifproducer.converter.DomainConstants.baseUrl;
import static de.ubleipzig.iiifproducer.converter.DomainConstants.structureBase;
import static de.ubleipzig.iiifproducer.converter.MetadataApiEnum.DISPLAYORDER;
import static de.ubleipzig.iiifproducer.converter.MetadataApiEnum.STRUCTTYPE;
import static de.ubleipzig.iiifproducer.converter.MetadataImplVersion3.DEUTSCH;
import static de.ubleipzig.iiifproducer.converter.MetadataImplVersion3.ENGLISH;
import static de.ubleipzig.iiifproducer.converter.MetadataImplVersion3.PERIOD;
import static de.ubleipzig.iiifproducer.converter.MetadataImplVersion3.deutschLabels;
import static de.ubleipzig.iiifproducer.converter.MetadataImplVersion3.englishLabels;
import static java.io.File.separator;
import static java.util.Optional.ofNullable;

@Builder
@Setter
@Getter
@AllArgsConstructor
public class StructureBuilderVersion3 {
    private final List<Structure> structures;
    private final String viewId;
    private final Map<String, String> backReferenceMap = new HashMap<>();
    MetadataImplVersion3 metadataImplVersion3;
    Set<String> enFilteredLabels;
    Set<String> deFilteredLabels;

    public void buildIncrements() {
        final AtomicInteger ai = new AtomicInteger(0);
        structures.forEach(s -> {
            final Optional<List<String>> cs = ofNullable(s.getCanvases());

            if (cs.isPresent()) {
                final List<String> paddedCanvases = buildPaddedCanvases(cs.get(), viewId);
                if (!paddedCanvases.isEmpty()) {
                    s.setCanvases(paddedCanvases);
                }
            }

            final String structureId = s.getId();
            if (!structureId.contains("LOG") || !structureId.contains("r0")) {
                if (ai.get() == 0) {
                    final String newStructureId = baseUrl + viewId + separator + structureBase + separator + "LOG_0000";
                    backReferenceMap.put(s.getId(), newStructureId);
                    //unset within (fix for early manifests)
                    s.setWithin(null);
                    ai.getAndIncrement();
                } else {
                    final String newStructureId =
                            baseUrl + viewId + separator + structureBase + separator + "LOG_" + String.format(
                                    "%04d", ai.getAndIncrement());
                    backReferenceMap.put(s.getId(), newStructureId);
                    //unset within (fix for early manifests)
                    s.setWithin(null);
                }
            }
        });
    }

    public List<Item> build() {
        metadataImplVersion3 = MetadataImplVersion3.builder().build();
        enFilteredLabels = metadataImplVersion3.buildFilteredLabelSet(englishLabels);
        deFilteredLabels = metadataImplVersion3.buildFilteredLabelSet(deutschLabels);
        final List<Item> newStructures = new ArrayList<>();
        for (Structure struct : structures) {
            final Item newStructure = Item.builder().build();
            final Object structureLabel = struct.getLabel();
            final Map<String, List<String>> labelMap = buildLabelMap((String) structureLabel, "de");
            newStructure.setLabel(labelMap);
            final Optional<List<String>> fr = ofNullable(struct.getRanges());
            final List<Item> newRanges = new ArrayList<>();
            final List<Item> newCanvases = new ArrayList<>();
            final Optional<List<String>> canvases = ofNullable(struct.getCanvases());
            canvases.ifPresent(cs -> cs.forEach(c -> {
                final Item newCanvas = Item.builder()
                        .id(c)
                        .type("Canvas")
                        .build();
                newCanvases.add(newCanvas);
            }));

            if (fr.isPresent()) {
                for (String r1 : fr.get()) {
                    final Optional<String> newRange = ofNullable(backReferenceMap.get(r1));
                    newRange.ifPresent(r -> {
                        String sId = null;
                        try {
                            sId = new URL(r).getPath().split(separator)[3];
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        final Item nr = Item.builder()
                                .id(r)
                                .type("Range")
                                .build();
                        final Optional<List<MetadataVersion3>> structureMetadata = ofNullable(
                                buildStructureMetadataForId(sId));
                        if (structureMetadata.isPresent()) {
                            List<MetadataVersion3> m = structureMetadata.get();
                            if (!m.isEmpty()) {
                                nr.setMetadata(m);
                            }
                        }
                        newRanges.add(nr);
                    });
                }
            }
            final Stream<Item> combinedItems = Stream.concat(newRanges.stream(), newCanvases.stream());
            final List<Item> newItems = combinedItems.collect(Collectors.toList());
            newStructure.setItems(newItems);
            final String structId = struct.getId();
            final String newStructId = backReferenceMap.get(structId);
            newStructure.setId(newStructId);
            final String sId;
            try {
                sId = new URL(newStructId).getPath().split(separator)[3];
               final Optional<List<MetadataVersion3>> structureMetadata = ofNullable(
                        buildStructureMetadataForId(sId));
                if (structureMetadata.isPresent()) {
                    List<MetadataVersion3> m = structureMetadata.get();
                    if (!m.isEmpty()) {
                        structureMetadata.ifPresent(newStructure::setMetadata);
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            newStructures.add(newStructure);
        }
        //finally merge all substructures into top structure
        final Optional<Item> topStructure = newStructures.stream().filter(
                s -> s.getId().contains("LOG_0000")).findAny();
        if (topStructure.isPresent()) {
            final List<Item> topStructureItems = topStructure.get().getItems();
            topStructureItems.forEach(ti -> {
                final Optional<Item> item = newStructures.stream().filter(
                        s -> s.getId().contains(ti.getId())).findAny();
                if (item.isPresent()) {
                    final Map<String, List<String>> label = item.get().getLabel();
                    ti.setLabel(label);
                    Optional<List<MetadataVersion3>> meta = ofNullable(ti.getMetadata());
                    if (meta.isPresent()) {
                        List<MetadataVersion3> m = meta.get();
                        if (!m.isEmpty()) {
                            meta.ifPresent(ti::setMetadata);
                        }
                    }
                    final List<Item> items = item.get().getItems();
                    final List<Item> ranges = items.stream().filter(t -> t.getType().equals("Range")).collect(
                            Collectors.toList());
                    if (ranges.isEmpty()) {
                        ti.setItems(items);
                    } else {
                        final List<Item> subItems = new ArrayList<>();
                        ranges.forEach(si -> {
                            final Item subItem = Item.builder().build();
                            final String siId = si.getId();
                            final Map<String, List<String>> labelMap = si.getLabel();
                            subItem.setLabel(labelMap);
                            subItem.setId(siId);
                            Optional<List<MetadataVersion3>> meta2 = ofNullable(si.getMetadata());
                            meta2.ifPresent(subItem::setMetadata);
                            final List<Item> subRange = newStructures.stream().filter(
                                    i -> i.getId().equals(siId)).collect(Collectors.toList());
                            final List<Item> subRangeCanvases = subRange.get(0).getItems();
                            subItem.setItems(subRangeCanvases);
                            subItems.add(subItem);
                        });
                        ti.setItems(subItems);
                    }
                }
            });
            final List<Item> finalStructure = new ArrayList<>();
            final Item top = topStructure.get();
            top.setType("Range");
            finalStructure.add(top);
            return finalStructure;
        }
        return null;
    }

    public List<MetadataVersion3> buildStructureMetadataForId(final String structId) {
        final Optional<List<Structure>> filteredSubList = Optional.of(
                structures.stream().filter(s -> s.getId().contains(structId)).collect(Collectors.toList()));
        List<MetadataVersion3> finalMetadata = new ArrayList<>();
        filteredSubList.ifPresent(maps -> maps.forEach(sm -> {
            final Optional<List<Metadata>> metadata = ofNullable(sm.getMetadata());
            if (metadata.isPresent()) {
                MultiValuedMap<String, String> newMetadata = metadataImplVersion3.convertListToMap(sm.getMetadata());
                List<MetadataVersion3> em = buildFilteredLabelMetadata(newMetadata, enFilteredLabels, englishLabels);
                List<MetadataVersion3> dm = buildFilteredLabelMetadata(newMetadata, deFilteredLabels, deutschLabels);
                finalMetadata.addAll(em);
                finalMetadata.addAll(dm);
            }
        }));
        return finalMetadata;
    }

    List<MetadataVersion3> addMetadataObject(final String language,
                                             final MultiValuedMap<String, String> newMetadata,
                                             final String key, final String label, final Integer displayOrder) {
        final List<MetadataVersion3> metadata = new ArrayList<>();
        List<String> values = new ArrayList<>(newMetadata.get(label));
        final MetadataVersion3 m = metadataImplVersion3.buildMetadata(language, label, values, displayOrder);
        if (m != null && !m.getValue().isEmpty()) {
            metadata.add(m);
        }
        return metadata;
    }

    List<MetadataVersion3> buildFilteredLabelMetadata(final MultiValuedMap<String, String> newMetadata,
                                  final Set<String> filteredLabels,
                                  final ResourceBundle bundle) {
        List<MetadataVersion3> metadata = new ArrayList<>();
        for (String l : filteredLabels) {
            final String displayLabel = bundle.getString(l);
            final String displayOrderKey = l + PERIOD + DISPLAYORDER.getApiKey();
            final Integer displayOrder = Integer.valueOf(bundle.getString(displayOrderKey));
            final String languageTag = bundle.getLocale().toLanguageTag();
            if (languageTag.equals(ENGLISH)) {
                metadata.addAll(addMetadataObject(ENGLISH, newMetadata, l, displayLabel, displayOrder));
            } else if (languageTag.equals(DEUTSCH)) {
                metadata.addAll(addMetadataObject(DEUTSCH, newMetadata, l, displayLabel, displayOrder));
            }
        }
        return metadata;
    }
}
