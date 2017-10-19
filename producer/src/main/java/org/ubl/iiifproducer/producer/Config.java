/*
 * IIIFProducer
 *
 * Copyright (C) 2017 Leipzig University Library <info@ub.uni-leipzig.de>
 *
 * @author Stefan Freitag <freitag@uni-leipzig.de>
 * @author Christopher Johnson <christopher_hanna.johnson@uni-leipzig.de>
 * @author Felix Kreißig <kreissig@ub.uni-leipzig.de>
 * @author Leander Seige <seige@ub.uni-leipzig.de>
 * @license http://opensource.org/licenses/gpl-2.0.php GNU GPLv2
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
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
