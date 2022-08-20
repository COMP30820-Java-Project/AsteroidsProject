package comp30830.asteroidsproject;

interface BubbleSort {

    static int[] highScoreSort(int[] highScores) {
        /*
        Bubble sort method to sort high scores in descending order

        Should run in linear time (passes through loop once and then on
        second pass nothing should have changed)
         */

        for (int i = highScores.length - 1; i > 0; i--) {

            // Has Swapped check to stop loop if list already sorted
            boolean hasSwapped = false;
            for (int j = i; j > 0; j--) {
                if (highScores[j] > highScores[j - 1]) {

                    // Temporary placeholder used for var swapping
                    int temp = highScores[j - 1];
                    highScores[j - 1] = highScores[j];
                    highScores[j] = temp;
                    hasSwapped = true;
                }
            }
            if (!hasSwapped)
                break;
        }

        // Sorts in place so same list returned
        return highScores;
    }
}
