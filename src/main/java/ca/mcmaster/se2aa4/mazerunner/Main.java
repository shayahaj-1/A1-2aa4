package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("** Starting Maze Runner **");

        Options options = new Options();
        options.addOption("i", true, "Path to the maze file");
        options.addOption("p", true, "Maze path sequence");
        CommandLineParser parser = new DefaultParser();

        try {
            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption("i")) {
                String mazeFile = cmd.getOptionValue("i");
                Maze maze = new Maze(mazeFile);
                Player player = new Player(maze, maze.getXStart(), maze.getYStart());

                if (cmd.hasOption("p")) {
                    String pathSequence = cmd.getOptionValue("p");
                    boolean isValid = player.isPathValid(pathSequence);
                    logger.info("Path validation result: {}", isValid ? "Valid" : "Invalid");
                } else {
                    Path path = player.explore();
                    System.out.println("Canonical Path: " + path.getCanonicalPath());
                    System.out.println("Factorized Path: " + path.getFactorizedPath());
                }
            } else {
                logger.error("No maze file provided (-i flag required). Exiting program.");
            }
        } catch (Exception e) {
            logger.error("An error occurred during program execution.", e);
        }

        logger.info("** End of Maze Runner **");
    }
}