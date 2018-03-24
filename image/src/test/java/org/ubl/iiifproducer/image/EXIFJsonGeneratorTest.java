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

import static java.nio.file.Paths.get;
import static org.ubl.iiifproducer.template.ManifestSerializer.serialize;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

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

import org.junit.jupiter.api.Test;
import org.ubl.iiifproducer.template.ImageMetadata;
import org.ubl.iiifproducer.template.ImageMetadataDirectory;
import org.ubl.iiifproducer.template.ImageMetadataTag;

public class EXIFJsonGeneratorTest {
    private String path = get(".").toAbsolutePath().normalize().getParent().toString();
    private String imageSourceDir = path + "/image/src/test/resources/";

    @Test
    void getImageMetadata() {
        try {
            final Stream<Path> paths = Files.walk(Paths.get(imageSourceDir)).filter(Files::isRegularFile);
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
            final Optional<String> json = serialize(imageMetadataList);
            final String output = json.orElse(null);
            System.out.println(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
