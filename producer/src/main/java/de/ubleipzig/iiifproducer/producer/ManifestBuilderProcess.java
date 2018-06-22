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

import de.ubleipzig.iiifproducer.template.TemplateCanvas;
import de.ubleipzig.iiifproducer.template.TemplateManifest;
import de.ubleipzig.iiifproducer.template.TemplateResource;
import de.ubleipzig.iiifproducer.template.TemplateSequence;
import de.ubleipzig.image.metadata.templates.ImageDimensions;

import java.util.List;

/**
 * ManifestBuilderProcess.
 *
 * @author christopher-johnson
 */
public interface ManifestBuilderProcess {

    /**
     *
     */
    void run();

    /**
     * @param body TemplateManifest
     */
    void setContext(TemplateManifest body);

    /**
     * @param body TemplateManifest
     */
    void setId(TemplateManifest body);

    /**
     * @param urn urn
     * @param body TemplateManifest
     */
    void setRelated(TemplateManifest body, String urn);

    /**
     * @param canvases List
     * @return List
     */
    List<TemplateSequence> addCanvasesToSequence(List<TemplateCanvas> canvases);

    /**
     *
     */
    void buildManifest();

    /**
     * @param dimensions ImageDimensions
     * @param canvas TemplateCanvas
     * @param resource TemplateResource
     */
    void setImageDimensions(ImageDimensions dimensions, TemplateCanvas canvas, TemplateResource resource);

    /**
     * @param imageManifestOutputPath imageManifestOutputPath
     * @param imageSourceDir imageSourceDir
     * @return List
     */
    List<ImageDimensions> getImageDimensions(String imageSourceDir, String imageManifestOutputPath);

    /**
     * @param imageManifestUrl imageManifestUrl
     * @return List
     */
    List<ImageDimensions> getImageDimensionsFromUrl(String imageManifestUrl);
}
