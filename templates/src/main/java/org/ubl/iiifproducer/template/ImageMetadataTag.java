package org.ubl.iiifproducer.template;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImageMetadataTag {

    @JsonProperty
    String tagName;

    @JsonProperty
    String tagDescription;

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public void setTagDescription(String tagDescription) {
        this.tagDescription = tagDescription;
    }

}
