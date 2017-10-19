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

package org.ubl.iiifproducer.doc;

import static org.apache.log4j.Logger.getLogger;
import static org.ubl.iiifproducer.doc.MetsManifestBuilder.getMetsFromFile;

import java.io.File;
import java.io.IOException;
import org.apache.log4j.Logger;

/**
 * ResourceLoader.
 *
 * @author christopher-johnson
 */
public class ResourceLoader {
    private static Logger logger = getLogger(ResourceLoader.class);

    public static MetsData getMets(String sourceFile) throws IOException {
        return getMetsFromFile(sourceFile);
    }

    public static MetsData getMetsAnchor(String sourceFile) throws IOException {
        File metsFile = new File(sourceFile);
        if (metsFile.exists()) {
            String anchorPath;
            anchorPath = metsFile.getParent() + "_anchor.xml";
            File anchorfile = new File(anchorPath);
            if (anchorfile.exists()) {
                return getMetsFromFile(anchorfile);
            }
        }
        return null;
    }
}
