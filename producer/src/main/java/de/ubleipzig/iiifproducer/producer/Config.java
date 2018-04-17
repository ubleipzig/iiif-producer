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

import static de.ubleipzig.iiifproducer.producer.Constants.BASE_URL;

import java.io.File;

/**
 * Config.
 *
 * @author christopher-johnson
 */
public class Config {

    private String viewId;
    private String title;
    private String inputFile;
    private String outputFile;
    private Boolean serializeImageManifest;

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
        return BASE_URL + viewId;
    }

    /**
     * @return String
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title String
     */
    public final void setTitle(final String title) {
        this.title = title;
    }

    /**
     * @return String
     */
    public String getInputFile() {
        return inputFile;
    }

    /**
     * @param inputFile String
     */
    public final void setInputFile(final String inputFile) {
        this.inputFile = inputFile;
    }

    /**
     * @return String
     */
    public String getBaseDir() {
        final File source = new File(inputFile);
        if (source.exists()) {
            return source.getParent();
        }
        return null;
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
}
