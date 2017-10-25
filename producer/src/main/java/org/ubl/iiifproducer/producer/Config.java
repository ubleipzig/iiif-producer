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

package org.ubl.iiifproducer.producer;

import static org.ubl.iiifproducer.producer.Constants.BASE_URL;

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
    private Boolean useSQL = false;

    public String getViewId() {
        return viewId;
    }

    public final void setViewId(String viewId) {
        this.viewId = viewId;
    }

    public String getResourceContext() {
        return BASE_URL + viewId;
    }

    public String getTitle() {
        return title;
    }

    public final void setTitle(String title) {
        this.title = title;
    }

    public String getInputFile() {
        return inputFile;
    }

    public final void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public String getBaseDir() {
        File source = new File(inputFile);
        if (source.exists()) {
            return source.getParent();
        }
        return null;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public final void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    public Boolean useSQL() {
        return useSQL;
    }

}
