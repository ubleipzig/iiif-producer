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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.simple.SimpleRDF;

import java.io.File;
import java.util.UUID;

import static java.io.File.separator;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.valueOf;
import static java.lang.String.format;

/**
 * IRIBuilder.
 *
 * @author christopher-johnson
 */
@Builder
@Setter
@Getter
@AllArgsConstructor
public final class IRIBuilder {

    private static final RDF rdf = new SimpleRDF();
    @Builder.Default
    private String annotationContext = "/anno";
    @Builder.Default
    private String canvasContext = "/canvas";
    @Builder.Default
    private String imageServiceBaseUrl = "https://iiif.ub.uni-leipzig.de/iiif";
    @Builder.Default
    private String imageServiceFileExtension = ".jpx";
    @Builder.Default
    private String imageServiceImageDirPrefix = "/j2k/";
    @Builder.Default
    private boolean isUBLImageService = true;
    private String resourceContext;

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
     * @param resourceIdString    String
     * @return IRI
     */
    public IRI buildServiceIRI(final String imageServiceContext, final String resourceIdString) {
        return rdf.createIRI(
                imageServiceContext + separator + resourceIdString + imageServiceFileExtension);
    }

    /**
     * @param viewId String
     * @return String
     */
    public String buildImageServiceContext(final String viewId) {
        final int viewIdInt = parseInt(viewId);
        final String v = format("%010d", viewIdInt);
        final String imageDirPrefix = imageServiceImageDirPrefix;
        final int part1 = parseInt(v.substring(0, 4));
        final String first = format("%04d", part1);
        final int part2 = parseInt(v.substring(5, 8));
        final String second = format("%04d", part2);
        if (isUBLImageService) {
            return imageServiceBaseUrl + imageDirPrefix + first + separator + second + separator + v;
        } else {
            return imageServiceBaseUrl + separator + viewId;
        }
    }

    /**
     * @param physical String
     * @return String
     */
    public String buildCanvasIRIfromPhysical(final String physical) {
        if (physical.matches("^.+_\\d+$")) {
            // XML from Kitodo 2, IDs like PHYS_0001
            final Integer newId = valueOf(physical.substring(physical.indexOf("_") + 1));
            return resourceContext + canvasContext + File.separator + format("%08d", newId);
        } else {
            // XML from Kitodo 3 or other, could be a UUID or something else
            return resourceContext + canvasContext + File.separator + physical;
        }
    }

    /**
     * @return String
     */
    public String buildAnnotationId() {
        return resourceContext + annotationContext + File.separator + UUID.randomUUID();
    }
}
