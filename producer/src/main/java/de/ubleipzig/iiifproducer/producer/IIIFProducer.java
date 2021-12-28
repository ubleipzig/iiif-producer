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
import de.ubleipzig.iiifproducer.template.*;
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
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import static de.ubleipzig.iiifproducer.template.ManifestSerializer.serialize;
import static de.ubleipzig.iiifproducer.template.ManifestSerializer.writeToFile;
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

    private String baseUrl;
    private String canvasContext;
    private final Properties config;
    private String defaultSequenceId;
    private String dfgFileName;
    private String katalogUrl;
    private String fulltextContext;
    private String fulltextFileGrp;
    private String manifestFileName;
    private String outputFile;
    private String resourceContext;
    private String viewId;
    private String viewerUrl;
    private String xmlFile;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public void run() {
        log.info("Running IIIF producer...");
        buildManifest();
    }

    public void setContext(final TemplateManifest body) {
        body.setContext(SC.CONTEXT);
    }

    public void setId(final TemplateManifest body) {
        final String resourceContext = baseUrl + viewId;
        body.setId(resourceContext + separator + manifestFileName);
    }

    public void setRelated(final TemplateManifest body, final String urn, final String viewId,
                           final boolean isHspCatalog) {
        final ArrayList<String> related = new ArrayList<>();
        if (!isHspCatalog) {
            log.info("Kein HSP-Manifest");
            related.add(katalogUrl + urn);
            related.add(viewerUrl + viewId);
        } else {
            log.info("Ist HSP-Manifest");
        }
        related.add(baseUrl + viewId + separator + manifestFileName);
        if (!isHspCatalog) {
            related.add(baseUrl + viewId + separator + dfgFileName);
        }
        body.setRelated(related);
    }

    /**
     * @param canvas TemplateCanvas
     * @param mets MetsAccessor
     * @param div String
     * @param viewId String
     */
    public void setCanvasSeeAlso(final TemplateCanvas canvas, final MetsAccessor mets, final String div,
                                 final String viewId) {
        final String fileId = mets.getFile(div, fulltextFileGrp);
        if (fileId != null && !fileId.isBlank()) {
            final TemplateSeeAlso seeAlso = new TemplateSeeAlso();
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

    public List<TemplateSequence> addCanvasesToSequence(final List<TemplateCanvas> canvases) {
        final List<TemplateSequence> sequence = new ArrayList<>();
        sequence.add(new TemplateSequence(resourceContext + defaultSequenceId, canvases));
        return sequence;
    }

    TemplateManifest setStructures(final TemplateTopStructure top, final TemplateManifest manifest, final
    MetsAccessor mets) {
        if (top.getRanges().size() > 0) {
            final List<TemplateStructure> subStructures = mets.buildStructures();
            if (subStructures.size() > 0) {
                final TemplateStructureList list = new TemplateStructureList(top, subStructures);
                manifest.setStructures(list.getStructureList());
                return manifest;
            }
        }
        return manifest;
    }

    public void buildManifest() {

        final TemplateManifest manifest = new TemplateManifest();
        setContext(manifest);
        setId(manifest);
        final IRIBuilder iriBuilder = IRIBuilder.builder()
                .annotationContext(config.getProperty("annotationContext"))
                .canvasContext(config.getProperty("canvasContext"))
                .imageServiceBaseUrl(config.getProperty("imageServiceBaseUrl"))
                .imageServiceFileExtension(config.getProperty("imageServiceFileExtension"))
                .imageServiceImageDirPrefix(config.getProperty("imageServiceImageDirPrefix"))
                .isUBLImageService((boolean) config.get("isUBLImageService"))
                .resourceContext(baseUrl + viewId)
                .build();

        final MetsAccessor mets = MetsImpl.builder()
                .anchorKey(config.getProperty("anchorKey"))
                .attributionKey(config.getProperty("attributionKey"))
                .attributionLicenseNote(config.getProperty("attributionLicenseNote"))
                .iriBuilder(iriBuilder)
                .license(config.getProperty("license"))
                .rangeContext(config.getProperty("rangeContext"))
                .resourceContext(resourceContext)
                .xmlFile(xmlFile)
                .mets()
                .xlinkmap()
                .build();

        final Integer viewIdInput = Integer.valueOf(viewId);
        final String viewId = format("%010d", viewIdInput);
        final String imageServiceContext = iriBuilder.buildImageServiceContext(viewId);
        final String urn = mets.getUrnReference();
        final Boolean isCatalog = mets.getCatalogType();
        setRelated(manifest, urn, viewId, isCatalog);

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

        final List<TemplateCanvas> canvases = new ArrayList<>();
        final AtomicInteger atomicInteger = new AtomicInteger(1);
        final List<String> divs = mets.getPhysical();
        for (String div : divs) {
            final String label = mets.getOrderLabel(div);

            final TemplateCanvas canvas = new TemplateCanvas();
            canvas.setCanvasLabel(label);

            //buildServiceIRI
            final String resourceFileId = format("%08d", atomicInteger.getAndIncrement());
            final IRI serviceIRI = iriBuilder.buildServiceIRI(imageServiceContext, resourceFileId);

            final TemplateResource resource = new TemplateResource();
            resource.setResourceLabel(label);

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
            canvas.setCanvasWidth(width);
            canvas.setCanvasHeight(height);
            resource.setResourceWidth(width);
            resource.setResourceHeight(height);

            //canvasId = resourceId
            final String canvasIdString = resourceContext + canvasContext + separator + resourceFileId;
            final String resourceIdString =
                    resourceContext + separator + resourceFileId + ".jpg";
            //cast canvas as IRI (failsafe)
            final IRI canvasIri = iriBuilder.buildCanvasIRI(canvasIdString);
            //set Canvas Id
            canvas.setCanvasId(canvasIri.getIRIString());
            //cast resource as IRI (failsafe)
            final IRI resourceIri = iriBuilder.buildResourceIRI(resourceIdString);
            //set resourceID
            resource.setResourceId(resourceIri.getIRIString());
            resource.setService(new TemplateService(serviceIRI.getIRIString()));

            //set Annotation
            final TemplateImage image = new TemplateImage();
            final String annotationId = iriBuilder.buildAnnotationId();
            image.setId(annotationId);
            image.setResource(resource);
            image.setTarget(canvas.getCanvasId());

            final List<TemplateImage> images = new ArrayList<>();
            images.add(image);

            canvas.setCanvasImages(images);

            setCanvasSeeAlso(canvas, mets, div, viewId);

            canvases.add(canvas);
        }

        final List<TemplateSequence> sequence = addCanvasesToSequence(canvases);
        manifest.setSequences(sequence);

        final TemplateTopStructure top = mets.buildTopStructure();
        final TemplateManifest structManifest;
        structManifest = setStructures(top, manifest, mets);

        log.info("Builder Process Complete, Serializing to Json ...");
        final Optional<String> json = serialize(structManifest);
        final String output = json.orElse(null);
        final File outfile = new File(outputFile);
        log.info("Writing file to {}", outputFile);
        writeToFile(output, outfile);
        log.debug("Manifest Output: {}", output);
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

