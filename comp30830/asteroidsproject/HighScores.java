package comp30830.asteroidsproject;

import java.io.File;
import java.io.FileNotFoundException;
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
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
        return highScores;
    }
}
