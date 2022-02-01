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
import de.ubleipzig.iiifproducer.model.v3.MetadataVersion3;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static de.ubleipzig.iiifproducer.converter.MetadataApiEnum.*;
import static java.util.Optional.ofNullable;

@Builder
@Setter
@Getter
@AllArgsConstructor
@Slf4j
public class MetadataImplVersion3 extends MetadataObjectTypes {
    List<Metadata> metadata;
    MetadataBuilderVersion3 metadataBuilder;

    private static final ResourceBundle deutschLabels = ResourceBundle.getBundle("metadataLabels", Locale.GERMAN);
    private static final ResourceBundle englishLabels = ResourceBundle.getBundle("metadataLabels", Locale.ENGLISH);
    private static final String PERIOD = ".";
    private static final String ENGLISH = "en";
    private static final String DEUTSCH = "de";
    private static final String NONE = "none";
    private List<MetadataVersion3> finalMetadata;

    private MetadataVersion3 buildMetadata(final String language, final String label, final String value,
                                           final Integer displayOrder) {
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

        final List<String> values = new ArrayList<>();
        values.add(value);
        final Map<String, List<String>> valuesMap = new HashMap<>();
        valuesMap.put(NONE, values);
        metadataV3.setValue(valuesMap);
        metadataV3.setDisplayOrder(displayOrder);
        return metadataV3;
    }

    public Map<String, Object> convertListToMap(List<Metadata> metadata) {
        return metadata.stream()
                .collect(Collectors.toMap(l -> (String) l.getLabel(), Metadata::getValue));
    }

    private List<MetadataVersion3> buildAuthor(List<MetadataVersion3> mList, Map<String, String> authorMap) {
        final Optional<String> gnd = ofNullable(authorMap.get(GND.getApiKey()));
        final String authorKey = AUTHOR.getApiKey();
        final String authorLabel = englishLabels.getString(authorKey);
        final String displayOrderKey = authorKey + PERIOD + DISPLAYORDER.getApiKey();
        final Integer authorLabelDisplayOrder = Integer.valueOf(englishLabels.getString(displayOrderKey));
        if (gnd.isPresent()) {
            final String author = authorMap.get(LABEL.getApiKey());
            final String authorValue = author + " [" + gnd.get() + "]";
            final MetadataVersion3 m1 = buildMetadata(ENGLISH, authorLabel, authorValue, authorLabelDisplayOrder);
            mList.add(m1);
        } else {
            final String authorValue = authorMap.get(LABEL.getApiKey());
            final MetadataVersion3 m1 = buildMetadata(ENGLISH, authorLabel, authorValue, authorLabelDisplayOrder);
            mList.add(m1);
        }
        return mList;
    }

    private List<MetadataVersion3> addMetadataStringOrList(final String language,
                                                           final Map<String, Object> newMetadata, final String key,
                                                           final String displayLabel, Integer displayOrder) {
        final List<MetadataVersion3> mList = new ArrayList<>();
        if (getValueAsString(newMetadata, key).isPresent()) {
            final String value = getValueAsString(newMetadata, key).get();
            final MetadataVersion3 m1 = buildMetadata(language, displayLabel, value, displayOrder);
            mList.add(m1);
        } else if (getValueAsStringList(newMetadata, key).isPresent()) {
            final List<String> values = getValueAsStringList(newMetadata, key).get();
            values.forEach(v -> {
                final MetadataVersion3 m1 = buildMetadata(language, displayLabel, v, displayOrder);
                mList.add(m1);
            });
        }
        return mList;
    }

/*    private List<MetadataVersion3> setCollections() {
        final String collectionKey = COLLECTION.getApiKey();
        final String collectionLabel = englishLabels.getString(collectionKey);
        final String displayOrderKey = collectionKey + PERIOD + DISPLAYORDER.getApiKey();
        final Integer collectionLabelOrder = Integer.valueOf(englishLabels.getString(displayOrderKey));
        final Map<String, Object> newMetadata = metsMods.getMetadata();
        return addMetadataStringOrList(ENGLISH, newMetadata, collectionKey, collectionLabel, collectionLabelOrder);
    }*/

/*    private List<MetadataVersion3> setLanguages() {
        final String languageKey = LANGUAGE.getApiKey();
        final String languageLabel = englishLabels.getString(languageKey);
        final String displayOrderKey = languageKey + PERIOD + DISPLAYORDER.getApiKey();
        final Integer languageLabelOrder = Integer.valueOf(englishLabels.getString(displayOrderKey));
        final Map<String, Object> newMetadata = metsMods.getMetadata();
        return addMetadataStringOrList(ENGLISH, newMetadata, languageKey, languageLabel, languageLabelOrder);
    }*/

    private List<MetadataVersion3> addMetadataObject(final String language, final Map<String, Object> newMetadata,
                                                     final String key, final String label, final Integer displayOrder) {
        final Optional<String> value = getValueAsString(newMetadata, label);
        if (value.isPresent()) {
            final MetadataVersion3 m = buildMetadata(language, label, value.get(), displayOrder);
            finalMetadata.add(m);
        }
        return finalMetadata;
    }

    public List<MetadataVersion3> buildFinalMetadata() {
        Map<String, Object> newMetadata = convertListToMap(metadata);
        //final List<MetadataVersion3> authors = buildMetadata(newMetadata);
//        final List<MetadataVersion3> collections = setCollections();
//        final List<MetadataVersion3> languages = setLanguages();
          finalMetadata = new ArrayList<>();
//        finalMetadata.addAll(collections);
//        finalMetadata.addAll(languages);

        final String manifestTypeKey = MANIFESTTYPE.getApiKey();
        final Optional<String> manifestType = getValueAsString(newMetadata, "Manifest Type");

        if (manifestType.isPresent()) {
            final String manifestTypeLabel = englishLabels.getString(manifestTypeKey);
            final String manifestTypeLabelDisplayOrderKey = manifestTypeKey + PERIOD + DISPLAYORDER.getApiKey();
            final Integer displayOrder = Integer.valueOf(englishLabels.getString(manifestTypeLabelDisplayOrderKey));
            final MetadataVersion3 manifestTypeObj = buildMetadata(
                    ENGLISH, manifestTypeLabel, manifestType.get(), displayOrder);
            finalMetadata.add(manifestTypeObj);
            if (manifestType.get().equals(MANUSCRIPT.getApiKey())) {
                //hack for UBL mets/mods classification confusion
                finalMetadata = addMetadataObject(DEUTSCH, newMetadata, SUBTITLE.getApiKey(), "Objekttitel", 3);
            } else {
                finalMetadata = addMetadataObject(ENGLISH, newMetadata, SUBTITLE.getApiKey(), "Subtitle", 3);
                //only show Physical Dimension for Non-manuscripts
                finalMetadata = addMetadataObject(
                        ENGLISH, newMetadata, PHYSICAL_DESCRIPTION.getApiKey(), "Physical Description", 10);
            }
        }

        final Set<String> enFilteredLabels = buildFilteredLabelSet(englishLabels);
        final Set<String> deFilteredLabels = buildFilteredLabelSet(deutschLabels);
        setFilteredLabelMetadata(newMetadata, enFilteredLabels, englishLabels);
        setFilteredLabelMetadata(newMetadata, deFilteredLabels, deutschLabels);
        finalMetadata.sort(Comparator.comparing(MetadataVersion3::getDisplayOrder));
        return finalMetadata;
    }

    private void setFilteredLabelMetadata(final Map<String, Object> newMetadata, final Set<String> filteredLabels,
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

/*    @Override
    public List<MetadataVersion3> buildStructureMetadataForId(final String structId) {
        final List<MetadataVersion3> mList = new ArrayList<>();
        final String authorKey = AUTHOR.getApiKey();
        final String authorNameKey = LABEL.getApiKey();
        final String authorLabel = englishLabels.getString(authorKey);
        final String displayOrderKey = authorKey + PERIOD + DISPLAYORDER.getApiKey();
        final Integer authorLabelDisplayOrder = Integer.valueOf(englishLabels.getString(displayOrderKey));
        final List<Map<String, Object>> structureMetadata = metsMods.getStructures();
        final Optional<List<Map<String, Object>>> filteredSubList = Optional.of(
                structureMetadata.stream().filter(s -> s.containsValue(structId)).collect(Collectors.toList()));
        filteredSubList.ifPresent(maps -> maps.forEach(sm -> {
            if (getValueAsMap(sm, authorKey).isPresent()) {
                final Map<String, String> authorMap = getValueAsMap(sm, authorKey).get();
                final Optional<String> gnd = ofNullable(authorMap.get(GND.getApiKey()));
                if (gnd.isPresent()) {
                    final String author = authorMap.get(authorNameKey);
                    final String authorValue = author + " [" + gnd.get() + "]";
                    final MetadataVersion3 m1 = buildMetadata(
                            ENGLISH, authorLabel, authorValue, authorLabelDisplayOrder);
                    mList.add(m1);
                } else {
                    final String authorValue = authorMap.get(authorNameKey);
                    final MetadataVersion3 m1 = buildMetadata(
                            ENGLISH, authorLabel, authorValue, authorLabelDisplayOrder);
                    mList.add(m1);
                }
            }
            final String structureTypeKey = STRUCTTYPE.getApiKey();
            final Optional<String> structureType = getValueAsString(sm, structureTypeKey);
            if (structureType.isPresent()) {
                final String structureTypeLabel = englishLabels.getString(structureTypeKey);
                final MetadataVersion3 structureTypeObj = buildMetadata(
                        ENGLISH, structureTypeLabel, structureType.get(), 1);
                mList.add(structureTypeObj);
            }
        }));
        if (!mList.isEmpty()) {
            return mList;
        } else {
            return null;
        }
    }*/
}
