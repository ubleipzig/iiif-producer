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

import java.util.List;

import org.ubl.iiifproducer.template.TemplateBody;
import org.ubl.iiifproducer.template.TemplateCanvas;
import org.ubl.iiifproducer.template.TemplateResource;
import org.ubl.iiifproducer.template.TemplateSequence;

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
     *
     * @param body TemplateBody
     */
    void setContext(TemplateBody body);

    /**
     *
     * @param body TemplateBody
     */
    void setId(TemplateBody body);

    /**
     *
     * @param body TemplateBody
     */
    void setRelated(TemplateBody body);

    /**
     *
     * @param canvases List
     * @return List
     */
    List<TemplateSequence> addCanvasesToSequence(List<TemplateCanvas> canvases);

    /**
     *
     * @param filename String
     * @return String
     */
    String buildFilePath(String filename);

    /**
     *
     */
    void buildManifest();

    /**
     *
     * @param filePath String
     * @param canvas TemplateCanvas
     * @param resource TemplateResource
     */
    void setImageDimensions(String filePath, TemplateCanvas canvas, TemplateResource resource);
}
