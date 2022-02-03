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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.ubleipzig.iiifproducer.model.ImageServiceResponse;
import de.ubleipzig.iiifproducer.model.v2.Canvas;
import de.ubleipzig.iiifproducer.model.v2.Manifest;
import de.ubleipzig.iiifproducer.model.v2.PaintingAnnotation;
import de.ubleipzig.iiifproducer.model.v2.Structure;
import de.ubleipzig.iiifproducer.model.v3.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static de.ubleipzig.iiifproducer.converter.ConverterUtils.buildLabelMap;
import static de.ubleipzig.iiifproducer.converter.DomainConstants.*;
import static java.io.File.separator;
import static java.lang.String.format;
import static java.util.Optional.ofNullable;

@Builder
@Setter
@Getter
@AllArgsConstructor
@Slf4j
public class ConverterVersion3 {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String NONE = "none";
    private final Manifest manifest;

    /**
     * mapServiceResponse.
     *
     * @param res String
     * @return ImageServiceResponse
     */
    public static ImageServiceResponse mapServiceResponse(final InputStream res) {
        try {
            return MAPPER.readValue(res, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public ManifestVersion3 execute() {
        try {
            MetadataBuilderVersion3 metadataBuilderVersion3 = MetadataBuilderVersion3.builder()
                    .manifest(manifest).build();

            final List<CanvasVersion3> canvases = new ArrayList<>();
            final String viewId = new URL(manifest.getId()).getPath().split(separator)[1];

            manifest.getSequences().forEach(sq -> {
                final AtomicInteger index = new AtomicInteger(1);
                for (Canvas c : sq.getCanvases()) {
                    Integer height = null;
                    Integer width = null;
                    final CanvasVersion3 canvas = CanvasVersion3.builder().build();
                    final BodyVersion3 body = BodyVersion3.builder().build();
                    for (PaintingAnnotation i : c.getImages()) {
                        String iiifService = i.getResource().getService().getId();
                        //hack to fix service
                        if (iiifService.contains(IIPSRV_DEFAULT)) {
                            iiifService = iiifService.replace(IIPSRV_DEFAULT, "iiif");
                        }
                        if (!iiifService.contains("https")) {
                            iiifService = iiifService.replace("http", "https");
                        }

                        //getDimensionsFromImageService
                        InputStream is = null;
                        try {
                            is = new URL(iiifService + "/info.json").openStream();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        final ImageServiceResponse ir = mapServiceResponse(is);
                        height = ir.getHeight();
                        width = ir.getWidth();

                        final ServiceVersion3 service = ServiceVersion3.builder()
                                .id(iiifService)
                                .type(IIIF_SERVICE_TYPE)
                                .profile(IIIF_SERVICE_PROFILE)
                                .build();
                        final List<ServiceVersion3> services = new ArrayList<>();
                        services.add(service);

                        //createBody
                        body.setService(services);
                        body.setHeight(height);
                        body.setWidth(width);
                        body.setType("Image");
                        body.setFormat("image/jpeg");
                        String resourceId = i.getResource().getId();
                        //hack for Mirador file extension check
                        if (resourceId.contains("jpx")) {
                            final String jpgResource = resourceId.replace("jpx", "jpg");
                            body.setId(jpgResource);
                        } else {
                            body.setId(resourceId);
                        }
                        //build Body label
                        final String bodyLabel = i.getResource().getLabel();
                        final Map<String, List<String>> bodyLabelMap = buildLabelMap(bodyLabel, NONE);
                        body.setLabel(bodyLabelMap);
                    }
                    //createAnnotation
                    final String canvasId = baseUrl + viewId + separator + targetBase + separator + format(
                            "%08d", index.getAndIncrement());
                    final List<AnnotationVersion3> annotations = new ArrayList<>();
                    final AnnotationVersion3 anno = AnnotationVersion3.builder()
                            .id(baseUrl + viewId + separator + annotationBase + separator + UUID.randomUUID())
                            .type("Annotation")
                            .motivation("painting")
                            .body(body)
                            .target(canvasId)
                            .build();
                    annotations.add(anno);

                    //createAnnotationPage
                    final List<AnnotationPage> annoPages = new ArrayList<>();
                    final AnnotationPage annoPage = AnnotationPage.builder()
                            .id(baseUrl + viewId + separator + annotationPageBase + separator + UUID.randomUUID())
                            .type("AnnotationPage")
                            .items(annotations)
                            .build();
                    annoPages.add(annoPage);

                    //setCanvas
                    canvas.setId(canvasId);
                    canvas.setType("Canvas");
                    canvas.setItems(annoPages);
                    canvas.setHeight(height);
                    canvas.setWidth(width);
                    final String canvasLabel = c.getLabel();
                    final Map<String, List<String>> canvasLabelMap = buildLabelMap(canvasLabel, NONE);
                    canvas.setLabel(canvasLabelMap);
                    canvases.add(canvas);
                }
            });

            List<MetadataVersion3> finalMetadata = metadataBuilderVersion3.execute();
            ManifestVersion3 newManifest = buildManifest(viewId, canvases);

            //build structures objects
            final Optional<List<Structure>> structures = ofNullable(manifest.getStructures());
            if (structures.isPresent()) {
                final List<Structure> structs = structures.get();
                final StructureBuilderVersion3 sbuilder = StructureBuilderVersion3.builder()
                        .metadataImplVersion3(finalMetadata)
                        .structures(structs)
                        .viewId(viewId)
                        .build();
                sbuilder.buildIncrements();
                List<Item> newStructures = sbuilder.build();
                newManifest.setStructures(newStructures);
            }

            //set seeAlso
            final Optional<String> finalURN = ofNullable(getURNfromFinalMetadata(finalMetadata));
            List<String> related = (List<String>) manifest.getRelated();
            final List<SeeAlso> seeAlso = setSeeAlso(viewId, finalURN.orElse(null), related);
            newManifest.setSeeAlso(seeAlso);
            newManifest.setMetadata(finalMetadata);

            //set manifest label
            final String manifestLabel = manifest.getLabel();
            final Map<String, List<String>> manifestLabelMap = buildLabelMap(manifestLabel, NONE);
            newManifest.setLabel(manifestLabelMap);

            return newManifest;
        } catch (IOException ex) {
            throw new RuntimeException("Could not Convert Manifest", ex.getCause());
        }
    }

    public ManifestVersion3 buildManifest(final String viewId, final List<CanvasVersion3> canvases) {

        final List<String> contexts = new ArrayList<>();
        contexts.add(WEB_ANNOTATION_CONTEXT);
        contexts.add(IIIF_VERSION3_CONTEXT);

        final String id = baseUrl + viewId + separator + manifestBase + ".json";

        final List<String> behaviors = new ArrayList<>();
        behaviors.add("paged");

        final MetadataVersion3 requiredStatement = MetadataVersion3.builder().build();
        final Map<String, List<String>> label = buildLabelMap("Attribution", "en");
        final Map<String, List<String>> value = buildLabelMap(domainAttribution, "en");
        requiredStatement.setLabel(label);
        requiredStatement.setValue(value);

        final ManifestVersion3.Logo logo = ManifestVersion3.Logo.builder()
                .id(domainLogo)
                .type("Image")
                .build();

        return ManifestVersion3.builder()
                .context(contexts)
                .id(id)
                .type("Manifest")
                .viewingDirection("left-to-right")
                .behavior(behaviors)
                .rights(domainLicense)
                .requiredStatement(requiredStatement)
                .items(canvases)
                .logo(logo)
                .build();
    }

    public List<SeeAlso> setSeeAlso(final String viewId, final String urn, List<String> related) {
        final ArrayList<SeeAlso> seeAlso = new ArrayList<>();
        String katalogId = katalogUrl + urn;
        String viewerId = viewerUrl + viewId;
        if (urn != null) {
            final SeeAlso katalogReference = SeeAlso.builder()
                    .id(katalogId)
                    .format("text/html")
                    .type("Application")
                    .profile(SEE_ALSO_PROFILE)
                    .build();
            seeAlso.add(katalogReference);
        }
        final SeeAlso katalogReference = SeeAlso.builder()
                .id(viewerId)
                .format("text/html")
                .type("Application")
                .profile(SEE_ALSO_PROFILE)
                .build();
        seeAlso.add(katalogReference);
        List<String> filteredRelated = related.stream()
                .filter(r -> !katalogId.equals(r) && !viewerId.equals(r)).collect(Collectors.toList());
        filteredRelated.forEach(r -> {
            if (r.contains("json")) {
                SeeAlso sa = SeeAlso.builder()
                        .id(r)
                        .format("application/json")
                        .type("Dataset")
                        .profile(IIIF_VERSION3_CONTEXT)
                        .build();
                seeAlso.add(sa);
            } else if (r.contains("xml")) {
                SeeAlso sa = SeeAlso.builder()
                        .id(r)
                        .format("application/xml")
                        .type("Dataset")
                        .profile(METS_PROFILE)
                        .build();
                seeAlso.add(sa);
            }
        });

        return seeAlso;
    }

    public String getURNfromFinalMetadata(final List<MetadataVersion3> finalMetadata) {
        final Optional<Set<MetadataVersion3>> metaURN = Optional.of(finalMetadata.stream().filter(
                y -> y.getLabel().values().stream().anyMatch(v -> v.contains("URN"))).collect(Collectors.toSet()));
        final Optional<MetadataVersion3> urn = metaURN.get().stream().findAny();
        return urn.map(u -> u.getValue().get(NONE).get(0)).orElse(null);
    }
}
