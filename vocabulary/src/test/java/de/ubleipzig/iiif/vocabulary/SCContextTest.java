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

import de.ubleipzig.iiif.vocabulary.templates.PresentationApiContextMap;

import org.junit.jupiter.api.Disabled;

/**
 * Test the SC Vocabulary Class
 *
 * @author christopher-johnson
 */
@Disabled
public class SCContextTest extends PresentationApiContextTest {

    @Override
    public String context() {
        return "http://iiif.io/api/presentation/2/context.json";
    }

    @Override
    public Class<PresentationApiContextMap> vocabulary() {
        return PresentationApiContextMap.class;
    }
}
