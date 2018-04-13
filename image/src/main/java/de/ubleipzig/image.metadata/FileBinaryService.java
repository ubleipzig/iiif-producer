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

package de.ubleipzig.image.metadata;

import static java.util.Arrays.asList;
import static java.util.Base64.getEncoder;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static org.apache.commons.codec.digest.MessageDigestAlgorithms.MD2;
import static org.apache.commons.codec.digest.MessageDigestAlgorithms.MD5;
import static org.apache.commons.codec.digest.MessageDigestAlgorithms.SHA_1;
import static org.apache.commons.codec.digest.MessageDigestAlgorithms.SHA_256;
import static org.apache.commons.codec.digest.MessageDigestAlgorithms.SHA_384;
import static org.apache.commons.codec.digest.MessageDigestAlgorithms.SHA_512;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;

/**
 * FileBinaryService.
 *
 * @author acoburn
 */
public class FileBinaryService {

    private static final Logger LOGGER = getLogger(FileBinaryService.class);
    private static final String SHA = "SHA";

    private static final Set<String> algorithms = new HashSet<>(
            asList(MD5, MD2, SHA, SHA_1, SHA_256, SHA_384, SHA_512));

    /**
     * FileBinaryService.
     */
    public FileBinaryService() {
    }

    /**
     * Get a list of supported algorithms.
     *
     * @return the supported digest algorithms
     */
    public Set<String> supportedAlgorithms() {
        return algorithms;
    }

    /**
     * Get the digest for an input stream.
     *
     * <p>Note: the digest likely uses the base64 encoding, but the specific encoding is defined
     * for each algorithm at https://www.iana.org/assignments/http-dig-alg/http-dig-alg.xhtml
     *
     * @param algorithm the algorithm to use
     * @param stream the input stream
     * @return a string representation of the digest
     */
    public Optional<String> digest(final String algorithm, final InputStream stream) {
        if (SHA.equals(algorithm)) {
            return of(SHA_1).map(DigestUtils::getDigest).flatMap(digest(stream));
        }
        return ofNullable(algorithm).filter(supportedAlgorithms()::contains).map(DigestUtils::getDigest).flatMap(
                digest(stream));
    }

    /**
     * digest.
     *
     * @param stream stream
     * @return Function
     */
    private Function<MessageDigest, Optional<String>> digest(final InputStream stream) {
        return algorithm -> {
            try {
                final String digest = getEncoder().encodeToString(DigestUtils.updateDigest(algorithm, stream).digest());
                stream.close();
                return of(digest);
            } catch (final IOException ex) {
                LOGGER.error("Error computing digest", ex);
            }
            return empty();
        };
    }
}
