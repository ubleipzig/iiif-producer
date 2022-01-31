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
import de.ubleipzig.iiifproducer.model.v2.Manifest;
import de.ubleipzig.iiifproducer.model.v3.MetadataVersion3;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collections;
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
        if (metadata.isPresent()) {
            List<Metadata> harmonizedMetadata = harmonizeIdentifierLabels(metadata.get());
            final Optional<Metadata> metaURN = harmonizedMetadata.stream().filter(
                    y -> y.getLabel().equals("URN")).findAny();
            final Optional<Metadata> metaPPN = harmonizedMetadata.stream().filter(
                    y -> y.getLabel().equals("Source PPN (SWB)")).findAny();
            metadataImplVersion3 = MetadataImplVersion3.builder().metadata(metadata.get()).build();
            return metadataImplVersion3.buildFinalMetadata();
        }
        return Collections.emptyList();
    }

    public List<Metadata> harmonizeIdentifierLabels(final List<Metadata> metadata) {
        metadata.forEach(m -> {
            final Optional<?> label = ofNullable(m.getLabel());
            final Optional<String> labelString = label.filter(String.class::isInstance).map(String.class::cast);
            labelString.ifPresent(l -> {
                switch (l) {
                    case "urn":
                        m.setLabel("URN");
                        break;
                    case "swb-ppn":
                        m.setLabel("Source PPN (SWB)");
                        break;
                    default:
                        break;
                }
            });
        });
        return metadata;
    }
}
