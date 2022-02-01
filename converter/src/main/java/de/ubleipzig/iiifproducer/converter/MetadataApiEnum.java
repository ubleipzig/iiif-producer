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

public enum MetadataApiEnum {

    AUTHOR("Author"), LABEL("label"), GND("GND"), STRUCTTYPE("structType"), COLLECTION("Collection"), LANGUAGE(
            "language-iso639-2"), DISPLAYORDER("displayOrder"), MANUSCRIPT("manuscript"), MANIFESTTYPE("manifestType"),
    LANGUAGE_NAME("Language Name"), SUBTITLE("subTitle"), PHYSICAL_DESCRIPTION("physicalDescription");

    private String apiKey;

    MetadataApiEnum(final String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiKey() {
        return apiKey;
    }

}
