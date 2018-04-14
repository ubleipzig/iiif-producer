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

import java.io.PrintWriter;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;

/**
 * ArgParser.
 *
 * @author christopher-johnson
 */
class ArgParser {

    private static final Logger logger = getLogger(ArgParser.class);
    private static final Options configOptions = new Options();

    static {
        configOptions.addOption(
                builder("v").longOpt("viewid").hasArg(true).desc("View identifier").required(true).build());

        configOptions.addOption(builder("t").longOpt("title").hasArg(true).desc("Title").required(true).build());

        configOptions.addOption(builder("i").longOpt("input").hasArg(true).desc("Source").required(true).build());

        configOptions.addOption(builder("o").longOpt("output").hasArg(true).desc("Output").required(true).build());
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

    /**
     * Parse command line arguments into a Config object.
     *
     * @param args command line arguments
     * @return the parsed config file or command line args.
     */
    public Config parseConfiguration(final String[] args) {
        // first see if they've specified a config file
        CommandLine c = null;
        Config config = null;

        try {
            c = parseConfigArgs(configOptions, args);
            config = parseConfigurationArgs(c);
        } catch (final ParseException e) {
            printHelp("Error parsing args: " + e.getMessage());
        }

        return config;
    }

    /**
     * This method parses the command-line args.
     *
     * @param cmd command line options
     * @return Config
     */
    private Config parseConfigurationArgs(final CommandLine cmd) {
        final Config config = new Config();

        config.setViewId(cmd.getOptionValue('v'));
        config.setTitle(cmd.getOptionValue('t'));
        config.setInputFile(cmd.getOptionValue('i'));
        config.setOutputFile(cmd.getOptionValue('o'));
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
        if (message != null) {
            writer.println("\n-----------------------\n" + message + "\n-----------------------\n");
        }
        writer.println("Running IIIF Producer from command line arguments");
        formatter.printHelp(writer, 80, "./producer", "", configOptions, 4, 4, "", true);
        writer.println("\n");
        writer.flush();

        if (message != null) {
            throw new RuntimeException(message);
        } else {
            throw new RuntimeException();
        }
    }
}
