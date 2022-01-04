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

package de.ubleipzig.iiifproducer.model;

import java.util.List;

/**
 * TemplateStructureList.
 *
 * @author christopher-johnson
 */
public class TemplateStructureList {

    private TemplateTopStructure top;
    private List<TemplateStructure> structures;

    /**
     * @param top TemplateTopStructure
     * @param structures List
     */
    public TemplateStructureList(final TemplateTopStructure top, final List<TemplateStructure> structures) {
        this.top = top;
        this.structures = structures;
    }

    /**
     * @return List
     */
    public List<TemplateStructure> getStructureList() {
        structures.add(0, top);
        return structures;
    }
}
