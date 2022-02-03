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
/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.ubleipzig.iiifproducer.converter;

/**
 * Constants.
 */
public final class DomainConstants {
    static final String IIPSRV_DEFAULT = "fcgi-bin/iipsrv.fcgi?iiif=";
    static final String METS_PROFILE = "http://www.loc.gov/METS/";
    public static String domainAttribution = "Provided by Leipzig University Library<br/>No Copyright - Public " +
            "Domain" + " Marked";
    public static String domainLogo = "https://iiif.ub.uni-leipzig.de/ubl-logo.png";
    public static String domainLicense = "https://creativecommons.org/publicdomain/mark/1.0/";
    static String baseUrl = "https://iiif.ub.uni-leipzig.de/";
    static String manifestBase = "manifest";
    static String sequenceBase = "sequence";
    static String targetBase = "canvas";
    static String structureBase = "range";
    static String annotationBase = "anno";
    static String annotationPageBase = "page";
    static String viewerUrl = "https://digital.ub.uni-leipzig.de/object/viewid/";
    static String katalogUrl = "https://katalog.ub.uni-leipzig.de/urn/";
    static String IIIF_SERVICE_PROFILE = "http://iiif.io/api/image/2/level1.json";
    static String IIIF_SERVICE_TYPE = "ImageService2";
    static String SEE_ALSO_PROFILE = "https://www.ub.uni-leipzig.de/profiles/application/v3";
    static String IIIF_VERSION3_CONTEXT = "http://iiif.io/api/presentation/3/context.json";
    static String WEB_ANNOTATION_CONTEXT = "http://www.w3.org/ns/anno.jsonld";

    private DomainConstants() {
    }
}
