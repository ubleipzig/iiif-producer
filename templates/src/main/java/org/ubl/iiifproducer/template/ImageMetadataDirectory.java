package org.ubl.iiifproducer.template;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ImageMetadataDirectory {
    @JsonProperty
    private String directory;

    @JsonProperty
    private List<ImageMetadataTag> tags;

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public void setTags(List<ImageMetadataTag> tags) {
        this.tags = tags;
    }
}
