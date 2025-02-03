package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class Player {

    //calling logger and initialize variables
    private static final Logger logger = LogManager.getLogger();
    private Maze maze;
    private int row;
    private int col;
    private String direction;

    //constructor
    public Player(Maze maze, int startRow, int startCol) {
        this.maze = maze;
        this.row = startRow;
        this.col = startCol;
        this.direction = "EAST";
        logger.info("Runner initialized at ({}, {}) facing {}", row, col, direction);
    }

    //path explorer
    public Path explore() {
        Path path = new Path();
        logger.info("Starting maze exploration using right-hand wall-following.");
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

    private boolean isAtExit() {
        return col == maze.getCols() - 1;
    }

    //movement functions
    private boolean canMoveForward() {
        int[] nextPos = moveForwardPosition(row, col, direction);
        return maze.canPass(nextPos[0], nextPos[1]);
    }

    private boolean canMoveRight() {
        String rightDirection = turnRight(direction);
        int[] nextPos = moveForwardPosition(row, col, rightDirection);
        return maze.canPass(nextPos[0], nextPos[1]);
    }

    private void moveForward() {
        int[] newPos = moveForwardPosition(row, col, direction);
        row = newPos[0];
        col = newPos[1];
        logger.info("Moved to position ({}, {})", row, col);
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

    private void turnRight() {
        direction = turnRight(direction);
        logger.info("Turned right, now facing {}", direction);
    }

    private void turnLeft() {
        direction = turnLeft(direction);
        logger.info("Turned left, now facing {}", direction);
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

    //path validation
    public boolean isPathValid(String path) {
        logger.info("Validating path: {}", path);
        
        //remove spaces
        path = path.replace(" ", "");
        int currentRow = row;
        int currentCol = col;
        String currentDirection = direction;
        int i = 0;

        while (i < path.length()) {
            char step = path.charAt(i);

            if (Character.isDigit(step)) {
                int count = 0;

                //parse digits
                while (i < path.length() && Character.isDigit(path.charAt(i))) {
                    count = count * 10 + (path.charAt(i) - '0');
                    i++;
                }

                if (i < path.length()) {
                    step = path.charAt(i);
                    for (int j = 0; j < count; j++) {
                        if (!processStep(step, currentRow, currentCol, currentDirection)) {
                            return false;
                        }
                    }
                }
            } else {
                if (!processStep(step, currentRow, currentCol, currentDirection)) {
                    return false;
                }
            }
            i++;
        }

        logger.info("Path validation successful.");
        return true;
    }

    //process each step given with -p flag
    private boolean processStep(char step, int row, int col, String direction) {
        switch (step) {
            case 'F':
                int[] newPos = moveForwardPosition(row, col, direction);
                if (!maze.canPass(newPos[0], newPos[1])) {
                    logger.warn("Invalid move detected at ({}, {})", row, col);
                    return false;
                }
                break;
            case 'L':
                direction = turnLeft(direction);
                break;
            case 'R':
                direction = turnRight(direction);
                break;
            default:
                logger.error("Invalid path instruction: {}", step);
                return false;
        }
        return true;
    }

}