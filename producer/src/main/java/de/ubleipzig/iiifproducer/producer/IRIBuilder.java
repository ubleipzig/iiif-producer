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

import static java.io.File.separator;
import static java.lang.Integer.parseInt;

import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.simple.SimpleRDF;

/**
 * IRIBuilder.
 *
 * @author christopher-johnson
 */
public final class IRIBuilder {

    private Config config;
    private static final RDF rdf = new SimpleRDF();

    /**
     * @param config Config
     */
    public IRIBuilder(final Config config) {
        this.config = config;
    }

    /**
     * @param canvasIdString String
     * @return IRI
     */
    public IRI buildCanvasIRI(final String canvasIdString) {
        return rdf.createIRI(canvasIdString);
    }

    /**
     * @param resourceIdString String
     * @return IRI
     */
    public IRI buildResourceIRI(final String resourceIdString) {
        return rdf.createIRI(resourceIdString);
    }

    /**
     * @param imageServiceContext String
     * @param resourceIdString String
     * @return IRI
     */
    public IRI buildServiceIRI(final String imageServiceContext, final String resourceIdString) {
        return rdf.createIRI(
                imageServiceContext + separator + resourceIdString + config.getImageServiceFileExtension());
    }

    /**
     * @return String
     */
    public String buildImageServiceContext() {
        final int viewIdInt = parseInt(config.getViewId());
        final String imageDirPrefix = config.getImageServiceImageDirPrefix();
        final StringBuilder newImageDir = new StringBuilder(Integer.toString(viewIdInt / 100));

        while (newImageDir.length() < 4) {
            newImageDir.insert(0, "0");
        }

        return config.getImageServiceBaseUrl() + imageDirPrefix + newImageDir + separator + config.getViewId();
        //return SERVICE_BASE + IMAGE_DIR + viewId;
    }

    private IRIBuilder() {
    }
}
