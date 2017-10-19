/*
 * IIIFProducer
 *
 * Copyright (C) 2017 Leipzig University Library <info@ub.uni-leipzig.de>
 *
 * @author Stefan Freitag <freitag@uni-leipzig.de>
 * @author Christopher Johnson <christopher_hanna.johnson@uni-leipzig.de>
 * @author Felix Kreißig <kreissig@ub.uni-leipzig.de>
 * @author Leander Seige <seige@ub.uni-leipzig.de>
 * @license http://opensource.org/licenses/gpl-2.0.php GNU GPLv2
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.ubl.iiifproducer.producer;

/**
 * Constants.
 *
 * @author christopher-johnson
 */
final class Constants {

    static final String BASE_URL = "https://iiif.ub.uni-leipzig.de/";
    static final String SERVICE_BASE = "https://iiif.ub.uni-leipzig.de/fcgi-bin/iipsrv.fcgi?iiif=";
    static final String VIEWER_URL = "https://digital.ub.uni-leipzig.de/object/viewid/";
    static final String IMAGE_DIR = "/j2k/0000/0000/";
    static final String IMAGE_SERVICE_FILE_EXT = ".jpx";
    static final String MANIFEST_FILENAME = "manifest.json";
    static final String SEQUENCE_ID = "/sequence/1";
    static final String IIIF_CANVAS = "/canvas";
    static final String IIIF_RANGE = "/range";
    static final String ATTRIBUTION_KEY = "Provided by ";
    static final String ANCHOR_KEY = "Part of";

    private Constants() {
    }
}
