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

    @CommandLine.Option(names = {"-c", "--config"}, required = true, description = "Path to config file")
    private String configFile = "etc/producer-config.yml";
    @CommandLine.Option(names = {"-f", "--format"}, description = "Produce Version Format")
    private String format = "v3";
    @CommandLine.Option(names = {"-o", "--outputFile"}, description = "Output File")
    private String outputFile = "/tmp/output.json";
    @CommandLine.Option(names = {"-v", "--viewid"}, required = true, description = "View identifier")
    private String viewId;
    @CommandLine.Option(names = {"-x", "--xmlFile"}, description = "XML Source")
    private String xmlFile;

    public static void main(final String[] args) {
        int exitCode = new CommandLine(new IIIFProducerDriver()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() {
        File file = new File(configFile);
        Properties configFile = retrieveConfig(file);
        Properties config = new Properties();
        config.setProperty("viewId", viewId);
        config.setProperty("xmlFile", xmlFile);
        config.setProperty("outputFile", outputFile);
        final Properties props = mergeProperties(configFile, config);
        final String resourceContext = props.getProperty("baseUrl") + viewId;

        final IRIBuilder iriBuilder = IRIBuilder.builder()
                .annotationContext(props.getProperty("annotationContext"))
                .canvasContext(props.getProperty("canvasContext"))
                .imageServiceBaseUrl(props.getProperty("imageServiceBaseUrl"))
                .imageServiceFileExtension(props.getProperty("imageServiceFileExtension"))
                .imageServiceImageDirPrefix(props.getProperty("imageServiceImageDirPrefix"))
                .isUBLImageService((boolean) props.get("isUBLImageService"))
                .rangeContext(props.getProperty("rangeContext"))
                .resourceContext(resourceContext)
                .build();

        final MetsAccessor mets = MetsImpl.builder()
                .anchorKey(props.getProperty("anchorKey"))
                .attributionKey(props.getProperty("attributionKey"))
                .attributionLicenseNote(props.getProperty("attributionLicenseNote"))
                .iriBuilder(iriBuilder)
                .license(props.getProperty("license"))
                .resourceContext(resourceContext)
                .xmlFile(xmlFile)
                .mets()
                .xlinkmap()
                .build();

        final IIIFProducer producer = IIIFProducer.builder()
                .baseUrl(props.getProperty("baseUrl"))
                .canvasContext(props.getProperty("canvasContext"))
                .defaultSequenceId(props.getProperty("defaultSequenceId"))
                .dfgFileName(props.getProperty("dfgFilename"))
                .format(format)
                .fulltextFileGrp(props.getProperty("fulltextFileGrp"))
                .iriBuilder(iriBuilder)
                .manifestFileName(props.getProperty("manifestFilename"))
                .mets(mets)
                .outputFile(outputFile)
                .resourceContext(resourceContext)
                .viewerUrl(props.getProperty("viewerUrl"))
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
