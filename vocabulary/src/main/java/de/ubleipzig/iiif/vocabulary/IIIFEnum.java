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

package de.ubleipzig.iiif.vocabulary;

/**
 * IIIFEnum.
 */
public enum IIIFEnum {

    IMAGE_CONTEXT("http://iiif.io/api/image/2/context.json"), SERVICE_PROFILE("http://iiif.io/api/image/2/level1.json");

    private String IRIString;

    IIIFEnum(final String IRIString) {
        this.IRIString = IRIString;
    }

    /**
     * @return String
     */
    public String IRIString() {
        return IRIString;
    }
}
