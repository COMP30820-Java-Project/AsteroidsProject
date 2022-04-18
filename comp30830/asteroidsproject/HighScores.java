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

    static void updateHighScores(String name, int score) {
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
        String[] highScoreNames = new String[5];
        int loopIndex = 0;
        for (String highScore : highScores) {
            String[] highScoreSplit = highScore.split("-");
            int highScoreValue = Integer.parseInt(highScoreSplit[1].trim());
            highScoreValues[loopIndex] = highScoreValue;

            String highScoreName = highScoreSplit[0].trim();
            highScoreNames[loopIndex] = highScoreName;
            loopIndex++;

            System.out.println("High score split has split the value" +
                    highScoreName + " and " + highScoreValue);
        }

        highScoreValues[4] = score;
        int[] sortedHighScoreValues = BubbleSort.highScoreSort(highScoreValues);
        System.out.println(Arrays.toString(sortedHighScoreValues));
        int indexOfScore = 0;
        for (int index = 0; index < sortedHighScoreValues.length; index++) {
            if (score == sortedHighScoreValues[index])
                indexOfScore += index;
        }

        String[] highScoreNamesAdjusted = new String[5];
        for (int nameIndex = 0; nameIndex < highScoreNames.length; nameIndex++) {
            highScoreNamesAdjusted[nameIndex] = highScoreNames[nameIndex];
        }

        highScoreNamesAdjusted[indexOfScore] = name;
        for (int i = indexOfScore + 1; i < highScoreNamesAdjusted.length; i++) {
            highScoreNamesAdjusted[i] = highScoreNames[i - 1];
        }
        System.out.println(Arrays.toString(highScoreNamesAdjusted));

        String highScoreStringForFile = "";

        for (int i = 0; i < highScores.length; i++) {
            highScores[i] = highScoreNamesAdjusted[i] + " - " +
                    sortedHighScoreValues[i] + System.lineSeparator();
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