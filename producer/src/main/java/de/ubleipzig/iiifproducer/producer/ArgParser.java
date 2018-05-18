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

import static java.lang.System.out;
import static org.apache.commons.cli.Option.builder;
import static org.slf4j.LoggerFactory.getLogger;

import io.dropwizard.configuration.ConfigurationException;
import io.dropwizard.configuration.YamlConfigurationFactory;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.jersey.validation.Validators;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;

/**
 * ArgParser.
 *
 * @author christopher-johnson
 */
class ArgParser {

    private static Logger logger = getLogger(ArgParser.class);
    private static final Options configOptions = new Options();

    static {
        configOptions.addOption(
                builder("v").longOpt("viewid").hasArg(true).desc("View identifier").required(true).build());

        configOptions.addOption(
                builder("i").longOpt("imageSourceDir").hasArg(true).desc("Image Source Directory").required(
                        true).build());

        configOptions.addOption(
                builder("x").longOpt("xmlinput").hasArg(true).desc("XML Source").required(true).build());

        configOptions.addOption(builder("o").longOpt("output").hasArg(true).desc("Output").required(true).build());

        configOptions.addOption(
                Option.builder("c").longOpt("config").hasArg(true).numberOfArgs(1).argName("config").desc(
                        "Path to config file").required(false).build());

        configOptions.addOption(
                builder("s").longOpt("serializeImageManifest").desc("serializeImageManifest").required(false).build());
    }

    /**
     * Parse command line options based on the provide Options.
     *
     * @param configOptions valid set of Options
     * @param args command line arguments
     * @return the list of option and values
     * @throws ParseException if invalid/missing option is found
     */
    private static CommandLine parseConfigArgs(final Options configOptions, final String[] args) throws ParseException {
        return new DefaultParser().parse(configOptions, args);
    }

    /**
     * Parse command-line arguments.
     *
     * @param args Command-line arguments
     * @return A configured IIIFProducer instance.
     **/
    ManifestBuilderProcess init(final String[] args) {
        final Config config = parseConfiguration(args);
        return new IIIFProducer(config);
    }

    public Config parseConfiguration(final String[] args) {
        final CommandLine c;
        Config config;
        try {
            c = parseConfigArgs(configOptions, args);
            config = parseConfigFileOptions(c);
            config = addSharedOptions(c, config);
            return config;
        } catch (final ParseException e) {
            printHelp("Error parsing args: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    private Config parseConfigFileOptions(final CommandLine cmd) {
        final String defaultConfigFile = ArgParser.class.getResource("/producer-config.yml").getPath();
        return retrieveConfig(new File(cmd.getOptionValue('c', defaultConfigFile)));
    }

    /**
     * This method parses the provided configFile into its equivalent command-line args.
     *
     * @param configFile containing config args
     * @return Array of args
     */
    private Config retrieveConfig(final File configFile) {
        if (!configFile.exists()) {
            printHelp("Configuration file does not exist: " + configFile);
        }
        logger.debug("Loading configuration file: {}", configFile);
        try {
            return new YamlConfigurationFactory<>(
                    Config.class, Validators.newValidator(), Jackson.newObjectMapper(), "").build(configFile);
        } catch (IOException | ConfigurationException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * This method add/updates the values of any options that may be
     * valid in either scenario (config file or fully command line).
     *
     * @param cmd a parsed command line
     * @return Config the config which may be updated
     */
    private Config addSharedOptions(final CommandLine cmd, final Config config) {
        final String viewId = cmd.getOptionValue("v");
        final String xmlSource = cmd.getOptionValue("x");
        final String imageSourceDir = cmd.getOptionValue("i");
        final String outputFile = cmd.getOptionValue("o");
        config.setViewId(viewId);
        config.setXmlFile(xmlSource);
        config.setImageSourceDir(imageSourceDir);
        config.setOutputFile(outputFile);
        config.setSerializeImageManifest(cmd.hasOption("s"));
        return config;
    }

    /**
     * Print help/usage information.
     *
     * @param message the message or null for none
     */
    private void printHelp(final String message) {
        final HelpFormatter formatter = new HelpFormatter();
        final PrintWriter writer = new PrintWriter(out);
        writer.println("\n-----------------------\n" + message + "\n-----------------------\n");
        writer.println("Running IIIF Producer from command line arguments");
        formatter.printHelp(writer, 80, "./producer", "", configOptions, 4, 4, "", true);
        writer.println("\n");
        writer.flush();
        throw new RuntimeException(message);
    }
}
