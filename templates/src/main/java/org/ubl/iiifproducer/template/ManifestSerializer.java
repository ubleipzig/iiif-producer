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

package org.ubl.iiifproducer.template;

import static com.fasterxml.jackson.core.util.DefaultIndenter.SYSTEM_LINEFEED_INSTANCE;
import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.newBufferedWriter;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.slf4j.LoggerFactory.getLogger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import org.slf4j.Logger;

/**
 * ManifestSerializer.
 *
 * @author christopher-johnson
 */
public final class ManifestSerializer {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static Logger logger = getLogger(ManifestSerializer.class);

    static {
        MAPPER.configure(WRITE_DATES_AS_TIMESTAMPS, false);
        MAPPER.configure(INDENT_OUTPUT, true);
        MAPPER.registerModule(new JavaTimeModule());
    }

    private ManifestSerializer() {
    }

    /**
     * Serialize the Manifest.
     *
     * @return the Manifest as a JSON string
     */
    public static Optional<String> serialize(final Object manifest) {
        try {
            return of(MAPPER.writer(PrettyPrinter.instance).writeValueAsString(manifest));
        } catch (final JsonProcessingException ex) {
            return empty();
        }
    }

    public static Boolean writeToFile(String json, File file) {
        logger.info("Writing File at {}", file.getPath());
        try (final BufferedWriter writer = newBufferedWriter(file.toPath(), UTF_8, CREATE,
                APPEND)) {
            writer.write(json);
        } catch (final IOException ex) {
            logger.error("Error writing data to resource {}: {}", file, ex.getMessage());
            return false;
        }
        return true;
    }

    private static class PrettyPrinter extends DefaultPrettyPrinter {
        public static final PrettyPrinter instance = new PrettyPrinter();

        public PrettyPrinter() {
            _arrayIndenter = SYSTEM_LINEFEED_INSTANCE;
        }
    }
}
