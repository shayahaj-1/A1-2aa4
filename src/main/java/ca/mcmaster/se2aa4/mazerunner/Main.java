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

    //calling logger
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("** Starting Maze Runner **");

        //intialize options/parser
        Options options = new Options();
        options.addOption("i", true, "Path to the maze file");
        options.addOption("p", true, "Maze path sequence");
        CommandLineParser parser = new DefaultParser();

        try {
            CommandLine cmd = parser.parse(options, args);
            MazeLoader loader = new MazeLoaderAdapter();
            //finding path for user with -i flag
            if (cmd.hasOption("i")) {
                String mazeFile = cmd.getOptionValue("i");
                Maze maze = new Maze(mazeFile, loader);
                MazeRunner player = new RightHandRunner(maze, maze.getXStart(), maze.getYStart());

                //path validation when -p flag is added
                if (cmd.hasOption("p")) {
                    String sequence = cmd.getOptionValue("p");
                    System.out.println(player.validatePath(sequence));
                    
                } 
                else {
                    //print paths for user
                    Path path = player.explore();
                    System.out.println("Canonical Path: " + path.getCanonicalPath());
                    System.out.println("Factorized Path: " + path.getFactorizedPath());
                }
            } 
            else {
                logger.error("No maze file provided (-i flag required). Exiting program.");
            }
        } catch (Exception e) {
            logger.error("An error occurred during program execution.", e);
        }
        logger.info("** End of Maze Runner **");
    }
}