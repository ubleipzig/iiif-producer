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

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.slf4j.LoggerFactory.getLogger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.ubleipzig.iiif.vocabulary.templates.BaseListContext;
import de.ubleipzig.iiif.vocabulary.templates.PresentationApiContextMap;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

/**
 * @author christopher-johnson
 */
public abstract class PresentationApiContextTest {

    private static final Logger LOGGER = getLogger(PresentationApiContextTest.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public abstract String context();

    public abstract Class<?> vocabulary();

    public Boolean isStrict() {
        return true;
    }

    protected String getContext(final String url) {
        final CloseableHttpClient client = HttpClients.createDefault();
        final HttpGet get = new HttpGet(url);
        try {
            final HttpResponse response = client.execute(get);
            final HttpEntity out = response.getEntity();
            return EntityUtils.toString(out, UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void testContext() throws IOException {
        final String context = getContext(context());
        final BaseListContext baseContext = MAPPER.readValue(context, new TypeReference<BaseListContext>() {
        });
        final PresentationApiContextMap contextMap = baseContext.getContext().get(0);
    }

    @Test
    public void testNamespace() throws Exception {
        final Optional<Field> context = stream(vocabulary().getFields()).filter(
                field -> field.getName().equals("CONTEXT")).findFirst();

        assertTrue(context.isPresent(), vocabulary().getName() + " does not contain a 'URI' field!");
        assertEquals(context(), context.get().get(null), "Namespaces do not match!");
    }

    private Stream<String> fields() {
        return stream(vocabulary().getFields()).map(Field::getName).map(
                name -> name.endsWith("_") ? name.substring(0, name.length() - 1) : name).filter(
                field -> !field.equals("CONTEXT")).filter(field -> !field.equals("URI"));
    }
}
