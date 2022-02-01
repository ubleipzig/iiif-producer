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

package de.ubleipzig.iiifproducer.producer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.ubleipzig.iiif.vocabulary.SC;
import de.ubleipzig.iiifproducer.converter.ConverterVersion3;
import de.ubleipzig.iiifproducer.model.ImageServiceResponse;
import de.ubleipzig.iiifproducer.model.v2.*;
import de.ubleipzig.iiifproducer.model.v3.ManifestVersion3;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.rdf.api.IRI;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static de.ubleipzig.iiifproducer.model.ManifestSerializer.serialize;
import static de.ubleipzig.iiifproducer.model.ManifestSerializer.writeToFile;
import static java.io.File.separator;
import static java.lang.String.format;

/**
 * IIIFProducer.
 *
 * @author christopher-johnson
 */
@Slf4j
@Builder
@AllArgsConstructor
public class IIIFProducer {

    @Builder.Default
    private String baseUrl = "https://iiif.ub.uni-leipzig.de/";
    @Builder.Default
    private String canvasContext = "/canvas";
    @Builder.Default
    private String defaultSequenceId = "/sequence/1";
    @Builder.Default
    private String dfgFileName = "presentation.xml";
    @Builder.Default
    private String format = "v3";
    @Builder.Default
    private String viewerUrl = "https://digital.ub.uni-leipzig.de/object/viewid/";
    @Builder.Default
    private String katalogUrl = "https://katalog.ub.uni-leipzig.de/urn/";
    @Builder.Default
    private String manifestFileName = "manifest.json";
    @Builder.Default
    private String fulltextContext = "alto";
    @Builder.Default
    private String fulltextFileGrp = "FULLTEXT";

    private IRIBuilder iriBuilder;
    private MetsAccessor mets;
    private String outputFile;
    private String resourceContext;
    private String viewId;
    private String xmlFile;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public void run() {
        log.info("Running IIIF producer...");
        buildManifest();
    }

    public void setRelated(final Manifest body, final String urn, final String viewIdFormatted,
                           final boolean isHspCatalog) {
        final ArrayList<String> related = new ArrayList<>();
        if (!isHspCatalog) {
            log.info("Kein HSP-Manifest");
            related.add(katalogUrl + urn);
            related.add(viewerUrl + viewIdFormatted);
        } else {
            log.info("Ist HSP-Manifest");
        }
        related.add(baseUrl + viewIdFormatted + separator + manifestFileName);
        if (!isHspCatalog) {
            related.add(baseUrl + viewIdFormatted + separator + dfgFileName);
        }
        body.setRelated(related);
    }

    /**
     * @param canvas Canvas
     * @param div    String
     * @param viewId String
     */
    public void setCanvasSeeAlso(final Canvas canvas, final String div,
                                 final String viewId) {
        final String fileId = mets.getFile(div, fulltextFileGrp);
        if (fileId != null && !fileId.isBlank()) {
            final SeeAlso seeAlso = SeeAlso.builder().build();
            final String href = mets.getHref(fileId);
            final String format = mets.getFormatForFile(fileId);

            final String fileName = new File(href).getName();

            final String fulltextContextFull = fulltextContext == null || fulltextContext.isEmpty()
                    ? "" : (fulltextContext + separator);

            seeAlso.setId(baseUrl + viewId + separator + fulltextContextFull + fileName);
            // application/alto+xml vs application/xml+alto: https://github.com/dbmdz/mirador-textoverlay/issues/167
            seeAlso.setFormat(format);
            if ("application/alto+xml".equals(format)) {
                // TODO use profile from xml file's xsd or have a configuration option (media type to profile)
                seeAlso.setProfile("http://www.loc.gov/standards/alto/alto-v2.0.xsd");
            }
            canvas.setSeeAlso(seeAlso);
        }
    }

    public List<Sequence> addCanvasesToSequence(final List<Canvas> canvases) {
        final List<Sequence> sequence = new ArrayList<>();
        sequence.add(Sequence.builder()
                .id(resourceContext + defaultSequenceId)
                .canvases(canvases)
                .build());
        return sequence;
    }

    Manifest setStructures(final TopStructure top, final Manifest manifest) {
        if (top.getRanges().size() > 0) {
            final List<Structure> subStructures = mets.buildStructures();
            if (subStructures.size() > 0) {
                final StructureList list = StructureList.builder().top(top).structures(subStructures).build();
                manifest.setStructures(list.getStructureList());
                return manifest;
            }
        }
        return manifest;
    }

    public void buildManifest() {

        final String resourceContext = baseUrl + viewId;

        final Manifest manifest = Manifest.builder()
                .context(SC.CONTEXT)
                .id(resourceContext + separator + manifestFileName)
                .build();

        final Integer viewIdInput = Integer.valueOf(viewId);
        final String viewIdFormatted = format("%010d", viewIdInput);
        final String imageServiceContext = iriBuilder.buildImageServiceContext(viewId);
        final String urn = mets.getUrnReference();
        final Boolean isCatalog = mets.getCatalogType();
        setRelated(manifest, urn, viewIdFormatted, isCatalog);

        mets.setManifestLabel(manifest);
        // TODO HSP-Spezifika - ggf bereits aus dem METS/MODS
        mets.setLicense(manifest);
        mets.setAttribution(manifest);
        mets.setLogo(manifest);
        final Boolean isManuscript = mets.getMtype();

        if (isManuscript) {
            mets.setHandschriftMetadata(manifest);
        } else if (isCatalog) {
            mets.setHspCatalogMetadata(manifest);
        } else {
            mets.setMetadata(manifest);
        }

        final List<Canvas> canvases = new ArrayList<>();
        final AtomicInteger atomicInteger = new AtomicInteger(1);
        final List<String> divs = mets.getPhysical();
        for (String div : divs) {
            final String label = mets.getOrderLabel(div);

            final Canvas canvas = Canvas.builder()
                    .label(label)
                    .build();

            //buildServiceIRI
            final String resourceFileId = format("%08d", atomicInteger.getAndIncrement());
            final IRI serviceIRI = iriBuilder.buildServiceIRI(imageServiceContext, resourceFileId);

            final Body resource = Body.builder()
                    .label(label)
                    .build();

            //getDimensionsFromImageService
            final InputStream is;
            try {
                is = new URL(serviceIRI.getIRIString() + "/info.json").openStream();
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
            final ImageServiceResponse ir = mapServiceResponse(is);
            final Integer height = ir.getHeight();
            final Integer width = ir.getWidth();
            canvas.setWidth(width);
            canvas.setHeight(height);
            resource.setWidth(width);
            resource.setHeight(height);

            //canvasId = resourceId
            final String canvasIdString = resourceContext + canvasContext + separator + resourceFileId;
            final String resourceIdString =
                    resourceContext + separator + resourceFileId + ".jpg";
            //cast canvas as IRI (failsafe)
            final IRI canvasIri = iriBuilder.buildCanvasIRI(canvasIdString);
            //set Canvas Id
            canvas.setId(canvasIri.getIRIString());
            //cast resource as IRI (failsafe)
            final IRI resourceIri = iriBuilder.buildResourceIRI(resourceIdString);
            //set resourceID
            resource.setId(resourceIri.getIRIString());
            resource.setService(Service.builder().id(serviceIRI.getIRIString()).build());

            //set Annotation
            final String annotationId = iriBuilder.buildAnnotationId();
            final PaintingAnnotation image = PaintingAnnotation.builder()
                    .id(annotationId)
                    .resource(resource)
                    .on(canvas.getId())
                    .build();

            final List<PaintingAnnotation> images = new ArrayList<>();
            images.add(image);

            canvas.setImages(images);

            setCanvasSeeAlso(canvas, div, viewIdFormatted);

            canvases.add(canvas);
        }

        final List<Sequence> sequence = addCanvasesToSequence(canvases);
        manifest.setSequences(sequence);

        final TopStructure top = mets.buildTopStructure();
        final Manifest manifestVersion2;
        manifestVersion2 = setStructures(top, manifest);

        String manifestOutput;
        if ("v3".equals(format)) {
            ConverterVersion3 reserializerVersion3 = ConverterVersion3.builder()
                    .manifest(manifestVersion2)
                    .build();
            ManifestVersion3 manifestVersion3 = reserializerVersion3.execute();
            manifestOutput = serialize(manifestVersion3).orElse(null);
        } else {
            manifestOutput = serialize(manifestVersion2).orElse(null);
        }
        log.info("Builder Process Complete, Serializing to Json ...");
        final File outfile = new File(outputFile);
        log.info("Writing file to {}", outputFile);
        writeToFile(manifestOutput, outfile);
        log.debug("Manifest Output: {}", manifestOutput);
    }

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
}

