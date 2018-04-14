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

package de.ubleipzig.iiifproducer.doc;

import static de.ubleipzig.iiifproducer.doc.MetsManifestBuilder.getMetsFromFile;
import static java.io.File.separator;
import static org.apache.commons.io.FilenameUtils.getBaseName;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;

/**
 * ResourceLoader.
 *
 * @author christopher-johnson
 */
public final class ResourceLoader {

    private static Logger logger = getLogger(ResourceLoader.class);

    private ResourceLoader() {
    }

    /**
     * @param sourceFile String
     * @return MetsData
     * @throws IOException IOException
     */
    public static MetsData getMets(final String sourceFile) throws IOException {
        logger.debug("Loading MetsData from File {}", sourceFile);
        return getMetsFromFile(sourceFile);
    }

    /**
     * @param sourceFileUri String
     * @return MetsData
     * @throws IOException IOException
     */
    public static MetsData getMetsAnchor(final String sourceFileUri) throws IOException {
        final File metsFile = new File(sourceFileUri);
        if (metsFile.exists()) {
            final String baseFileName = getBaseName(metsFile.getName());
            final String anchorFileName = baseFileName + "_anchor.xml";
            final String anchorFilePath = metsFile.getParent() + separator + anchorFileName;
            final File anchorfile = new File(anchorFilePath);
            if (anchorfile.exists()) {
                return getMetsFromFile(anchorfile);
            }
        }
        return null;
    }
}
