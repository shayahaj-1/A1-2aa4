package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;

class Maze {
    //calling logger and initialize variables
    private static final Logger logger = LogManager.getLogger();
    private MazeLoader loader;
    private char[][] maze;
    private int rows;
    private int cols;
    private int xStart;
    private int yStart;
    private int xEnd;
    private int yEnd;

    //constructor
    public Maze(String mazeFile, MazeLoader loader) {
        this.loader = loader;
        maze = loader.loadMaze(mazeFile);
        if (maze != null) {
            rows = maze.length;
            cols = maze[0].length;
            findEntry();
            findExit();
        } else {
            logger.error("Failed to load maze. Maze is null.");
        }
    }

    //is this a location you can pass through (not a wall)?
    public boolean canPass(int x, int y) {
        return x >= 0 && x < rows && y >= 0 && y < cols && maze[x][y] == ' ';
    }

    //finds coordinates for entry
    private void findEntry() {
        for (int i = 0; i < rows; i++) {
            if (maze[i][0] == ' ') {
                xStart = i;
                yStart = 0;
                break;
            }
        }
    }

    //finds coordinates for exit
    private void findExit() {
        for (int i = rows - 1; i >= 0; i--) {
            if (maze[i][cols - 1] == ' ') {
                xEnd = i;
                yEnd = cols - 1;
                break;
            }
        }
    }

    //getters and setters
    public int getXStart() {
        return xStart;
    }

    public int getYStart() {
        return yStart;
    }

    public int getXEnd() {
        return xEnd;
    }

    public int getYEnd() {
        return yEnd;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
}
