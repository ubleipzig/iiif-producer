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

package de.ubleipzig.iiifproducer.converter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static de.ubleipzig.iiifproducer.converter.MetadataApiEnum.DISPLAYORDER;
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
                (s) -> !s.contains(DISPLAYORDER.getApiKey())).collect(Collectors.toSet());
    }

    Set<String> intersectBundles(final Set<String> labels1, final Set<String> labels2 ) {
        labels1.retainAll(labels2);
        return labels1;
    }
}
