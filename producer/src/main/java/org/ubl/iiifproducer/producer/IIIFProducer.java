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

package org.ubl.iiifproducer.producer;

import static java.lang.String.format;
import static java.lang.String.valueOf;
import static org.apache.commons.io.FilenameUtils.getBaseName;
import static org.slf4j.LoggerFactory.getLogger;
import static org.ubl.iiifproducer.doc.MetsConstants.HANDSHRIFT_TYPE;
import static org.ubl.iiifproducer.producer.Constants.BASE_URL;
import static org.ubl.iiifproducer.producer.Constants.IIIF_CANVAS;
import static org.ubl.iiifproducer.producer.Constants.MANIFEST_FILENAME;
import static org.ubl.iiifproducer.producer.Constants.SEQUENCE_ID;
import static org.ubl.iiifproducer.producer.Constants.VIEWER_URL;
import static org.ubl.iiifproducer.producer.JAI.getImageDimensions;
import static org.ubl.iiifproducer.producer.StaticIRIBuilder.buildCanvasIRI;
import static org.ubl.iiifproducer.producer.StaticIRIBuilder.buildImageServiceContext;
import static org.ubl.iiifproducer.producer.StaticIRIBuilder.buildResourceIRI;
import static org.ubl.iiifproducer.producer.StaticIRIBuilder.buildServiceIRI;
import static org.ubl.iiifproducer.template.ManifestSerializer.serialize;
import static org.ubl.iiifproducer.template.ManifestSerializer.writeToFile;
import static org.ubl.iiifproducer.vocabulary.IIIF.PRESENTATION_CONTEXT;

import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.rdf.api.IRI;
import org.slf4j.Logger;
import org.ubl.iiifproducer.storage.SQL;
import org.ubl.iiifproducer.template.TemplateBody;
import org.ubl.iiifproducer.template.TemplateCanvas;
import org.ubl.iiifproducer.template.TemplateImage;
import org.ubl.iiifproducer.template.TemplateResource;
import org.ubl.iiifproducer.template.TemplateSequence;
import org.ubl.iiifproducer.template.TemplateService;
import org.ubl.iiifproducer.template.TemplateStructure;
import org.ubl.iiifproducer.template.TemplateStructureList;
import org.ubl.iiifproducer.template.TemplateTopStructure;

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
            SQL db = new SQL(config.getTitle(), config.getViewId());
            db.initDb();
        }
    }

    @Override
    public void setContext(TemplateBody body) {
        body.setContext(PRESENTATION_CONTEXT);
    }

    @Override
    public void setId(TemplateBody body) {
        String resourceContext = config.getResourceContext();
        body.setId(resourceContext + "/" + MANIFEST_FILENAME);
    }

    @Override
    public void setRelated(TemplateBody body) {
        String viewId = config.getViewId();
        ArrayList<String> related = new ArrayList<>();
        related.add(VIEWER_URL + viewId);
        related.add(BASE_URL + viewId + "/" + MANIFEST_FILENAME);
        body.setRelated(related);
    }

    @Override
    public List<TemplateSequence> addCanvasesToSequence(List<TemplateCanvas> canvases) {
        String resourceContext = config.getResourceContext();
        List<TemplateSequence> sequence = new ArrayList<>();
        sequence.add(new TemplateSequence(resourceContext + SEQUENCE_ID, canvases));
        return sequence;
    }

    @Override
    public String buildFilePath(String filename) {
        String baseDir = config.getBaseDir();
        String filePath;
        filePath = baseDir + "/" + filename;

        if (!new File(filePath).exists()) {
            int p0 = filePath.lastIndexOf(".");
            filePath = filePath.substring(0, p0) + ".TIF";
            if (!new File(filePath).exists()) {
                logger.error(valueOf(new FileNotFoundException("File Not Found")));
            }
        }
        return filePath;
    }

    @Override
    public void setImageDimensions(String filePath, TemplateCanvas canvas,
                                   TemplateResource resource) {
        Dimension dim = null;
        try {
            File imageFile = new File(filePath);
            if (imageFile.exists()) {
                dim = getImageDimensions(imageFile);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }

        if (dim != null) {
            final double imgWidth = dim.getWidth();
            final Integer width = (int) imgWidth;
            final double imgHeight = dim.getHeight();
            final Integer height = (int) imgHeight;
            canvas.setCanvasWidth(width);
            canvas.setCanvasHeight(height);
            resource.setResourceWidth(width);
            resource.setResourceHeight(height);
        }

    }

    @Override
    public void buildManifest() {
        TemplateBody body = new TemplateBody();
        setContext(body);
        setId(body);
        setRelated(body);
        MetsAccessor mets = null;

        try {
            mets = new MetsImpl(this.config);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (mets != null) {
            mets.setManifestLabel(body);
            mets.setAttribution(body);
            mets.setLogo(body);
            String mtype = mets.getMtype();
            if (Objects.equals(mtype, HANDSHRIFT_TYPE)) {
                mets.setHandschriftMetadata(body);
            } else {
                mets.setMetadata(body);
            }

            final List<TemplateCanvas> canvases = new ArrayList<>();

            List<String> divs = mets.getPhysical();
            for (String div : divs) {

                String label = mets.getOrderLabel(div);

                TemplateCanvas canvas = new TemplateCanvas();
                canvas.setCanvasLabel(label);

                TemplateResource resource = new TemplateResource();
                resource.setResourceLabel(label);

                String fileID = mets.getFile(div);
                String fileName = mets.getHref(fileID);
                String filePath = buildFilePath(fileName);

                setImageDimensions(filePath, canvas, resource);

                //get resource context from config
                String resourceContext = config.getResourceContext();
                //get integer identifier from filename
                Integer baseName = Integer.valueOf(getBaseName(fileName));
                //pad integer to 8 digits
                String resourceFileId = format("%08d", baseName);
                //canvasId = resourceId
                String canvasIdString = resourceContext + IIIF_CANVAS + "/" + resourceFileId;
                //cast canvas as IRI (failsafe)
                IRI canvasIri = buildCanvasIRI(canvasIdString);
                //set Canvas Id
                canvas.setCanvasId(canvasIri.getIRIString());
                //resource IRI (original source file extension required by client)
                //TODO coordinate with dereferenceable location / confirm file extention
                String resourceIdString = resourceContext + "/" + resourceFileId + ".jpg";
                //cast resource as IRI (failsafe)
                IRI resourceIri = buildResourceIRI(resourceIdString);
                //set resourceID
                resource.setResourceId(resourceIri.getIRIString());
                //set Image Service
                //TODO fix IIIP image service IRI format
                String imageServiceContext = buildImageServiceContext(config.getViewId());
                IRI serviceIRI = buildServiceIRI(imageServiceContext, resourceFileId);
                resource.setService(new TemplateService(serviceIRI.getIRIString()));

                TemplateImage image = new TemplateImage();
                image.setResource(resource);
                image.setTarget(canvas.getCanvasId());

                List<TemplateImage> images = new ArrayList<>();
                images.add(image);

                canvas.setCanvasImages(images);

                canvases.add(canvas);
            }

            List<TemplateSequence> sequence = addCanvasesToSequence(canvases);
            body.setSequences(sequence);

            TemplateTopStructure top = mets.buildTopStructure();
            List<TemplateStructure> subStructures = mets.buildStructures();

            TemplateStructureList list = new TemplateStructureList(top, subStructures);
            body.setStructures(list.getStructureList());

            final Optional<String> json = serialize(body);
            String output = json.orElse(null);
            String outputFile = config.getOutputFile();
            File outfile = new File(outputFile);
            writeToFile(output, outfile);
        }
    }
}
