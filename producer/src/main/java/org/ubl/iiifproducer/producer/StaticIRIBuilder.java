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


    public static IRI buildCanvasIRI(String canvasIdString) {
        return createIRI(canvasIdString);
    }

    public static IRI buildResourceIRI(String resourceIdString) {
        return createIRI(resourceIdString);
    }

    public static IRI buildServiceIRI(String imageServiceContext, String resourceIdString) {
        return createIRI(imageServiceContext + "/" + resourceIdString + IMAGE_SERVICE_FILE_EXT);
    }

    public static String buildImageServiceContext(String viewId) {
        int viewIdInt = parseInt(viewId);
        String imageDir = IMAGE_DIR;
        StringBuilder newImageDir = new StringBuilder(Integer.toString(viewIdInt / 100));

        while (newImageDir.length() < 4) {
               newImageDir.insert(0, "0");
        }

        String hack = imageDir.split("/")[0] + "/" + imageDir.split("/")[1] + "/" + imageDir.split(
                      "/")[2];

         return SERVICE_BASE + hack + "/" + newImageDir + "/" + viewId;
        //return SERVICE_BASE + IMAGE_DIR + viewId;
    }
}
