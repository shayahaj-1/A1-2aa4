package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class RightHandRunner extends MazeRunner {

    public RightHandRunner(Maze maze, int startRow, int startCol) {
        super(maze, startRow, startCol);
    }

    @Override
    protected boolean canMoveForward() {
        int[] nextPos = moveForwardPosition(row, col, direction);
        return maze.canPass(nextPos[0], nextPos[1]);
    }

    @Override
    protected boolean canMoveRight() {
        String rightDirection = turnRight(direction);
        int[] nextPos = moveForwardPosition(row, col, rightDirection);
        return maze.canPass(nextPos[0], nextPos[1]);
    }

    @Override
    protected void moveForward() {
        int[] newPos = moveForwardPosition(row, col, direction);
        row = newPos[0];
        col = newPos[1];
        logger.info("Moved to position ({}, {})", row, col);
    }

    @Override
    protected void turnRight() {
        direction = turnRight(direction);
        logger.info("Turned right, now facing {}", direction);
    }

    @Override
    protected void turnLeft() {
        direction = turnLeft(direction);
        logger.info("Turned left, now facing {}", direction);
    }

    private int[] moveForwardPosition(int row, int col, String direction) {
        switch (direction) {
            case "NORTH": row--; break;
            case "SOUTH": row++; break;
            case "EAST": col++; break;
            case "WEST": col--; break;
        }
        return new int[]{row, col};
    }

    private String turnLeft(String direction) {
        return switch (direction) {
            case "NORTH" -> "WEST";
            case "WEST" -> "SOUTH";
            case "SOUTH" -> "EAST";
            case "EAST" -> "NORTH";
            default -> direction;
        };
    }

    private String turnRight(String direction) {
        return switch (direction) {
            case "NORTH" -> "EAST";
            case "EAST" -> "SOUTH";
            case "SOUTH" -> "WEST";
            case "WEST" -> "NORTH";
            default -> direction;
        };
    }
}
