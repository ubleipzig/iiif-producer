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

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static de.ubleipzig.iiifproducer.converter.MetadataApiEnum.*;
import static java.util.Optional.ofNullable;

public abstract class MetadataObjectTypes {
    @SuppressWarnings("unchecked")
    static Optional<List<String>> getValueAsStringList(final Map<String, Object> newMetadata, final String key) {
        final Optional<?> value = ofNullable(newMetadata.get(key));
        return value.filter(List.class::isInstance).map(List.class::cast);
    }

    @SuppressWarnings("unchecked")
    static Optional<List<Map<String, String>>> getValueAsMapList(final Map<String, Object> newMetadata,
                                                                 final String key) {
        final Optional<?> value = ofNullable(newMetadata.get(key));
        return value.filter(List.class::isInstance).map(List.class::cast);
    }

    @SuppressWarnings("unchecked")
    static Optional<Map<String, String>> getValueAsMap(final Map<String, Object> newMetadata, final String key) {
        final Optional<?> value = ofNullable(newMetadata.get(key));
        return value.filter(Map.class::isInstance).map(Map.class::cast);
    }

    static Optional<String> getValueAsString(final Map<String, Object> newMetadata, final String key) {
        final Optional<?> value = ofNullable(newMetadata.get(key));
        return value.filter(String.class::isInstance).map(String.class::cast);
    }

    Set<String> buildFilteredLabelSet(final ResourceBundle bundle) {
        final Enumeration<String> bundleLabelKeys = bundle.getKeys();
        final Stream<String> labelStream = StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(bundleLabelKeys.asIterator(), Spliterator.ORDERED), false);
        return labelStream.filter(
                (s) -> !s.contains(DISPLAYORDER.getApiKey()) && !s.contains(LANGUAGE.getApiKey()) && !s.contains(
                        COLLECTION.getApiKey()) && !s.contains(AUTHOR.getApiKey()) && !s.contains(
                        MANIFESTTYPE.getApiKey()) && !s.contains(STRUCTTYPE.getApiKey())).collect(Collectors.toSet());
    }
}
