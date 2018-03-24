package org.ubl.iiifproducer.template;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ImageMetadata {
    @JsonProperty
    private String filename;

    @JsonProperty
    private List<ImageMetadataDirectory> directories;

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setDirectories(List<ImageMetadataDirectory> directories) {
        this.directories = directories;
    }

    @JsonIgnore
    public String getFilename(){
        return this.filename;
    }
}
