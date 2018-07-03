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
import static java.lang.Integer.valueOf;
import static java.lang.String.format;

import java.io.File;
import java.util.UUID;

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
        final String v = format("%010d", viewIdInt);
        final String imageDirPrefix = config.getImageServiceImageDirPrefix();
        final int part1 = parseInt(v.substring(0,4));
        final String first = format("%04d", part1);
        final int part2 = parseInt(v.substring(5,8));
        final String second = format("%04d", part2);
        if (config.getIsUBLImageService()) {
            return config.getImageServiceBaseUrl() + imageDirPrefix + first + separator + second + separator + v;
        } else {
            return config.getImageServiceBaseUrl() + config.getViewId();
        }
    }

    /**
     * @param physical String
     * @return String
     */
    public String buildCanvasIRIfromPhysical(final String physical) {
        final String resourceContext = config.getResourceContext();
        final Integer newId = valueOf(physical.substring(physical.indexOf("_") + 1));
        return resourceContext + config.getCanvasContext() + File.separator + format("%08d", newId);
    }

    /**
     * @return String
     */
    public String buildAnnotationId() {
        final String resourceContext = config.getResourceContext();
        return resourceContext + config.getAnnotationContext() + File.separator + UUID.randomUUID();
    }

    private IRIBuilder() {
    }
}
