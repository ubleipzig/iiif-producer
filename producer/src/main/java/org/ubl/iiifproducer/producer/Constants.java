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

/**
 * Constants.
 *
 * @author christopher-johnson
 */
final class Constants {

    static final String BASE_URL = "https://iiif.ub.uni-leipzig.de/";
    static final String SERVICE_BASE = "http://localhost:5000";
    static final String VIEWER_URL = "https://digital.ub.uni-leipzig.de/object/viewid/";
    static final String IMAGE_DIR = "/iiif/";
    static final String IMAGE_SERVICE_FILE_EXT = ".tif";
    static final String MANIFEST_FILENAME = "manifest.json";
    static final String SEQUENCE_ID = "/sequence/1";
    static final String IIIF_CANVAS = "/canvas";
    static final String IIIF_RANGE = "/range";
    static final String ATTRIBUTION_KEY = "Provided by ";
    static final String ANCHOR_KEY = "Part of";
    static final String MANIFEST_HTTP_DIR =
            "/home/christopher/IdeaProjects/manifest-service/public/";
    private Constants() {
    }
}
