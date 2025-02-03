package ca.mcmaster.se2aa4.mazerunner;

import java.util.ArrayList;

class Path {

    //initialize variable
    private final ArrayList<Character> steps;

    //constructor
    public Path() {
        steps = new ArrayList<>();
    }

    //add a step to the path
    public void addStep(char step) {
        steps.add(step);
    }

    //get canonical path
    public String getCanonicalPath() {
        StringBuilder canonical = new StringBuilder();
        char currentPath = steps.get(0);
        for (char instruction : steps) {
            if (instruction != currentPath) {
                canonical.append(" ");
            }
            canonical.append(instruction);
            currentPath = instruction;
        }
        return canonical.toString();
    }

    //get factorized path
    public String getFactorizedPath() {
        StringBuilder factorized = new StringBuilder();
        char currentPath = steps.get(0);
        int count = 0;

        for (char instruction : steps) {
            if (instruction == currentPath) {
                count++;
            } else {
                if (count != 1) {
                    factorized.append(count);
                }
                factorized.append(currentPath).append(" ");
                currentPath = instruction;
                count = 1;
            }
        }

        if (count != 1) {
            factorized.append(count);
        }
        factorized.append(currentPath).append(" ");
        return factorized.toString();
    }
}
