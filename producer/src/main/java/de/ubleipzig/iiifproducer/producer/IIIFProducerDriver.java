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

package de.ubleipzig.iiifproducer.producer;

import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;
import picocli.CommandLine;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

/**
 * IIIFProducerDriver.
 *
 * @author christopher-johnson
 */
@CommandLine.Command(
        name = "producer",
        description = "IIIF Producer",
        mixinStandardHelpOptions = true
)
@Slf4j
public final class IIIFProducerDriver implements Callable<Integer> {

    @CommandLine.Option(names = {"-v", "--viewid"}, required = true, description = "View identifier")
    private String viewId;

    @CommandLine.Option(names = {"-x", "--xmlFile"}, required = false, description = "XML Source")
    private String xmlFile;

    @CommandLine.Option(names = {"-o", "--outputFile"}, required = false, description = "Output File")
    private String outputFile = "/tmp/output.json";

    @CommandLine.Option(names = {"-c", "--config"}, required = true, description = "Path to config file")
    private String configFile = "etc/producer-config.yml";

    @CommandLine.Option(names = {"-s", "--serializeImageManifest"}, required = false, description = "serializeImageManifest")
    private boolean serializeImageManifest = false;

    @CommandLine.Option(names = {"-u", "--imageManifestUrl"}, required = false, description = "imageManifestUrl")
    private String imageManifestUrl = "image-manifest.json";

    public static void main(final String[] args) {
        int exitCode = new CommandLine(new IIIFProducerDriver()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() {
        File file = new File(configFile);
        Properties config = retrieveConfig(file);
        Properties configFromCLI = new Properties();
        configFromCLI.setProperty("viewId", viewId);
        configFromCLI.setProperty("xmlFile", xmlFile);
        configFromCLI.setProperty("outputFile", outputFile);
        configFromCLI.setProperty("imageManifestUrl", imageManifestUrl);
        configFromCLI.setProperty("serializeImageManifest", String.valueOf(serializeImageManifest));
        Properties finalProps = mergeProperties(config, configFromCLI);
        IIIFProducer producer = IIIFProducer.builder()
                .baseUrl(config.getProperty("baseUrl"))
                .canvasContext(config.getProperty("canvasContext"))
                .config(finalProps)
                .defaultSequenceId(config.getProperty("defaultSequenceId"))
                .fulltextFileGrp(config.getProperty("fulltextFileGrp"))
                .manifestFileName(config.getProperty("manifestFilename"))
                .outputFile(outputFile)
                .resourceContext(config.getProperty("baseUrl") + viewId)
                .viewId(viewId)
                .xmlFile(xmlFile)
                .build();
        producer.buildManifest();
        return 0;
}

    /**
     * This method parses the provided configFile into its equivalent command-line args.
     *
     * @param configFile containing config args
     * @return Array of args
     */
    private Properties retrieveConfig(final File configFile) {
        if (!configFile.exists()) {
            log.error("Configuration file does not exist: " + configFile);
        }
        log.debug("Loading configuration file: {}", configFile);
        try {
            Yaml yaml = new Yaml();
            Properties map = yaml.loadAs(new FileInputStream(configFile), Properties.class);
            Properties config = new Properties();
            config.putAll(map);
            return config;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private Properties mergeProperties(Properties... properties) {
        return Stream.of(properties)
                .collect(Properties::new, Map::putAll, Map::putAll);
    }
}
