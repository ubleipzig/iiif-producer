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

import static java.io.File.separator;
import static java.lang.Integer.parseInt;
import static org.ubl.iiifproducer.producer.Constants.IMAGE_DIR;
import static org.ubl.iiifproducer.producer.Constants.IMAGE_SERVICE_FILE_EXT;
import static org.ubl.iiifproducer.producer.Constants.SERVICE_BASE;

import org.apache.commons.rdf.api.IRI;

/**
 * StaticIRIBuilder.
 *
 * @author christopher-johnson
 */
public class StaticIRIBuilder extends RDFBase {

    /**
     * @param canvasIdString String
     * @return IRI
     */
    public static IRI buildCanvasIRI(final String canvasIdString) {
        return createIRI(canvasIdString);
    }

    /**
     * @param resourceIdString String
     * @return IRI
     */
    public static IRI buildResourceIRI(final String resourceIdString) {
        return createIRI(resourceIdString);
    }

    /**
     * @param imageServiceContext String
     * @param resourceIdString String
     * @return IRI
     */
    public static IRI buildServiceIRI(final String imageServiceContext, final String resourceIdString) {
        return createIRI(imageServiceContext + "/" + resourceIdString + IMAGE_SERVICE_FILE_EXT);
    }

    /**
     * @param viewId String
     * @return String
     */
    public static String buildImageServiceContext(final String viewId) {
        final int viewIdInt = parseInt(viewId);
        final String imageDir = IMAGE_DIR;
        final StringBuilder newImageDir = new StringBuilder(Integer.toString(viewIdInt / 100));

        while (newImageDir.length() < 4) {
            newImageDir.insert(0, "0");
        }

        final String hack =
                imageDir.split(separator)[0] + separator + imageDir.split(separator)[1] + separator + imageDir.split(
                        separator)[2];

        return SERVICE_BASE + hack + separator + newImageDir + separator + viewId;
        //return SERVICE_BASE + IMAGE_DIR + viewId;
    }
}
