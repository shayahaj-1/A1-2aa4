package ca.mcmaster.se2aa4.mazerunner;

import java.util.ArrayList;

class Path {
    private ArrayList<Character> steps;
    private String canonical = "";
    private String factorized = "";


    public Path() {
        steps = new ArrayList<>();
    }

    public void addStep(char step) {
        steps.add(step);
    }

    public String getCanonicalPath() {
        char currentPath = steps.get(0);
        for (char instruction : steps) {
            if (instruction != currentPath) {
                canonical += " ";
            }
            canonical += instruction;
            currentPath = instruction;
        }
        return canonical;
    }

    public String getFactorizedPath() {
        char currentPath = steps.get(0);
        int count = 0;

        for (char instruction : steps) {
            if (instruction == currentPath) {
                count++;
            }
            else {
                if (count != 1) {
                    factorized += count;
                }
                factorized = factorized + currentPath + " ";
                currentPath = instruction;
                count = 1;
            }
        }

        if (count != 1) {
            factorized += count;
        }
        factorized = factorized + currentPath + " ";
        return factorized;
    }
}