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
import de.ubleipzig.iiifproducer.converter.ConverterUtils;
import de.ubleipzig.iiifproducer.model.v2.Manifest;
import de.ubleipzig.iiifproducer.model.v3.Homepage;
import de.ubleipzig.iiifproducer.model.v3.MetadataVersion3;
import de.ubleipzig.iiifproducer.model.v3.SeeAlso;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConverterUtilsTest {
    ConverterUtils utils;

    @BeforeEach
    void init() {
        utils = ConverterUtils.builder().build();
    }

    @Test
    void buildHomePagesTest() {
        String[] relatedArray = {
            "https://katalog.ub.uni-leipzig.de/urn/xyz:1144:55", "http://example.com/123456",
                "presentation.xml", "manifest.json"
        };
        List<Homepage> homepages = utils.buildHomepages(Arrays.asList(relatedArray));
        Homepage hp = homepages.stream().findFirst().orElse(null);
        assert hp != null;
        assertEquals("https://katalog.ub.uni-leipzig.de/urn/xyz:1144:55", hp.getId());
        assertEquals(2, homepages.size());
    }

    @Test
    void buildSeeAlsoTest() {
        List<String> related = List.of("http://example/presentation.xml");
        List<SeeAlso> seeAlso = utils.buildSeeAlso("123456", "xyz:1144:55", related);
        assert seeAlso != null;
        SeeAlso sa = seeAlso.stream().findFirst().orElse(null);
        assert sa != null;
        assertEquals("http://example/presentation.xml", sa.getId());
    }

    @Test
    void buildRequiredStatementTest() {
        String attribution = "\n                        Urheberrechtsschutz 1.0\n                    <br/>";
        List<String> licenses = List.of("http://rightsstatements.org/vocab/InC/1.0/");
        Manifest manifest = Manifest.builder()
                .attribution(attribution)
                .license(licenses)
                .build();
        MetadataVersion3 rs = utils.buildRequiredStatement(manifest);
        assertEquals("<div>                        Urheberrechtsschutz 1.0                    <br/><a href=\"http://rightsstatements.org/vocab/InC/1.0/\">http://rightsstatements.org/vocab/InC/1.0/</a></div>",
                rs.getValue().get("en").stream().findFirst().orElse(null));
    }
}
