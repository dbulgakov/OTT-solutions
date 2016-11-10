package com.dbulgakov;

/**
 * Simple console app to find string "OneTwoTrip" in char matrix.
 * In output displays each char index in case of success and "Impossible" otherwise.
 *
 * @author  Dmitriy Bulgakov
 * @version 0.1
 * @since   2016-07-2
 */

public class Main {
    private static final String INPUT_FILE_NAME="input.txt";
    private static final String OUTPUT_FILE_NAME="output.txt";
    private static final String COOL_TRAVEL_COMPANY_NAME="onetwotrip";

    public static void main(String[] args) {
        // getting start time
        long startTime = System.nanoTime();

        // setting up our simple search engine with regex expression. (looking only for one character)
        SearchEngine searchEngine = new SearchEngine(COOL_TRAVEL_COMPANY_NAME);

        // executing search and saving results
        searchEngine.ExecuteSearch(INPUT_FILE_NAME);
        System.out.printf("Time elapsed: %.3f seconds%n", ((System.nanoTime() - startTime) / 1e9));
        searchEngine.SaveToFile(OUTPUT_FILE_NAME);
    }
}