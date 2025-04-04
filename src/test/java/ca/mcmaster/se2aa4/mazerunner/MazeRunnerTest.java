package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MazeRunnerTest {

    @Test
    //maze should not be null
    void testLoads() {
        MazeLoaderAdapter loader = new MazeLoaderAdapter();
        char[][] maze = loader.loadMaze("direct.maz.txt");
        assertNotNull(maze);
    }

    @Test
    //should be able to pass through open space
    void testCanPass() {
        MazeLoader loader = new MazeLoaderAdapter();
        Maze maze = new Maze("direct.maz.txt", loader);
        assertTrue(maze.canPass(1, 1));
    }

    @Test
    //should not be able to pass through walls
    void testCannotPass() {
        MazeLoader loader = new MazeLoaderAdapter();
        Maze maze = new Maze("direct.maz.txt", loader);
        assertFalse(maze.canPass(0, 0));
    }

    @Test
    //checks that start and end positions are properly found on left and right boundards respectively
    void testEntryExit() {
        MazeLoader loader = new MazeLoaderAdapter();
        Maze maze = new Maze("medium.maz.txt", loader);
        assertTrue(maze.getXStart() >= 0 && maze.getYStart() == 0);
        assertTrue(maze.getXEnd() >= 0 && maze.getYEnd() == maze.getCols() - 1);
    }

    @Test
    //checks that runner is initialized at start point
    void testInitialization() {
        MazeLoader loader = new MazeLoaderAdapter();
        Maze maze = new Maze("small.maz.txt", loader);
        RightHandRunner runner = new RightHandRunner(maze, maze.getXStart(), maze.getYStart());
        assertNotNull(runner);
    }

    @Test
    //checks basic movement
    void testMoveForward() {
        MazeLoader loader = new MazeLoaderAdapter();
        Maze maze = new Maze("direct.maz.txt", loader);
        RightHandRunner runner = new RightHandRunner(maze, maze.getXStart(), maze.getYStart());
        runner.moveForward();
        assertNotEquals(maze.getXStart(), runner.row);
    }

    @Test
    //checks that runner turns in place
    void testTurnRight() {
        MazeLoader loader = new MazeLoaderAdapter();
        Maze maze = new Maze("direct.maz.txt", loader);
        RightHandRunner runner = new RightHandRunner(maze, maze.getXStart(), maze.getYStart());
        runner.turnRight();
        assertEquals(maze.getXStart(), runner.row);
        assertEquals(maze.getXStart(), runner.col);
    }

    @Test
    //checks that canonical path format is accurate
    void testPathCanonicalFormat() {
        Path path = new Path();
        path.addStep('F');
        path.addStep('F');
        path.addStep('R');
        assertEquals("FF R", path.getCanonicalPath(), "Canonical path should be formatted correctly");
    }

    @Test
    //checks that factorized path format is accurate
    void testPathFactorizedFormat() {
        Path path = new Path();
        path.addStep('F');
        path.addStep('F');
        path.addStep('R');
        assertEquals("2F R", path.getFactorizedPath(), "Factorized path should be formatted correctly");
    }

    @Test
    //checks that path validation is accurate
    void testPathValidation() {
        MazeLoader loader = new MazeLoaderAdapter();
        Maze maze = new Maze("straight.maz.txt", loader);
        RightHandRunner runner = new RightHandRunner(maze, maze.getXStart(), maze.getYStart());
        String validation = runner.validatePath("FFFF");
        assertTrue(validation.contains("Correct path!"), "Path should be valid");
    }
}
