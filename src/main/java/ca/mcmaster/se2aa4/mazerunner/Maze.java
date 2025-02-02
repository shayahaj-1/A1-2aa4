package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;

class Maze {
    private static final Logger logger = LogManager.getLogger();
    private char[][] maze;
    private int rows;
    private int cols;
    private int xStart;
    private int yStart;
    private int xEnd;
    private int yEnd;

    public Maze(String mazeFile) {
        loadMaze(mazeFile);
        getEntry();
        getExit();

    }

    private void loadMaze(String mazeFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(mazeFile))) {
            List<char[]> mazeLines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                mazeLines.add(line.toCharArray());
            }

            rows = mazeLines.size();
            if (rows > 0) {
                cols = mazeLines.get(0).length;
                maze = new char[rows][cols];
                for (int i = 0; i < rows; i++) {
                    maze[i] = mazeLines.get(i);
                }
                logger.info("Maze loaded successfully from file: {}", mazeFile);
            } else {
                logger.error("Empty maze file: {}", mazeFile);
            }
        } catch (Exception e) {
            logger.error("Failed to load maze from file: {}", mazeFile, e);
        }
    }

    private void getEntry() {
        for (int i = 0; i < rows; i++) {
            if (maze[i][0] == ' ') {
                xStart = i;
                yStart = 0;
                break;
            }
        }
    }

    private void getExit() {
        for (int i = rows - 1; i >= 0; i--) {
            if (maze[i][cols - 1] == ' ') {
                xEnd = i;
                yEnd = cols - 1;
                break;
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public char getTile(int row, int col) {
    if (row >= 0 && row < rows && col >= 0 && col < cols) {
        return maze[row][col];
    }
    return '#'; // Return wall character if out of bounds
}

}