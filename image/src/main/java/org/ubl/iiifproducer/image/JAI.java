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

package org.ubl.iiifproducer.image;

import static javax.imageio.ImageIO.createImageInputStream;
import static javax.imageio.ImageIO.getImageReaders;
import static javax.imageio.spi.IIORegistry.getDefaultInstance;
import static org.slf4j.LoggerFactory.getLogger;

import com.github.jaiimageio.impl.plugins.tiff.TIFFImageWriterSpi;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import org.slf4j.Logger;

/**
 * JAI.
 *
 * @author christopher-johnson
 */
public class JAI {
    private static Logger logger = getLogger(JAI.class);

    /**
     * registerAllServicesProviders.
     */
    private static void registerAllServicesProviders() {
        getDefaultInstance().registerServiceProvider(new TIFFImageWriterSpi());
    }

    /**
     * getImageDimensions.
     *
     * @param resourceFile File
     * @return Dimension
     */
    public static Dimension getImageDimensions(final File resourceFile) {
        registerAllServicesProviders();

        try (ImageInputStream in = createImageInputStream(resourceFile)) {
            final Iterator<ImageReader> readers = getImageReaders(in);
            if (readers.hasNext()) {
                final ImageReader reader = readers.next();
                try {
                    reader.setInput(in);
                    return new Dimension(reader.getWidth(0), reader.getHeight(0));
                } finally {
                    reader.dispose();
                }
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
