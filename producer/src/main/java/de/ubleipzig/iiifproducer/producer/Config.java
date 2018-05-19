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

package de.ubleipzig.iiifproducer.producer;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

/**
 * Config.
 *
 * @author christopher-johnson
 */
public class Config {

    private String viewId;
    private String xmlFile;
    private String outputFile;
    private Boolean serializeImageManifest;
    private String imageSourceDir;

    @NotNull
    private String baseUrl;
    @NotNull
    private String viewerUrl;
    @NotNull
    private String katalogUrl;
    @NotNull
    private String imageServiceBaseUrl;
    @NotNull
    private String imageServiceImageDirPrefix;
    @NotNull
    private String imageServiceFileExtension;
    @NotNull
    private String manifestFilename;
    @NotNull
    private String defaultSequenceId;
    @NotNull
    private String canvasContext;
    @NotNull
    private String rangeContext;
    private String license;
    private String attributionLicenseNote;
    private String attributionKey;
    private String anchorKey;

    private String manifestHttpDir;
    private String imageManifestOutputDir;

    /**
     * @return String
     */
    public String getViewId() {
        return viewId;
    }

    /**
     * @param viewId String
     */
    public final void setViewId(final String viewId) {
        this.viewId = viewId;
    }

    /**
     * @return String
     */
    public String getResourceContext() {
        return this.baseUrl + viewId;
    }

    /**
     * @return String
     */
    public String getXmlFile() {
        return xmlFile;
    }

    /**
     * @param xmlFile String
     */
    public final void setXmlFile(final String xmlFile) {
        this.xmlFile = xmlFile;
    }

    /**
     * @param imageSourceDir String
     */
    public final void setImageSourceDir(final String imageSourceDir) {
        this.imageSourceDir = imageSourceDir;
    }

    /**
     * @return String
     */
    public String getImageSourceDir() {
        return imageSourceDir;
    }

    /**
     * @return String
     */
    public String getOutputFile() {
        return outputFile;
    }

    /**
     * @param outputFile String
     */
    public final void setOutputFile(final String outputFile) {
        this.outputFile = outputFile;
    }

    /**
     * @return Boolean
     */
    public Boolean getSerializeImageManifest() {
        return serializeImageManifest;
    }

    /**
     * @param serializeImageManifest Boolean
     */
    public final void setSerializeImageManifest(final Boolean serializeImageManifest) {
        this.serializeImageManifest = serializeImageManifest;
    }

    /**
     * @return baseUrl String
     */
    @JsonProperty
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * @param baseUrl String
     */
    @JsonProperty
    public void setBaseUrl(final String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * @return imageServiceBaseUrl String
     */
    @JsonProperty
    public String getImageServiceBaseUrl() {
        return imageServiceBaseUrl;
    }

    /**
     * @param imageServiceBaseUrl String
     */
    @JsonProperty
    public void setImageServiceBaseUrl(final String imageServiceBaseUrl) {
        this.imageServiceBaseUrl = imageServiceBaseUrl;
    }

    /**
     * @return viewerUrl
     */
    @JsonProperty
    public String getViewerUrl() {
        return viewerUrl;
    }

    /**
     * @param viewerUrl String
     */
    @JsonProperty
    public void setViewerUrl(final String viewerUrl) {
        this.viewerUrl = viewerUrl;
    }

    /**
     * @return katalogUrl String
     */
    @JsonProperty
    public String getKatalogUrl() {
        return katalogUrl;
    }

    /**
     * @param katalogUrl String
     */
    @JsonProperty
    public void setKatalogUrl(final String katalogUrl) {
        this.katalogUrl = katalogUrl;
    }

    /**
     * @return imageServiceUriPrefix
     */
    @JsonProperty
    public String getImageServiceImageDirPrefix() {
        return imageServiceImageDirPrefix;
    }

    /**
     * @param imageServiceImageDirPrefix String
     */
    @JsonProperty
    public void setImageServiceImageDirPrefix(final String imageServiceImageDirPrefix) {
        this.imageServiceImageDirPrefix = imageServiceImageDirPrefix;
    }

    /**
     * @return imageServiceFileExtension
     */
    @JsonProperty
    public String getImageServiceFileExtension() {
        return imageServiceFileExtension;
    }

    /**
     * @param imageServiceFileExtension String
     */
    @JsonProperty
    public void setImageServiceFileExtension(final String imageServiceFileExtension) {
        this.imageServiceFileExtension = imageServiceFileExtension;
    }

    /**
     * @return manifestFilename
     */
    @JsonProperty
    public String getManifestFilename() {
        return manifestFilename;
    }

    /**
     * @param manifestFilename String
     */
    @JsonProperty
    public void setManifestFilename(final String manifestFilename) {
        this.manifestFilename = manifestFilename;
    }

    /**
     * @return defaultSequenceId
     */
    @JsonProperty
    public String getDefaultSequenceId() {
        return defaultSequenceId;
    }

    /**
     * @param defaultSequenceId String
     */
    @JsonProperty
    public void setDefaultSequenceId(final String defaultSequenceId) {
        this.defaultSequenceId = defaultSequenceId;
    }

    /**
     * @return canvasContext
     */
    @JsonProperty
    public String getCanvasContext() {
        return canvasContext;
    }

    /**
     * @param canvasContext String
     */
    @JsonProperty
    public void setCanvasContext(final String canvasContext) {
        this.canvasContext = canvasContext;
    }

    /**
     * @return rangeContext
     */
    @JsonProperty
    public String getRangeContext() {
        return rangeContext;
    }

    /**
     * @param rangeContext String
     */
    @JsonProperty
    public void setRangeContext(final String rangeContext) {
        this.rangeContext = rangeContext;
    }

    /**
     * @return license
     */
    @JsonProperty
    public String getLicense() {
        return license;
    }

    /**
     * @param license String
     */
    @JsonProperty
    public void setLicense(final String license) {
        this.license = license;
    }

    /**
     * @return attributionLicenseNote
     */
    @JsonProperty
    public String getAttributionLicenseNote() {
        return attributionLicenseNote;
    }

    /**
     * @param attributionLicenseNote String
     */
    @JsonProperty
    public void setAttributionLicenseNote(final String attributionLicenseNote) {
        this.attributionLicenseNote = attributionLicenseNote;
    }

    /**
     * @return attributionKey
     */
    @JsonProperty
    public String getAttributionKey() {
        return attributionKey;
    }

    /**
     * @param attributionKey String
     */
    @JsonProperty
    public void setAttributionKey(final String attributionKey) {
        this.attributionKey = attributionKey;
    }

    /**
     * @return anchorKey
     */
    @JsonProperty
    public String getAnchorKey() {
        return anchorKey;
    }

    /**
     * @param anchorKey String
     */
    @JsonProperty
    public void setAnchorKey(final String anchorKey) {
        this.anchorKey = anchorKey;
    }

    /**
     * @return manifestHttpDir
     */
    @JsonProperty
    public String getManifestHttpDir() {
        return manifestHttpDir;
    }

    /**
     * @param manifestHttpDir String
     */
    @JsonProperty
    public void setManifestHttpDir(final String manifestHttpDir) {
        this.manifestHttpDir = manifestHttpDir;
    }

    /**
     * @return imageManifestOutputDir
     */
    @JsonProperty
    public String getImageManifestOutputDir() {
        return imageManifestOutputDir;
    }

    /**
     * @param imageManifestOutputDir String
     */
    @JsonProperty
    public void setImageManifestOutputDir(final String imageManifestOutputDir) {
        this.imageManifestOutputDir = imageManifestOutputDir;
    }
}
