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

import static org.slf4j.LoggerFactory.getLogger;
import static org.ubl.iiifproducer.image.JsonSerializer.serialize;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.ubl.iiifproducer.image.templates.ImageDimensions;
import org.ubl.iiifproducer.image.templates.ImageMetadata;
import org.ubl.iiifproducer.image.templates.ImageMetadataDirectory;
import org.ubl.iiifproducer.image.templates.ImageMetadataManifest;
import org.ubl.iiifproducer.image.templates.ImageMetadataTag;

/**
 * ImageMetadataGenerator.
 *
 * @author christopher-johnson
 */
public class ImageMetadataGenerator {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static Logger logger = getLogger(ImageMetadataGenerator.class);
    private String imageSourceDir;

    /**
     *
     * @param imageSourceDir String
     */
    public ImageMetadataGenerator(final String imageSourceDir) {
        this.imageSourceDir = imageSourceDir;
    }

    /**
     *
     * @return String
     */
    public String buildImageMetadataManifest() {
        try {
            final Stream<Path> paths = Files.walk(Paths.get(imageSourceDir)).filter(Files::isRegularFile);
            final ImageMetadataManifest manifest = new ImageMetadataManifest();
            final List<ImageMetadata> imageMetadataList = new ArrayList<>();
            paths.forEach(p -> {
                final URI uri = p.toUri();
                final ImageMetadata im = new ImageMetadata();
                final File file = new File(String.valueOf(p.toAbsolutePath()));
                im.setFilename(file.getName());
                Metadata metadata = null;
                try {
                    metadata = ImageMetadataReader.readMetadata(uri.toURL().openStream());
                } catch (ImageProcessingException | IOException e) {
                    e.printStackTrace();
                }
                final List<ImageMetadataDirectory> md = new ArrayList<>();
                Objects.requireNonNull(metadata).getDirectories().forEach(d -> {
                    final ImageMetadataDirectory imdir = new ImageMetadataDirectory();
                    imdir.setDirectory(d.getName());
                    md.add(imdir);
                    final List<ImageMetadataTag> mt = new ArrayList<>();
                    final Collection<Tag> tags = d.getTags();
                    tags.forEach(t -> {
                        final ImageMetadataTag imtag = new ImageMetadataTag();
                        imtag.setTagName(t.getTagName());
                        imtag.setTagDescription(t.getDescription());
                        mt.add(imtag);
                    });
                    imdir.setTags(mt);
                });
                im.setDirectories(md);
                imageMetadataList.add(im);
            });
            imageMetadataList.sort(Comparator.comparing(ImageMetadata::getFilename));
            manifest.setImageMetadata(imageMetadataList);
            final Optional<String> json = serialize(manifest);
            return json.orElse(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @return List
     */
    public List<ImageDimensions> buildImageDimensions() {
        final String body = buildImageMetadataManifest();
        try {
            final ImageMetadataManifest manifest = MAPPER.readValue(body, new TypeReference<ImageMetadataManifest>() {
            });
            final List<ImageDimensions> dimList = new ArrayList<>();
            final List<ImageMetadata> imageList = manifest.getImageMetadata();
            imageList.forEach(i -> {
                final ImageDimensions dims = new ImageDimensions();
                dims.setFilename(i.getFilename());
                final List<ImageMetadataDirectory> dirList = i.getDirectories();
                dirList.forEach(d -> {
                    final List<ImageMetadataTag> tagList = d.getTags();
                    tagList.forEach(t -> {
                        final String tagName = t.getTagName();
                        if (tagName.equals("Image Height")) {
                            final String[] parts = t.getTagDescription().split(" ");
                            final String height = parts[0];
                            dims.setHeight(Integer.parseInt(height));
                        }

                        if (tagName.equals("Image Width")) {
                            final String[] parts = t.getTagDescription().split(" ");
                            final String width = parts[0];
                            dims.setWidth(Integer.parseInt(width));
                        }
                    });
                });
                dimList.add(dims);
            });
            return dimList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
