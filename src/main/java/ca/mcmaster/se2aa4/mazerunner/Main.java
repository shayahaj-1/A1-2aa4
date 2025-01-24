package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {

        //define command-line options
        Options options = new Options();
        options.addOption("i", true, "Input file containing the maze");
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);

            //check if the input file option is provided
            if (cmd.hasOption("i")) {
                String inputFile = cmd.getOptionValue("i");
                logger.info("Reading the maze from file " + inputFile);

                //read the maze from the file
                try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        for (int idx = 0; idx < line.length(); idx++) {
                            if (line.charAt(idx) == '#') {
                                System.out.print("WALL ");
                            } else if (line.charAt(idx) == ' ') {
                                System.out.print("PASS ");
                            }
                        }
                        System.out.print(System.lineSeparator());
                    }
                } catch (IOException e) {
                    logger.error("Error reading the maze file: " + inputFile, e);
                }

            } else {
                logger.error("No input file specified with the -i option.");
            }
            logger.info("Computing path");
            logger.info("PATH NOT COMPUTED");

        } catch (Exception e) {
            logger.error("An unexpected error occurred", e);
        }

        logger.info("End of MazeRunner");
    }
}
