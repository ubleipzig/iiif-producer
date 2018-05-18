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

import static de.ubleipzig.iiifproducer.doc.MetsConstants.HANDSHRIFT_TYPE;
import static de.ubleipzig.iiifproducer.producer.UUIDType5.NAMESPACE_URL;
import static de.ubleipzig.iiifproducer.template.ManifestSerializer.serialize;
import static de.ubleipzig.iiifproducer.template.ManifestSerializer.writeToFile;
import static java.io.File.separator;
import static java.lang.String.format;
import static org.apache.commons.io.FilenameUtils.getBaseName;
import static org.slf4j.LoggerFactory.getLogger;

import de.ubleipzig.iiif.vocabulary.SC;
import de.ubleipzig.iiifproducer.template.TemplateCanvas;
import de.ubleipzig.iiifproducer.template.TemplateImage;
import de.ubleipzig.iiifproducer.template.TemplateManifest;
import de.ubleipzig.iiifproducer.template.TemplateResource;
import de.ubleipzig.iiifproducer.template.TemplateSequence;
import de.ubleipzig.iiifproducer.template.TemplateService;
import de.ubleipzig.iiifproducer.template.TemplateStructure;
import de.ubleipzig.iiifproducer.template.TemplateStructureList;
import de.ubleipzig.iiifproducer.template.TemplateTopStructure;
import de.ubleipzig.image.metadata.ImageMetadataService;
import de.ubleipzig.image.metadata.ImageMetadataServiceConfig;
import de.ubleipzig.image.metadata.ImageMetadataServiceImpl;
import de.ubleipzig.image.metadata.templates.ImageDimensionManifest;
import de.ubleipzig.image.metadata.templates.ImageDimensions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    public void setRelated(final TemplateManifest body, final String urn) {
        final String viewId = config.getViewId();
        final ArrayList<String> related = new ArrayList<>();
        related.add(config.getKatalogUrl() + urn);
        related.add(config.getViewerUrl() + viewId);
        related.add(config.getBaseUrl() + viewId + separator + config.getManifestFilename());
        body.setRelated(related);
    }

    @Override
    public List<TemplateSequence> addCanvasesToSequence(final List<TemplateCanvas> canvases) {
        final String resourceContext = config.getResourceContext();
        final List<TemplateSequence> sequence = new ArrayList<>();
        sequence.add(new TemplateSequence(resourceContext + config.getDefaultSequenceId(), canvases));
        return sequence;
    }

    private String getImageManifestPid() {
        return "image-manifest-" + UUIDType5.nameUUIDFromNamespaceAndString(NAMESPACE_URL, config.getImageSourceDir())
                + ".json";
    }

    @Override
    public void setImageDimensions(final ImageDimensions dimensions, final TemplateCanvas canvas, final
    TemplateResource resource) {
        final Integer width = dimensions.getWidth();
        final Integer height = dimensions.getHeight();
        canvas.setCanvasWidth(width);
        canvas.setCanvasHeight(height);
        resource.setResourceWidth(width);
        resource.setResourceHeight(height);
    }

    @Override
    public List<ImageDimensions> getImageDimensions(final String imageSourceDir, final String
            dimensionManifestOutputPath) {
        final ImageMetadataServiceConfig imageMetadataGeneratorConfig = new ImageMetadataServiceConfig();
        final Optional<String> out = Optional.ofNullable(dimensionManifestOutputPath);
        final String dimensionManifestPath = out.orElse(
                config.getImageManifestOutputDir() + separator + getImageManifestPid());
        //case 1: the image manifest exists at output path
        if (new File(dimensionManifestPath).exists()) {
            imageMetadataGeneratorConfig.setDimensionManifestFilePath(dimensionManifestPath);
            final ImageMetadataService imageMetadataService = new ImageMetadataServiceImpl(
                    imageMetadataGeneratorConfig);
            return imageMetadataService.unmarshallDimensionManifestFromFile();
        }
        imageMetadataGeneratorConfig.setImageSourceDir(imageSourceDir);
        final ImageMetadataService imageMetadataService = new ImageMetadataServiceImpl(imageMetadataGeneratorConfig);
        //case 2: serialize new image manifest from binaries
        if (config.getSerializeImageManifest()) {
            if (!new File(imageSourceDir).exists()) {
                throw new RuntimeException("no images found at " + imageSourceDir + " - Exiting");
            }
            final ImageDimensionManifest dimManifest = imageMetadataService.build();
            imageMetadataService.serializeImageDimensionManifest(dimManifest, dimensionManifestPath);
            imageMetadataGeneratorConfig.setDimensionManifestFilePath(dimensionManifestPath);
            logger.debug("Writing Image Dimension Manifest to {}", dimensionManifestPath);
            return imageMetadataService.unmarshallDimensionManifestFromFile();
        } else {
            //case 3: do not serialize manifest, just read binaries and return list of dimensions
            if (!new File(imageSourceDir).exists()) {
                throw new RuntimeException("no images found at " + imageSourceDir + " - Exiting");
            }
            final String imageManifest = imageMetadataService.buildImageMetadataManifest();
            return imageMetadataService.buildDimensionManifestListFromImageMetadataManifest(imageManifest);
        }
    }

    TemplateManifest setStructures(final TemplateTopStructure top, final TemplateManifest manifest, final
    MetsAccessor mets) {
        if (top.getRanges().size() > 0) {
            final List<TemplateStructure> subStructures = mets.buildStructures();
            final TemplateStructureList list = new TemplateStructureList(top, subStructures);
            manifest.setStructures(list.getStructureList());
            return manifest;
        }
        return manifest;
    }

    @Override
    public void buildManifest() {
        //TODO these filesystem dependencies should be abstracted to a repository container
        final String imageManifestOutputPath = config.getImageSourceDir() + separator + getImageManifestPid();
        final String imageSourceDir = config.getImageSourceDir();
        final List<ImageDimensions> dimensions = getImageDimensions(imageSourceDir, imageManifestOutputPath);

        final TemplateManifest manifest = new TemplateManifest();
        setContext(manifest);
        setId(manifest);

        final MetsAccessor mets = new MetsImpl(this.config);
        final IRIBuilder iriBuilder = new IRIBuilder(this.config);

        final String urn = mets.getUrnReference();
        setRelated(manifest, urn);

        mets.setManifestLabel(manifest);
        mets.setLicense(manifest);
        mets.setAttribution(manifest);
        mets.setLogo(manifest);
        final String mtype = mets.getMtype();

        if (Objects.equals(mtype, HANDSHRIFT_TYPE)) {
            mets.setHandschriftMetadata(manifest);
        } else {
            mets.setMetadata(manifest);
        }

        final List<TemplateCanvas> canvases = new ArrayList<>();
        final AtomicInteger atomicInteger = new AtomicInteger(0);
        final List<String> divs = mets.getPhysical();
        for (String div : divs) {
            final String label = mets.getOrderLabel(div);

            final TemplateCanvas canvas = new TemplateCanvas();
            canvas.setCanvasLabel(label);

            final TemplateResource resource = new TemplateResource();
            resource.setResourceLabel(label);

            final String fileID = mets.getFile(div);
            logger.debug("File Id: {}", fileID);
            final String fileName = mets.getHref(fileID);
            logger.debug("File Name: {}", fileName);
            final ImageDimensions dimension = dimensions.get(atomicInteger.get());
            setImageDimensions(dimension, canvas, resource);

            //get resource context from config
            final String resourceContext = config.getResourceContext();
            //get integer identifier from filename
            final Integer baseName = Integer.valueOf(getBaseName(fileName));
            //pad integer to 8 digits
            final String resourceFileId = format("%08d", baseName);
            //canvasId = resourceId
            final String canvasIdString = resourceContext + config.getCanvasContext() + separator + resourceFileId;
            //cast canvas as IRI (failsafe)
            final IRI canvasIri = iriBuilder.buildCanvasIRI(canvasIdString);
            //set Canvas Id
            canvas.setCanvasId(canvasIri.getIRIString());
            //resource IRI (original source file extension required by client)
            //TODO coordinate with dereferenceable location / confirm file extention
            final String resourceIdString = resourceContext + separator + resourceFileId + ".jpg";
            //cast resource as IRI (failsafe)
            final IRI resourceIri = iriBuilder.buildResourceIRI(resourceIdString);
            //set resourceID
            resource.setResourceId(resourceIri.getIRIString());
            //set Image Service
            //TODO fix IIIP image service IRI format
            final String imageServiceContext = iriBuilder.buildImageServiceContext();
            final IRI serviceIRI = iriBuilder.buildServiceIRI(imageServiceContext, resourceFileId);
            resource.setService(new TemplateService(serviceIRI.getIRIString()));

            final TemplateImage image = new TemplateImage();
            image.setResource(resource);
            image.setTarget(canvas.getCanvasId());

            final List<TemplateImage> images = new ArrayList<>();
            images.add(image);

            canvas.setCanvasImages(images);

            canvases.add(canvas);
            atomicInteger.getAndIncrement();
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
}

