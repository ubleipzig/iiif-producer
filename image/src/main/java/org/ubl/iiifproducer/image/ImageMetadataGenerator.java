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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
import org.ubl.iiifproducer.image.templates.ImageDimensionManifest;
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
    private static Logger log = getLogger(ImageMetadataGenerator.class);
    private FileBinaryService service = new FileBinaryService();
    private ImageMetadataGeneratorConfig imageMetadataGeneratorConfig;

    /**
     * ImageMetadataGenerator.
     *
     * @param imageMetadataGeneratorConfig imageMetadataGeneratorConfig
     */
    public ImageMetadataGenerator(final ImageMetadataGeneratorConfig imageMetadataGeneratorConfig) {
        this.imageMetadataGeneratorConfig = imageMetadataGeneratorConfig;
    }

    /**
     * buildImageMetadataManifest.
     *
     * @return String
     */
    public String buildImageMetadataManifest() {
        final String collection = imageMetadataGeneratorConfig.getImageSourceDir();
        try {
            final Stream<Path> paths = Files.walk(Paths.get(collection)).filter(Files::isRegularFile);
            final ImageMetadataManifest manifest = new ImageMetadataManifest();
            manifest.setCollection(collection);
            final List<ImageMetadata> imageMetadataList = new ArrayList<>();
            paths.forEach(p -> {
                final URI uri = p.toUri();
                final ImageMetadata im = new ImageMetadata();
                final File file = new File(String.valueOf(p.toAbsolutePath()));
                try {
                    final String filename = file.getName();
                    final InputStream targetStream = new FileInputStream(file);
                    final String digest = service.digest("SHA-1", targetStream).orElse(null);
                    im.setDigest(digest);
                    im.setFilename(filename);
                    log.debug("Setting Digest {} for filename {}", digest, filename);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                Metadata metadata = null;
                try {
                    metadata = ImageMetadataReader.readMetadata(uri.toURL().openStream());
                    log.debug("Reading Metatdata from filename {}", file.getName());
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
            log.debug("Serializing Image Manifest to Json");
            final Optional<String> json = serialize(manifest);
            return json.orElse(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * buildDimensionManifest.
     *
     * @param imageManifest String
     * @return List
     */
    public ImageDimensionManifest buildDimensionManifest(final String imageManifest) {
        try {
            final ImageDimensionManifest dimManifest = new ImageDimensionManifest();
            final String metadataFilePath = imageMetadataGeneratorConfig.getMetadataFilePath();
            final byte[] body;
            final String manifestString;
            if (metadataFilePath != null) {
                body = Files.readAllBytes(Paths.get(metadataFilePath));
            } else {
                body = imageManifest.getBytes();
            }
            manifestString = new String(Objects.requireNonNull(body));
            final ImageMetadataManifest manifest = MAPPER.readValue(
                    manifestString, new TypeReference<ImageMetadataManifest>() {
                    });
            final String collection = manifest.getCollection();
            dimManifest.setCollection(collection);
            final List<ImageDimensions> dimList = new ArrayList<>();
            final List<ImageMetadata> imageList = manifest.getImageMetadata();
            imageList.forEach(i -> {
                final ImageDimensions dims = new ImageDimensions();
                dims.setFilename(i.getFilename());
                dims.setDigest(i.getDigest());
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
            dimManifest.setImageMetadata(dimList);
            return dimManifest;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * buildDimensionManifestFromFile.
     *
     * @return List
     */
    public List<ImageDimensions> buildDimensionManifestFromFile() {
        try {
            final byte[] body = Files.readAllBytes(
                    Paths.get(imageMetadataGeneratorConfig.getDimensionManifestFilePath()));
            final String manifestString = new String(body);
            final ImageDimensionManifest dimManifest = MAPPER.readValue(
                    manifestString, new TypeReference<ImageDimensionManifest>() {
                    });
            return dimManifest.getImageMetadata();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * buildDimensionManifest.
     *
     * @return List
     */
    public ImageDimensionManifest build() {
        final String imageManifest = buildImageMetadataManifest();
        return buildDimensionManifest(imageManifest);
    }

}
