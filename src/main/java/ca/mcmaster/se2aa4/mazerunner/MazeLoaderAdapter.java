package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;

public class MazeLoaderAdapter implements MazeLoader {
    private static final Logger logger = LogManager.getLogger();
    private int rows;
    private int cols;
    private char[][] maze;

    @Override
    //loads maze from given file
    public char[][] loadMaze(String mazeFile) {
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
            return new char[0][0];
        }

        return maze;
    }
}
