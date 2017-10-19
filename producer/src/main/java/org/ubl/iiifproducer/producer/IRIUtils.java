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

import static java.lang.Integer.valueOf;
import static java.lang.String.format;
import static org.ubl.iiifproducer.producer.Constants.IIIF_CANVAS;

/**
 * IRIUtils.
 *
 * @author christopher-johnson
 */
public class IRIUtils {
    private Config config;

    IRIUtils(Config config) {
        this.config = config;
    }

    public String buildCanvasIRIfromPhysical(String physical) {
        String resourceContext = config.getResourceContext();
        Integer newId = valueOf(physical.substring(physical.indexOf("_") + 1));
        return resourceContext + IIIF_CANVAS + "/" + format("%08d", newId);
    }
}
