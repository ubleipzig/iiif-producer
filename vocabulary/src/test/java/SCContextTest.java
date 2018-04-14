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

import de.ubleipzig.iiifproducer.vocabulary.SC;

import org.junit.jupiter.api.Disabled;

/**
 * Test the SC Vocabulary Class
 *
 * @author christopher-johnson
 */
@Disabled
public class SCContextTest extends AbstractListContextTest {

    @Override
    public String context() {
        return "http://iiif.io/api/presentation/2/context.json";
    }

    @Override
    public Class<SC> vocabulary() {
        return SC.class;
    }
}
