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

import static de.ubleipzig.iiifproducer.template.ManifestSerializer.serialize;
import static de.ubleipzig.iiifproducer.template.ManifestSerializer.writeToFile;
import static java.io.File.separator;
import static java.lang.String.format;
import static org.slf4j.LoggerFactory.getLogger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.ubleipzig.iiif.vocabulary.SC;
import de.ubleipzig.iiifproducer.template.ImageServiceResponse;
import de.ubleipzig.iiifproducer.template.TemplateCanvas;
import de.ubleipzig.iiifproducer.template.TemplateImage;
import de.ubleipzig.iiifproducer.template.TemplateManifest;
import de.ubleipzig.iiifproducer.template.TemplateResource;
import de.ubleipzig.iiifproducer.template.TemplateSequence;
import de.ubleipzig.iiifproducer.template.TemplateService;
import de.ubleipzig.iiifproducer.template.TemplateStructure;
import de.ubleipzig.iiifproducer.template.TemplateStructureList;
import de.ubleipzig.iiifproducer.template.TemplateTopStructure;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.rdf.api.IRI;
import org.slf4j.Logger;

/**
 * IIIFProducer.
 *
 * @author christopher-johnson
 */
public class IIIFProducer implements ManifestBuilderProcess {

    private static Logger logger = getLogger(IIIFProducer.class);
    private final Config config;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * IIIFProducer Class.
     */
    IIIFProducer(final Config config) {
        this.config = config;
    }

    @Override
    public void run() {
        logger.info("Running IIIF producer...");
        buildManifest();
    }

    @Override
    public void setContext(final TemplateManifest body) {
        body.setContext(SC.CONTEXT);
    }

    @Override
    public void setId(final TemplateManifest body) {
        final String resourceContext = config.getResourceContext();
        body.setId(resourceContext + separator + config.getManifestFilename());
    }

    @Override
    public void setRelated(final TemplateManifest body, final String urn, final String viewId) {
        final ArrayList<String> related = new ArrayList<>();
        related.add(config.getKatalogUrl() + urn);
        related.add(config.getViewerUrl() + viewId);
        related.add(config.getBaseUrl() + viewId + separator + config.getManifestFilename());
        related.add(config.getBaseUrl() + viewId + separator + config.getDfgFilename());
        body.setRelated(related);
    }

    @Override
    public List<TemplateSequence> addCanvasesToSequence(final List<TemplateCanvas> canvases) {
        final String resourceContext = config.getResourceContext();
        final List<TemplateSequence> sequence = new ArrayList<>();
        sequence.add(new TemplateSequence(resourceContext + config.getDefaultSequenceId(), canvases));
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

    @Override
    public void buildManifest() {

        final TemplateManifest manifest = new TemplateManifest();
        setContext(manifest);
        setId(manifest);

        final MetsAccessor mets = new MetsImpl(this.config);
        final IRIBuilder iriBuilder = new IRIBuilder(this.config);
        final Integer viewIdInput = Integer.valueOf(config.getViewId());
        final String viewId = format("%010d", viewIdInput);
        final String resourceContext = config.getResourceContext();
        final String imageServiceContext = iriBuilder.buildImageServiceContext(viewId);
        final String canvasContext = config.getCanvasContext();
        final String urn = mets.getUrnReference();
        setRelated(manifest, urn, viewId);

        mets.setManifestLabel(manifest);
        mets.setLicense(manifest);
        mets.setAttribution(manifest);
        mets.setLogo(manifest);
        final Boolean isManuscript = mets.getMtype();

        if (isManuscript) {
            mets.setHandschriftMetadata(manifest);
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
            canvases.add(canvas);
        }

        final List<TemplateSequence> sequence = addCanvasesToSequence(canvases);
        manifest.setSequences(sequence);

        final TemplateTopStructure top = mets.buildTopStructure();
        final TemplateManifest structManifest;
        structManifest = setStructures(top, manifest, mets);

        logger.info("Builder Process Complete, Serializing to Json ...");
        final Optional<String> json = serialize(structManifest);
        final String output = json.orElse(null);
        final String outputFile = config.getOutputFile();
        final File outfile = new File(outputFile);
        logger.info("Writing file to {}", outputFile);
        writeToFile(output, outfile);
        logger.debug("Manifest Output: {}", output);
    }

    /**
     * mapServiceResponse.
     *
     * @param res String
     * @return ImageServiceResponse
     */
    public static ImageServiceResponse mapServiceResponse(final InputStream res) {
        try {
            return MAPPER.readValue(res, new TypeReference<ImageServiceResponse>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

