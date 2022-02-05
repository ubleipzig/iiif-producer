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

import static de.ubleipzig.iiifproducer.converter.MetadataApiEnum.DISPLAYORDER;
import static de.ubleipzig.iiifproducer.converter.MetadataApiEnum.MANIFESTTYPE;
import static de.ubleipzig.iiifproducer.converter.MetadataApiEnum.MANUSCRIPT;
import static de.ubleipzig.iiifproducer.converter.MetadataApiEnum.STRUCTTYPE;
import static de.ubleipzig.iiifproducer.converter.MetadataApiEnum.SUBTITLE;

@Builder
@Setter
@Getter
@AllArgsConstructor
@Slf4j
public class MetadataImplVersion3 extends MetadataObjectTypes {
    static final ResourceBundle deutschLabels = ResourceBundle.getBundle("metadataLabels", Locale.GERMAN);
    static final ResourceBundle englishLabels = ResourceBundle.getBundle("metadataLabels", Locale.ENGLISH);
    static final String PERIOD = ".";
    static final String ENGLISH = "en";
    static final String DEUTSCH = "de";
    static final String NONE = "none";
    List<Metadata> metadata;
    List<Structure> structures;
    MetadataBuilderVersion3 metadataBuilder;
    private List<MetadataVersion3> finalMetadata;

    MetadataVersion3 buildMetadata(final String language, final String label, List<String> values,
                                           final Integer displayOrder) {
        if (!values.isEmpty()) {
            final MetadataVersion3 metadataV3 = MetadataVersion3.builder().build();

            final List<String> labels = new ArrayList<>();
            labels.add(label);
            Map<String, List<String>> labelsMap = new HashMap<>();
            if (language.equals(ENGLISH)) {
                labelsMap.put(ENGLISH, labels);
            } else if (language.equals(DEUTSCH)) {
                labelsMap.put(DEUTSCH, labels);
            }
            metadataV3.setLabel(labelsMap);

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

    private List<MetadataVersion3> addMetadataObject(final String language,
                                                     final MultiValuedMap<String, String> newMetadata,
                                                     final String key, final String label, final Integer displayOrder) {
        List<String> values = new ArrayList<>(newMetadata.get(label));
        final MetadataVersion3 m = buildMetadata(language, label, values, displayOrder);
        if (m != null && !m.getValue().isEmpty()) {
            finalMetadata.add(m);
        }
        return finalMetadata;
    }

    public List<MetadataVersion3> buildFinalMetadata() {
        MultiValuedMap<String, String> newMetadata = convertListToMap(metadata);
        finalMetadata = new ArrayList<>();

        final String manifestTypeKey = MANIFESTTYPE.getApiKey();
        final List<String> manifestTypeList = new ArrayList<>(newMetadata.get("Manifest Type"));
        String manifestType = manifestTypeList.stream().findFirst().orElse(null);
        if (manifestType != null) {
            final String manifestTypeLabel = englishLabels.getString(manifestTypeKey);
            final String manifestTypeLabelDisplayOrderKey = manifestTypeKey + PERIOD + DISPLAYORDER.getApiKey();
            final Integer displayOrder = Integer.valueOf(englishLabels.getString(manifestTypeLabelDisplayOrderKey));
            final MetadataVersion3 manifestTypeObj = buildMetadata(
                    ENGLISH, manifestTypeLabel, manifestTypeList, displayOrder);
            finalMetadata.add(manifestTypeObj);
            if (manifestType.equals(MANUSCRIPT.getApiKey())) {
                finalMetadata = addMetadataObject(DEUTSCH, newMetadata, SUBTITLE.getApiKey(), "Objekttitel", 3);
            } else {
                finalMetadata = addMetadataObject(ENGLISH, newMetadata, SUBTITLE.getApiKey(), "Subtitle", 3);
            }
        }

        final Set<String> enFilteredLabels = buildFilteredLabelSet(englishLabels);
        final Set<String> deFilteredLabels = buildFilteredLabelSet(deutschLabels);
        setFilteredLabelMetadata(newMetadata, enFilteredLabels, englishLabels);
        setFilteredLabelMetadata(newMetadata, deFilteredLabels, deutschLabels);
        finalMetadata.sort(Comparator.comparing(MetadataVersion3::getDisplayOrder));
        return finalMetadata;
    }

    private void setFilteredLabelMetadata(final MultiValuedMap<String, String> newMetadata,
                                          final Set<String> filteredLabels,
                                          final ResourceBundle bundle) {
        filteredLabels.forEach(l -> {
            final String displayLabel = bundle.getString(l);
            final String displayOrderKey = l + PERIOD + DISPLAYORDER.getApiKey();
            final Integer displayOrder = Integer.valueOf(bundle.getString(displayOrderKey));
            final String languageTag = bundle.getLocale().toLanguageTag();
            if (languageTag.equals(ENGLISH)) {
                finalMetadata = addMetadataObject(ENGLISH, newMetadata, l, displayLabel, displayOrder);
            } else if (languageTag.equals(DEUTSCH)) {
                finalMetadata = addMetadataObject(DEUTSCH, newMetadata, l, displayLabel, displayOrder);
            }
        });
    }
}
