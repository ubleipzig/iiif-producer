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
import static de.ubleipzig.iiifproducer.producer.Constants.BASE_URL;
import static de.ubleipzig.iiifproducer.producer.Constants.IIIF_CANVAS;
import static de.ubleipzig.iiifproducer.producer.Constants.KATALOG_URL;
import static de.ubleipzig.iiifproducer.producer.Constants.MANIFEST_FILENAME;
import static de.ubleipzig.iiifproducer.producer.Constants.SEQUENCE_ID;
import static de.ubleipzig.iiifproducer.producer.Constants.VIEWER_URL;
import static de.ubleipzig.iiifproducer.producer.StaticIRIBuilder.buildCanvasIRI;
import static de.ubleipzig.iiifproducer.producer.StaticIRIBuilder.buildImageServiceContext;
import static de.ubleipzig.iiifproducer.producer.StaticIRIBuilder.buildResourceIRI;
import static de.ubleipzig.iiifproducer.producer.StaticIRIBuilder.buildServiceIRI;
import static de.ubleipzig.iiifproducer.producer.UUIDType5.NAMESPACE_URL;
import static de.ubleipzig.iiifproducer.template.ManifestSerializer.serialize;
import static de.ubleipzig.iiifproducer.template.ManifestSerializer.writeToFile;
import static de.ubleipzig.iiifproducer.vocabulary.IIIF.PRESENTATION_CONTEXT;
import static java.io.File.separator;
import static java.lang.String.format;
import static org.apache.commons.io.FilenameUtils.getBaseName;
import static org.slf4j.LoggerFactory.getLogger;

import de.ubleipzig.iiifproducer.storage.SQL;
import de.ubleipzig.iiifproducer.template.TemplateCanvas;
import de.ubleipzig.iiifproducer.template.TemplateImage;
import de.ubleipzig.iiifproducer.template.TemplateManifest;
import de.ubleipzig.iiifproducer.template.TemplateResource;
import de.ubleipzig.iiifproducer.template.TemplateSequence;
import de.ubleipzig.iiifproducer.template.TemplateService;
import de.ubleipzig.iiifproducer.template.TemplateStructure;
import de.ubleipzig.iiifproducer.template.TemplateStructureList;
import de.ubleipzig.iiifproducer.template.TemplateTopStructure;
import de.ubleipzig.image.metadata.ImageMetadataGenerator;
import de.ubleipzig.image.metadata.ImageMetadataGeneratorConfig;
import de.ubleipzig.image.metadata.templates.ImageDimensionManifest;
import de.ubleipzig.image.metadata.templates.ImageDimensions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
        if (config.useSQL()) {
            final SQL db = new SQL(config.getTitle(), config.getViewId());
            db.initDb();
        }
    }

    @Override
    public void setContext(final TemplateManifest body) {
        body.setContext(PRESENTATION_CONTEXT);
    }

    @Override
    public void setId(final TemplateManifest body) {
        final String resourceContext = config.getResourceContext();
        body.setId(resourceContext + separator + MANIFEST_FILENAME);
    }

    @Override
    public void setRelated(final TemplateManifest body, final String urn) {
        final String viewId = config.getViewId();
        final ArrayList<String> related = new ArrayList<>();
        related.add(KATALOG_URL + urn);
        related.add(VIEWER_URL + viewId);
        related.add(BASE_URL + viewId + separator + MANIFEST_FILENAME);
        body.setRelated(related);
    }

    @Override
    public List<TemplateSequence> addCanvasesToSequence(final List<TemplateCanvas> canvases) {
        final String resourceContext = config.getResourceContext();
        final List<TemplateSequence> sequence = new ArrayList<>();
        sequence.add(new TemplateSequence(resourceContext + SEQUENCE_ID, canvases));
        return sequence;
    }

    @Override
    public void buildImageDimensionManifest() throws FileNotFoundException {
        final String imageSourceDir = config.getBaseDir() + separator + config.getTitle() + "_tif";

        if (!new File(imageSourceDir).exists()) {
            throw new FileNotFoundException("Images Not Found");
        }

        final ImageMetadataGeneratorConfig imageMetadataGeneratorConfig = new ImageMetadataGeneratorConfig();
        imageMetadataGeneratorConfig.setImageSourceDir(imageSourceDir);
        final ImageMetadataGenerator generator = new ImageMetadataGenerator(imageMetadataGeneratorConfig);
        final ImageDimensionManifest dimManifest = generator.build();
        final String imageManifestPid = "image-manifest-" + UUIDType5.nameUUIDFromNamespaceAndString(
                NAMESPACE_URL, config.getTitle());
        final String outputPath = config.getBaseDir() + separator + imageManifestPid;
        final String out = serialize(dimManifest).orElse("");
        writeToFile(out, new File(outputPath));
        logger.debug("Writing Image Dimension Manifest to: {}", outputPath);
    }

    @Override
    public List<ImageDimensions> getImageDimensionManifest() {
        final ImageMetadataGeneratorConfig imageMetadataGeneratorConfig = new ImageMetadataGeneratorConfig();
        imageMetadataGeneratorConfig.setDimensionManifestFilePath(getImageDimensionManifestPath());
        final ImageMetadataGenerator generator = new ImageMetadataGenerator(imageMetadataGeneratorConfig);
        final List<ImageDimensions> dimensions = generator.buildDimensionManifestFromFile();
        logger.debug("Building ImageDimension List");
        return dimensions;
    }

    private String getImageDimensionManifestPath() {
        final String imageManifestPid = "image-manifest-" + UUIDType5.nameUUIDFromNamespaceAndString(
                NAMESPACE_URL, config.getTitle());
        return config.getBaseDir() + separator + imageManifestPid;
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
    public void buildManifest() {

        final List<ImageDimensions> dimensions;
        if (!new File(getImageDimensionManifestPath()).exists()) {
            try {
                buildImageDimensionManifest();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.exit(1);
            }
            dimensions = getImageDimensionManifest();
        } else {
            dimensions = getImageDimensionManifest();
        }

        final TemplateManifest manifest = new TemplateManifest();
        setContext(manifest);
        setId(manifest);

        MetsAccessor mets = null;

        try {
            mets = new MetsImpl(this.config);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (mets != null) {
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
                final String canvasIdString = resourceContext + IIIF_CANVAS + separator + resourceFileId;
                //cast canvas as IRI (failsafe)
                final IRI canvasIri = buildCanvasIRI(canvasIdString);
                //set Canvas Id
                canvas.setCanvasId(canvasIri.getIRIString());
                //resource IRI (original source file extension required by client)
                //TODO coordinate with dereferenceable location / confirm file extention
                final String resourceIdString = resourceContext + separator + resourceFileId + ".jpg";
                //cast resource as IRI (failsafe)
                final IRI resourceIri = buildResourceIRI(resourceIdString);
                //set resourceID
                resource.setResourceId(resourceIri.getIRIString());
                //set Image Service
                //TODO fix IIIP image service IRI format
                final String imageServiceContext = buildImageServiceContext(config.getViewId());
                final IRI serviceIRI = buildServiceIRI(imageServiceContext, resourceFileId);
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
            final List<TemplateStructure> subStructures = mets.buildStructures();

            final TemplateStructureList list = new TemplateStructureList(top, subStructures);
            manifest.setStructures(list.getStructureList());
            logger.info("Builder Process Complete, Serializing to Json ...");
            final Optional<String> json = serialize(manifest);
            final String output = json.orElse(null);
            final String outputFile = config.getOutputFile();
            final File outfile = new File(outputFile);
            logger.info("Writing file to {}", outputFile);
            writeToFile(output, outfile);
            logger.debug("Manifest Output: {}", output);
        }
    }
}
