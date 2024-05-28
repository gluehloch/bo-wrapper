/*
 * ============================================================================
 * Project betoffice-wrapper Copyright (c) 2000-2024 by Andre Winkler. All
 * rights reserved.
 * ============================================================================
 * GNU GENERAL PUBLIC LICENSE TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND
 * MODIFICATION
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 */

package de.betoffice.wrapper.cli;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Optional;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import de.betoffice.wrapper.cli.ApiCommandLineArguments.Command;

public class ApiCommandLineParser {

    public Optional<ApiCommandLineArguments> parse(String[] args, PrintStream ps) {
        final Options options = parseCommandLine(args);
        final CommandLineParser parser = new DefaultParser();

        final CommandLine commandLine;
        try {
            commandLine = parser.parse(options, args);
        } catch (MissingOptionException ex) {
            ps.println(String.format("%s", ex.getMessage()));
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("Betoffice API command line tool.", options);
            formatter.printUsage(new PrintWriter(ps), 30, "use me....");
            return Optional.empty();
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }

        final ApiCommandLineArguments cla = new ApiCommandLineArguments();
        if (commandLine.hasOption("help")) {
            cla.setCommand(Command.HELP);
        } else if (commandLine.hasOption("test")) {
            cla.setCommand(Command.TEST_DATABASE_CONNECTION);
        }

        return Optional.of(cla);
    }

    private Options parseCommandLine(String[] args) {
        Options options = new Options();

        Option helpOption = Option.builder("help")
                .longOpt("help")
                .desc("print this help")
                .build();

        Option testConnection = Option.builder("test")
                .longOpt("test")
                .desc("Test database connection.")
                .build();

        options.addOption(helpOption);
        options.addOption(testConnection);

        return options;
    }

}
