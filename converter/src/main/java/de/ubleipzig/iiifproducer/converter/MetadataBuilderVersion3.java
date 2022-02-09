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

import de.ubleipzig.iiifproducer.model.Metadata;
import de.ubleipzig.iiifproducer.model.v2.Manifest;
import de.ubleipzig.iiifproducer.model.v2.Structure;
import de.ubleipzig.iiifproducer.model.v3.MetadataVersion3;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Builder
@Setter
@Getter
@AllArgsConstructor
@Slf4j
public class MetadataBuilderVersion3 {
    private final Manifest manifest;
    private MetadataImplVersion3 metadataImplVersion3;

    public List<MetadataVersion3> execute() {
        final Optional<List<Metadata>> metadata = ofNullable(manifest.getMetadata());
        List<Metadata> m = new ArrayList<>();
        List<Structure> s = new ArrayList<>();
        final Optional<List<Structure>> structures = ofNullable(manifest.getStructures());
        if (metadata.isPresent()) {
            m = metadata.get();
        } else if (structures.isPresent()) {
            s = structures.get();
        }
        metadataImplVersion3 = MetadataImplVersion3.builder()
                .metadata(m)
                .structures(s)
                .build();
        return metadataImplVersion3.buildFinalMetadata();
    }
}
