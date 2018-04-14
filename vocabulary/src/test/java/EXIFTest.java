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

import de.ubleipzig.iiifproducer.vocabulary.EXIF;

/**
 * Test the EXIF Vocabulary Class
 *
 * @author acoburn
 */
public class EXIFTest extends AbstractVocabularyTest {

    @Override
    public String namespace() {
        return "http://www.w3.org/2003/12/exif/ns#";
    }

    @Override
    public Class<EXIF> vocabulary() {
        return EXIF.class;
    }
}
