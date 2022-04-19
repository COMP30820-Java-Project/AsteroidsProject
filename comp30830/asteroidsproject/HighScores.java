package comp30830.asteroidsproject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class HighScores {

    static String[] readHighScores() {
        String[] highScores = new String[5];
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
        return highScores;
    }

    static void updateHighScores(int score) {
        String[] highScores = new String[5];
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

        int[] highScoreValues = new int[5];
        for (int i = 0; i < highScores.length; i++) {
            highScoreValues[i] = Integer.parseInt(highScores[i]);
        }

        highScoreValues[4] = score;
        int[] sortedHighScoreValues = BubbleSort.highScoreSort(highScoreValues);


        String highScoreStringForFile = "";

        for (int i = 0; i < highScores.length; i++) {
            highScores[i] = Integer.toString(sortedHighScoreValues[i]) + System.lineSeparator();
        }

        for (String line : highScores) {
            highScoreStringForFile += line;
        }

        try {
            FileWriter fileWriter = new FileWriter("comp30830/asteroidsproject/HighScores.txt", false);
            fileWriter.write(highScoreStringForFile);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred writing to the high score file");
            e.printStackTrace();
        }
    }
}