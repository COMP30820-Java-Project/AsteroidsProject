package comp30830.asteroidsproject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class HighScores {

    // Static method to read high scores from file
    static String[] readHighScores() {
        String[] highScores = new String[5];

        // Try catch block to read high scores into string array line by line
        try {
            File highScoreFile = new File("comp30830/asteroidsproject/HighScores.txt");
            Scanner fileReader = new Scanner(highScoreFile);
            int lineCount = 0;
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                highScores[lineCount] = line;
                lineCount++;
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("High Score file not found when reading for high score menu");
            e.printStackTrace();
        }

        // Return String array
        return highScores;
    }

    // Static method called to update high score file with the argument value
    static void updateHighScores(int score) {
        String[] highScores = new String[5];

        // Try catch block reads high score file content into string array
        try {
            File highScoreFile = new File("comp30830/asteroidsproject/HighScores.txt");
            Scanner fileReader = new Scanner(highScoreFile);
            int lineCount = 0;
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                highScores[lineCount] = line;
                lineCount++;
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("High Score file not found when updating high scores");
            e.printStackTrace();
        }

        // Int array created to compare high score values
        int[] highScoreValues = new int[5];
        for (int i = 0; i < highScores.length; i++) {
            highScoreValues[i] = Integer.parseInt(highScores[i]);
        }

        // Lowest high score removed and values sorted in descending order
        highScoreValues[4] = score;
        int[] sortedHighScoreValues = BubbleSort.highScoreSort(highScoreValues);

        // Create string to write to file and concatenate each int to this file with
        // system-independent file separator
        String highScoreStringForFile = "";

        for (int i = 0; i < highScores.length; i++) {
            highScores[i] = Integer.toString(sortedHighScoreValues[i]) + System.lineSeparator();
        }

        for (String line : highScores) {
            highScoreStringForFile += line;
        }

        // Try catch block to write string to file
        try {
            FileWriter fileWriter = new FileWriter("comp30830/asteroidsproject/HighScores.txt", false);
            fileWriter.write(highScoreStringForFile);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred writing to the high score file");
            e.printStackTrace();
        }

    }

    static int getLowestHighScore() {

        // Static method to return the lowest high score value. Used to determine
        // when to update high scores

        String[] highScores = new String[5];

        // Try catch block to read contents of high score file
        try {
            File highScoreFile = new File("comp30830/asteroidsproject/HighScores.txt");
            Scanner fileReader = new Scanner(highScoreFile);
            int lineCount = 0;
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                highScores[lineCount] = line;
                lineCount++;
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("High Score file not found when reading for high score menu");
            e.printStackTrace();
        }

        // Return smallest value as an integer
        return Integer.parseInt(highScores[4]);
    }
}