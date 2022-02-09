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
import de.ubleipzig.iiifproducer.model.v3.MetadataVersion3;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

import static de.ubleipzig.iiifproducer.converter.DomainConstants.DEUTSCH;
import static de.ubleipzig.iiifproducer.converter.DomainConstants.ENGLISH;
import static de.ubleipzig.iiifproducer.converter.DomainConstants.NONE;
import static de.ubleipzig.iiifproducer.converter.DomainConstants.PERIOD;
import static de.ubleipzig.iiifproducer.converter.MetadataApiEnum.DISPLAYORDER;
import static de.ubleipzig.iiifproducer.converter.MetadataApiEnum.MANIFESTTYPE;
import static de.ubleipzig.iiifproducer.converter.MetadataApiEnum.MANUSCRIPT;

@Builder
@Setter
@Getter
@AllArgsConstructor
@Slf4j
public class MetadataImplVersion3 extends MetadataObjectTypes {
    static final ResourceBundle deutschLabels = ResourceBundle.getBundle("metadataLabels", Locale.GERMAN);
    static final ResourceBundle englishLabels = ResourceBundle.getBundle("metadataLabels", Locale.ENGLISH);

    List<Metadata> metadata;
    List<Structure> structures;
    MetadataBuilderVersion3 metadataBuilder;
    private List<MetadataVersion3> finalMetadata;

    MetadataVersion3 buildMetadata(final Map<String, List<String>> languageLabels, List<String> values,
                                   final Integer displayOrder) {
        if (!values.isEmpty()) {
            final MetadataVersion3 metadataV3 = MetadataVersion3.builder().build();
            metadataV3.setLabel(languageLabels);
            final Map<String, List<String>> valuesMap = new HashMap<>();
            valuesMap.put(NONE, values);
            metadataV3.setValue(valuesMap);
            metadataV3.setDisplayOrder(displayOrder);
            return metadataV3;
        } else {
            return null;
        }
    }

    public MultiValuedMap<String, String> convertListToMap(List<Metadata> metadata) {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
        metadata.stream()
                .filter(m -> m.getValue() != null && !StringUtils.isBlank(m.getValue().toString())
                        && !m.getValue().toString().startsWith("\""))
                .forEach(m -> {
                    map.put((String) m.getLabel(), (String) m.getValue());
                });
        return map;
    }

    public List<MetadataVersion3> buildFinalMetadata() {
        MultiValuedMap<String, String> newMetadata = convertListToMap(metadata);
        finalMetadata = new ArrayList<>();

        final String manifestTypeKey = MANIFESTTYPE.getApiKey();
        final List<String> manifestTypeList = new ArrayList<>(newMetadata.get("Manifest Type"));
        String manifestType = manifestTypeList.stream().findFirst().orElse(null);
        if (manifestType != null) {
            final String manifestTypeLabelDisplayOrderKey = manifestTypeKey + PERIOD + DISPLAYORDER.getApiKey();
            final Integer displayOrder = Integer.valueOf(englishLabels.getString(manifestTypeLabelDisplayOrderKey));
            if (manifestType.equals(MANUSCRIPT.getApiKey())) {
                List<String> values = new ArrayList<>(newMetadata.get("Objekttitel"));
                Map<String, List<String>> labelMap = new HashMap<>();
                labelMap.put(DEUTSCH, Collections.singletonList("Objekttitel"));
                final MetadataVersion3 m = buildMetadata(labelMap, values, displayOrder);
                if (m != null && !m.getValue().isEmpty()) {
                    finalMetadata.add(m);
                }
            } else {
                List<String> values = new ArrayList<>(newMetadata.get("Subtitle"));
                Map<String, List<String>> labelMap = new HashMap<>();
                labelMap.put(DEUTSCH, Collections.singletonList("Subtitle"));
                final MetadataVersion3 m = buildMetadata(labelMap, values, displayOrder);
                if (m != null && !m.getValue().isEmpty()) {
                    finalMetadata.add(m);
                }
            }
        }

        buildFilteredLabelMetadata(newMetadata, englishLabels);
        buildFilteredLabelMetadata(newMetadata, deutschLabels);
        finalMetadata.sort(Comparator.comparing(MetadataVersion3::getDisplayOrder));
        return finalMetadata;
    }

    void buildFilteredLabelMetadata(final MultiValuedMap<String, String> newMetadata,
                                    final ResourceBundle bundle) {
        final Set<String> enFilteredLabelKeys = buildFilteredLabelSet(englishLabels);
        final Set<String> deFilteredLabelKeys = buildFilteredLabelSet(deutschLabels);
        //defaults to DEUTSCH primary
        if (DEUTSCH.equals(bundle.getLocale().toLanguageTag())) {
            for (String labelKey : deFilteredLabelKeys) {
                final Map<String, List<String>> labelMap = new HashMap<>();
                final String displayLabel = bundle.getString(labelKey);
                final String displayOrderKey = labelKey + PERIOD + DISPLAYORDER.getApiKey();
                final Integer displayOrder = Integer.valueOf(bundle.getString(displayOrderKey));
                Optional<Set<String>> enlabelSet =
                        Optional.of(enFilteredLabelKeys.stream().filter(en -> en.contains(labelKey)).collect(Collectors.toSet()));
                final String enLabel = enlabelSet.get().stream().findFirst().orElse(null);
                if (enLabel != null) {
                    final Optional<String> englishDisplayLabel = Optional.of(englishLabels.getString(enLabel));
                    englishDisplayLabel.ifPresent(s -> labelMap.put(ENGLISH, Collections.singletonList(s)));
                }
                labelMap.put(DEUTSCH, Collections.singletonList(displayLabel));
                List<String> values = new ArrayList<>(newMetadata.get(displayLabel));
                final MetadataVersion3 m = buildMetadata(labelMap, values, displayOrder);
                if (m != null && !m.getValue().isEmpty()) {
                    finalMetadata.add(m);
                }
            }
        } else {
            for (String labelKey : enFilteredLabelKeys) {
                final Map<String, List<String>> labelMap = new HashMap<>();
                final String displayLabel = bundle.getString(labelKey);
                final String displayOrderKey = labelKey + PERIOD + DISPLAYORDER.getApiKey();
                final Integer displayOrder = Integer.valueOf(bundle.getString(displayOrderKey));
                Optional<Set<String>> delabelSet =
                        Optional.of(deFilteredLabelKeys.stream().filter(en -> en.contains(labelKey)).collect(Collectors.toSet()));
                final String deLabel = delabelSet.get().stream().findFirst().orElse(null);
                if (deLabel != null) {
                    final Optional<String> deutschDisplayLabel = Optional.of(
                            deutschLabels.getString(deLabel));
                    deutschDisplayLabel.ifPresent(s -> labelMap.put(DEUTSCH, Collections.singletonList(s)));
                }
                labelMap.put(ENGLISH, Collections.singletonList(displayLabel));
                List<String> values = new ArrayList<>(newMetadata.get(displayLabel));
                final MetadataVersion3 m = buildMetadata(labelMap, values, displayOrder);
                if (m != null && !m.getValue().isEmpty()) {
                    finalMetadata.add(m);
                }
            }
        }
    }
}
