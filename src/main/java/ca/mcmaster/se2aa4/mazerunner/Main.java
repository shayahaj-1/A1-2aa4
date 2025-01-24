package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {

        Options options = new Options();
        options.addOption("i", true, "Input file containing the maze");
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);

            if cmd.hasOption(opt:"i") {
                String mazeFile = cmd.getOptionValue(opt:"i");
            }
            char[][] maze = readMaze(mazeFile);
            String path = exploreMaze(maze);
            System.out.println("Path: " + path);
        } catch (Exception e) {
            logger.error("An unexpected error occurred", e);
        }
        logger.info("End of MazeRunner");

    }

    private static char[][] readMaze(String mazeFile) {
        logger.info("Reading the maze file: " + mazeFile);
        try (BufferedReader reader = new BufferedReader(new FileReader(mazeFile))) {
            String line = reader.readLine;
            int rows = 1; //rows
            int cols = line.length(); //columns

            //read maze into string
            while ((line) != null) {
                rows++;
            }

            char[][] maze = new char[rows][cols];
            
            //remake maze
            int rowNow = 0;
            while ((line) != null) {
                for (int i = 0; i < cols; i++) {
                    maze[rowNow][i] = line.charAt(i);
                }
            }

            return maze;

        } catch (Exception e) {
            logger.error("An unexpected error occurred", e);
        }

        logger.info("End of MazeRunner");
        return null;
    }

    private static String explore(char[][] maze) {
        int row = 0;
        int col = 0;
        for (int i = 0; i < maze.length; i++) {
            //for MVP, only entry on the left
            if (maze[i][0] == ' ') {
                row = i;
                col = 0;
                break;
            }
        }

        String path = "";
        boolean exit = false;

        while (!exit) {
            //exit if at the right
            if ((col == 0 || col == maze[0].length - 1) && maze[row][col] == ' ' && path.length() > 0) {
                exit = true;
            }

            if (isOpen(maze, row, col + 1)) {
                //move up
                row--;
                path += "L F ";
            } else if (isOpen(maze, row + 1, col)) { 
                //move down
                row++;
                path += "F ";
            } else if (isOpen(maze, row, col - 1)) { 
                //move left
                col--;
                path += "L F ";
            } else if (isOpen(maze, row - 1, col)) {
                //move right
                col++;
                path += "R F ";;
            } else {
                exit = true; //no open tiles
            }
        }

        return path;
    }

    private static boolean isOpen(char[][] maze, int row, int col) {
                //row and column bounds
        return row >= 0 && row < maze.length
                && col >= 0 && col < maze[0].length
                //cell is in bounds and an open space
                && maze[row][col] == ' ';
    }
}
