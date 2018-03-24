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

package org.ubl.iiifproducer.doc;

import static java.io.File.separator;
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
public final class ResourceLoader {
    private static Logger logger = getLogger(ResourceLoader.class);

    private ResourceLoader() { }

    /**
     *
     * @param sourceFile String
     * @return MetsData
     * @throws IOException IOException
     */
    public static MetsData getMets(final String sourceFile) throws IOException {
        return getMetsFromFile(sourceFile);
    }

    /**
     *
     * @param sourceFileUri String
     * @return MetsData
     * @throws IOException IOException
     */
    public static MetsData getMetsAnchor(final String sourceFileUri) throws IOException {
        final File metsFile = new File(sourceFileUri);
        if (metsFile.exists()) {
            final String anchorFileName = metsFile.getName();
            final int pos = anchorFileName.lastIndexOf(".");
            String anchorBaseName = null;
            if (pos > 0) {
                anchorBaseName = anchorFileName.substring(0, pos);
            }
            final String anchorPath = metsFile.getParent() + separator + anchorBaseName + "_anchor.xml";
            final File anchorfile = new File(anchorPath);
            if (anchorfile.exists()) {
                return getMetsFromFile(anchorfile);
            }
        }
        return null;
    }
}
