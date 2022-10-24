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

package de.ubleipzig.iiifproducer.converter;

import de.ubleipzig.iiifproducer.model.v2.Manifest;
import de.ubleipzig.iiifproducer.model.v3.Homepage;
import de.ubleipzig.iiifproducer.model.v3.MetadataVersion3;
import de.ubleipzig.iiifproducer.model.v3.SeeAlso;
import lombok.Builder;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static de.ubleipzig.iiifproducer.converter.DomainConstants.*;
import static de.ubleipzig.iiifproducer.model.v3.TypeConstants.DATASET;
import static de.ubleipzig.iiifproducer.model.v3.TypeConstants.TEXT;
import static java.io.File.separator;
import static java.lang.String.format;

@Builder
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


    public List<Homepage> buildHomepages(List<String> related) {
        final ArrayList<Homepage> homepages = new ArrayList<>();
        for (String rel : related) {
            if (!rel.endsWith(".json") && !rel.endsWith(".xml")) {
                final Homepage homepage = Homepage.builder()
                        .id(rel)
                        .format("text/html")
                        .type(TEXT)
                        .build();
                homepages.add(homepage);
            }
        }
        return homepages;
    }

    public List<SeeAlso> buildSeeAlso(final String viewId, final String urn, List<String> related) {
        String katalogId = katalogUrl + urn;
        String viewerId = viewerUrl + viewId;
        final ArrayList<SeeAlso> seeAlso = new ArrayList<>();
        List<String> filteredRelated = related.stream()
                .filter(r -> !katalogId.equals(r) && !viewerId.equals(r)).collect(Collectors.toList());
        filteredRelated.forEach(r -> {
            if (r.contains("xml")) {
                SeeAlso sa = SeeAlso.builder()
                        .id(r)
                        .format("application/xml")
                        .type(DATASET)
                        .profile(METS_PROFILE)
                        .build();
                seeAlso.add(sa);
            }
        });
        if (!seeAlso.isEmpty()) {
            return seeAlso;
        } else {
            return null;
        }
    }

    public String getURNfromFinalMetadata(final List<MetadataVersion3> finalMetadata) {
        final Optional<Set<MetadataVersion3>> metaURN = Optional.of(finalMetadata.stream().filter(
                y -> y.getLabel().values().stream().anyMatch(v -> v.contains("URN"))).collect(Collectors.toSet()));
        final Optional<MetadataVersion3> urn = metaURN.get().stream().findAny();
        return urn.map(u -> u.getValue().get(DomainConstants.NONE).get(0)).orElse(null);
    }

    public MetadataVersion3 buildRequiredStatement(Manifest manifest) {
        //build Required Statement
        final List<String> formattedLicenses = new ArrayList<>();
        List<String> licenses = manifest.getLicense();
        licenses.forEach(l -> {
            String htmlLicense = "<a href=\"" + l + "\">" + l + "</a>";
            formattedLicenses.add(htmlLicense);
        });
        final String rightsString = String.join("<br/>", formattedLicenses);
        final Map<String, List<String>> label = buildLabelMap("Attribution", ENGLISH);
        final String attribution = manifest.getAttribution();
        String cleanAttribution;
        // do not strip <br/> from domain constant attribution
        if (!attribution.contains("Provided by Leipzig University Library")) {
            cleanAttribution = attribution.replaceAll("<[^>]*>", "")
                    .replaceAll("\n", "");
        } else {
            cleanAttribution = attribution;
        }
        final String htmlStatement = "<div>" + cleanAttribution + "<br/>" + rightsString + "</div>";
        final Map<String, List<String>> value = buildLabelMap(htmlStatement, ENGLISH);
        return MetadataVersion3.builder()
                .label(label)
                .value(value)
                .build();
    }
}
