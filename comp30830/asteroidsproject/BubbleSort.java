package comp30830.asteroidsproject;

interface BubbleSort {

    static int[] highScoreSort(int[] highScores) {

        for (int i = highScores.length - 1; i > 0; i--) {
            boolean hasSwapped = false;
            for (int j = i; j > 0; j--) {
                if (highScores[j] > highScores[j - 1]) {
                    int temp = highScores[j - 1];
                    highScores[j - 1] = highScores[j];
                    highScores[j] = temp;
                    hasSwapped = true;
                }
            }
            if (!hasSwapped)
                break;
        }

        return highScores;
    }
}
