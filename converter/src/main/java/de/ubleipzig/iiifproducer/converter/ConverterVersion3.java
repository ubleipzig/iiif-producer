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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static de.ubleipzig.iiifproducer.converter.ConverterUtils.buildLabelMap;
import static de.ubleipzig.iiifproducer.converter.DomainConstants.*;
import static de.ubleipzig.iiifproducer.model.v3.TypeConstants.*;
import static java.io.File.separator;
import static java.lang.String.format;
import static java.util.Optional.ofNullable;

@Builder
@Setter
@Getter
@AllArgsConstructor
@Slf4j
public class ConverterVersion3 {

    private final Manifest manifest;

    public ManifestVersion3 execute() {
        try {

            final List<CanvasVersion3> canvases = new ArrayList<>();
            final String viewId = new URL(manifest.getId()).getPath().split(separator)[1];

            manifest.getSequences().forEach(sq -> {
                final AtomicInteger index = new AtomicInteger(1);
                for (Canvas c : sq.getCanvases()) {
                    Integer height = null;
                    Integer width = null;

                    final BodyVersion3 body = BodyVersion3.builder().build();
                    for (PaintingAnnotation i : c.getImages()) {
                        String iiifService = i.getResource().getService().getId();
                        //fix service
                        if (iiifService.contains(IIPSRV_DEFAULT)) {
                            iiifService = iiifService.replace(IIPSRV_DEFAULT, "iiif");
                        }
                        if (!iiifService.contains("https")) {
                            iiifService = iiifService.replace("http", "https");
                        }

                        height = i.getResource().getHeight();
                        width = i.getResource().getWidth();

                        final ServiceVersion3 service = ServiceVersion3.builder()
                                .id(iiifService)
                                .type(IIIF_SERVICE_TYPE)
                                .profile(IIIF_SERVICE_PROFILE)
                                .build();
                        final List<ServiceVersion3> services = List.of(service);

                        //createBody
                        body.setService(services);
                        body.setHeight(height);
                        body.setWidth(width);
                        body.setType(IMAGE);
                        body.setFormat(IMAGE_JPEG);
                        String resourceId = i.getResource().getId();
                        //fix for Mirador file extension check
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

                    final AnnotationVersion3 anno = AnnotationVersion3.builder()
                            .id(baseUrl + viewId + separator + annotationBase + separator + UUID.randomUUID())
                            .type(ANNOTATION)
                            .motivation(PAINTING)
                            .body(body)
                            .target(canvasId)
                            .build();
                    final List<AnnotationVersion3> annotations = List.of(anno);

                    //createAnnotationPage
                    final AnnotationPage annoPage = AnnotationPage.builder()
                            .id(baseUrl + viewId + separator + annotationPageBase + separator + UUID.randomUUID())
                            .type(ANNOTATION_PAGE)
                            .items(annotations)
                            .build();
                    final List<AnnotationPage> annoPages = List.of(annoPage);

                    //setCanvas
                    final String canvasLabel = c.getLabel();
                    final Map<String, List<String>> canvasLabelMap = buildLabelMap(canvasLabel, NONE);
                    final Optional<de.ubleipzig.iiifproducer.model.v2.SeeAlso> canvasSeeAlso = ofNullable(c.getSeeAlso());
                    SeeAlso canvasSeeAlsoV3 = null;
                    if (canvasSeeAlso.isPresent()) {
                        canvasSeeAlsoV3 = SeeAlso.builder()
                                .format(canvasSeeAlso.get().getFormat())
                                .id(canvasSeeAlso.get().getId())
                                .type(DATASET)
                                .profile(canvasSeeAlso.get().getProfile())
                                .build();
                    }
                    final CanvasVersion3 canvas = CanvasVersion3.builder()
                            .height(height)
                            .id(canvasId)
                            .items(annoPages)
                            .label(canvasLabelMap)
                            .seeAlso(canvasSeeAlsoV3)
                            .type(CANVAS)
                            .width(width)
                            .build();
                    canvases.add(canvas);
                }
            });

            return buildManifest(viewId, canvases);
        } catch (IOException ex) {
            throw new RuntimeException("Could not Convert Manifest", ex.getCause());
        }
    }

    public ManifestVersion3 buildManifest(final String viewId, final List<CanvasVersion3> canvases) {

        final ConverterUtils converterUtils = ConverterUtils.builder().build();
        MetadataBuilderVersion3 metadataBuilderVersion3 = MetadataBuilderVersion3.builder()
                .manifest(manifest).build();

        // build Contexts
        final List<String> contexts = List.of(WEB_ANNOTATION_CONTEXT, IIIF_VERSION3_CONTEXT);

        // build id
        final String id = baseUrl + viewId + separator + manifestBase + ".json";

        // build Behaviors
        final List<String> behaviors = List.of(PAGED);

        // build Required Statement
        final MetadataVersion3 requiredStatement = converterUtils.buildRequiredStatement(manifest);

        //build Rights (use first match)
        final String rights = manifest.getLicense().stream().findFirst().orElse(null);

        //build Logo
        final ManifestVersion3.Logo logo = ManifestVersion3.Logo.builder()
                .id(domainLogo)
                .type(IMAGE)
                .build();

        // build Metadata
        List<MetadataVersion3> finalMetadata = metadataBuilderVersion3.execute();

        //build Structures
        final Optional<List<Structure>> structures = ofNullable(manifest.getStructures());
        List<Item> newStructures = null;
        if (structures.isPresent()) {
            final List<Structure> structs = structures.get();
            final StructureBuilderVersion3 sbuilder = StructureBuilderVersion3.builder()
                    .structures(structs)
                    .viewId(viewId)
                    .build();
            sbuilder.buildIncrements();
            newStructures = sbuilder.build();
        }

        //build seeAlso
        final String finalURN = converterUtils.getURNfromFinalMetadata(finalMetadata);
        @SuppressWarnings("unchecked")
        List<String> related = (List<String>) manifest.getRelated();
        final List<SeeAlso> seeAlso = converterUtils.buildSeeAlso(viewId, finalURN, related);

        //build Homepages
        boolean isHSP = converterUtils.isHspManifest(manifest);
        final List<Homepage> homepages = converterUtils.buildHomepages(related);

        //build manifest label
        final String manifestLabel = manifest.getLabel();
        final Map<String, List<String>> manifestLabelMap = buildLabelMap(manifestLabel, NONE);

        return ManifestVersion3.builder()
                .behavior(behaviors)
                .context(contexts)
                .homepage(homepages)
                .id(id)
                .items(canvases)
                .label(manifestLabelMap)
                .logo(!isHSP ? logo : null)
                .metadata(finalMetadata)
                .requiredStatement(requiredStatement)
                .rights(rights)
                .seeAlso(seeAlso)
                .structures(newStructures)
                .type(MANIFEST)
                .viewingDirection(LEFT_TO_RIGHT)
                .build();
    }

}
