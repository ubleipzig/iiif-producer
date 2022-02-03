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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.ubleipzig.iiifproducer.converter.DomainConstants.baseUrl;
import static de.ubleipzig.iiifproducer.converter.DomainConstants.targetBase;
import static java.io.File.separator;
import static java.lang.String.format;

public final class ConverterUtils {

    private ConverterUtils() {
    }

    public static Map<String, List<String>> buildLabelMap(final String value, String language) {
        final List<String> labelList = new ArrayList<>();
        labelList.add(value);
        final Map<String, List<String>> labelMap = new HashMap<>();
        labelMap.put(language, labelList);
        return labelMap;
    }

    public static List<String> buildPaddedCanvases(final List<String> canvases, final String viewId) {
        final List<String> paddedCanvases = new ArrayList<>();
        canvases.forEach(c -> {
            try {
                final String paddedCanvasId = format("%08d", Integer.valueOf(new URL(c).getPath().split(separator)[3]));
                final String canvas = baseUrl + viewId + separator + targetBase + separator + paddedCanvasId;
                paddedCanvases.add(canvas);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        });
        return paddedCanvases;
    }
}
