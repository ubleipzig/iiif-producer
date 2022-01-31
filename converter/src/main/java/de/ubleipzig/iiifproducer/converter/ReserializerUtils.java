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

public final class ReserializerUtils {

    private ReserializerUtils() {
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
