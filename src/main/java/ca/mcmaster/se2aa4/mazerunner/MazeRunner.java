package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

abstract class MazeRunner {

    //calling logger and initialize variables
    protected static final Logger logger = LogManager.getLogger();
    protected Maze maze;
    protected int row, col;
    protected String direction;

    //constructor
    public MazeRunner(Maze maze, int startRow, int startCol) {
        this.maze = maze;
        this.row = startRow;
        this.col = startCol;
        this.direction = "EAST";
        logger.info("MazeRunner initialized at ({}, {}) facing {}", row, col, direction);
    }


    //path explorer
    public final Path explore() {
        Path path = new Path();
        logger.info("Starting maze exploration.");

        while (!isAtExit()) {
            if (canMoveRight()) {
                turnRight();
                moveForward();
                path.addStep('R');
                path.addStep('F');
            } else if (canMoveForward()) {
                moveForward();
                path.addStep('F');
            } else {
                turnLeft();
                path.addStep('L');
            }
        }
        logger.info("Exploration complete.");
        return path;
    }

    protected boolean isAtExit() {
        return row == maze.getXEnd() && col == maze.getYEnd();
    }

    protected abstract boolean canMoveForward();
    protected abstract boolean canMoveRight();
    protected abstract void moveForward();
    protected abstract void turnRight();
    protected abstract void turnLeft();

    //path validation
    public String validatePath(String path) {
        int count = 0;
        path = path.replace(" ", "");
        logger.info("Validating path sequence: {}", path);

        for (int i = 0; i < path.length(); i++) {
            char move = path.charAt(i);

            //handle digit characters for move repetitions
            if (Character.isDigit(move)) {
                count = count * 10 + (move - '0');
                continue;
            }
            if (count == 0) {
                count = 1;
            }

            for (int j = 0; j < count; j++) {
                switch (move) {
                    case 'F':
                        if (!canMoveForward()) {
                            logger.error("Invalid forward move at ({}, {})", row, col);
                            return "Incorrect path";
                        }
                        moveForward();
                        break;
                    case 'R':
                        turnRight();
                        break;
                    case 'L':
                        turnLeft();
                        break;
                    default:
                        logger.error("Invalid path instruction: {}", move);
                        return "Incorrect path";
                }
            }

            count = 0; //reset move count after processing
        }

        //check if the player has reached the maze exit
        if (isAtExit()) {
            logger.info("Path validation successful: Exit reached!");
            return "Path validation successful: Correct path!";
        } else {
            logger.warn("Path validation failed: Did not reach the exit.");
            return "Path validation failed: Incorrect path!";
        }
    }

    // //movement functions
    // private boolean canMoveForward() {
    //     int[] nextPos = moveForwardPosition(row, col, direction);
    //     return maze.canPass(nextPos[0], nextPos[1]);
    // }

    // private boolean canMoveRight() {
    //     String rightDirection = turnRight(direction);
    //     int[] nextPos = moveForwardPosition(row, col, rightDirection);
    //     return maze.canPass(nextPos[0], nextPos[1]);
    // }

    // private void moveForward() {
    //     int[] newPos = moveForwardPosition(row, col, direction);
    //     row = newPos[0];
    //     col = newPos[1];
    //     logger.info("Moved to position ({}, {})", row, col);
    // }

    // private int[] moveForwardPosition(int row, int col, String direction) {
    //     switch (direction) {
    //         case "NORTH": row--; break;
    //         case "SOUTH": row++; break;
    //         case "EAST": col++; break;
    //         case "WEST": col--; break;
    //     }
    //     return new int[]{row, col};
    // }

    // private void turnRight() {
    //     direction = turnRight(direction);
    //     logger.info("Turned right, now facing {}", direction);
    // }

    // private void turnLeft() {
    //     direction = turnLeft(direction);
    //     logger.info("Turned left, now facing {}", direction);
    // }

    // private String turnLeft(String direction) {
    //     return switch (direction) {
    //         case "NORTH" -> "WEST";
    //         case "WEST" -> "SOUTH";
    //         case "SOUTH" -> "EAST";
    //         case "EAST" -> "NORTH";
    //         default -> direction;
    //     };
    // }

    // private String turnRight(String direction) {
    //     return switch (direction) {
    //         case "NORTH" -> "EAST";
    //         case "EAST" -> "SOUTH";
    //         case "SOUTH" -> "WEST";
    //         case "WEST" -> "NORTH";
    //         default -> direction;
    //     };
    // }

    // //path validation
    // public String validatePath(String path) {
    //     int count = 0;
    //     path = path.replace(" ", "");
    //     logger.info("Validating path sequence: {}", path);

    //     for (int i = 0; i < path.length(); i++) {
    //         char move = path.charAt(i);

    //         //handle digit characters for move repetitions
    //         if (Character.isDigit(move)) {
    //             count = count * 10 + (move - '0');
    //             continue;
    //         }
    //         if (count == 0) {
    //             count = 1;
    //         }

    //         for (int j = 0; j < count; j++) {
    //             switch (move) {
    //                 case 'F':
    //                     if (!canMoveForward()) {
    //                         logger.error("Invalid forward move at ({}, {})", row, col);
    //                         return "Incorrect path";
    //                     }
    //                     moveForward();
    //                     break;
    //                 case 'R':
    //                     turnRight();
    //                     break;
    //                 case 'L':
    //                     turnLeft();
    //                     break;
    //                 default:
    //                     logger.error("Invalid path instruction: {}", move);
    //                     return "Incorrect path";
    //             }
    //         }

    //         count = 0; //reset move count after processing
    //     }

    //     //check if the player has reached the maze exit
    //     if (isAtExit()) {
    //         logger.info("Path validation successful: Exit reached!");
    //         return "Path validation successful: Correct path!";
    //     } else {
    //         logger.warn("Path validation failed: Did not reach the exit.");
    //         return "Path validation failed: Incorrect path!";
    //     }
    // }
}