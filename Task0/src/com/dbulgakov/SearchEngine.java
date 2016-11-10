package com.dbulgakov;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

class SearchEngine {
    private Pattern pPattern;
    private String sPattern;
    private CustomConcurrentHashMap<Character, Pair<Integer, Integer>> lettersMap;
    private final int coreNumber;

    SearchEngine(String wordToSeek) {

        // setting up search pattern
        pPattern = ConvertPattern(wordToSeek);
        sPattern = wordToSeek;

        // setting up core number for current instance
        coreNumber = Runtime.getRuntime().availableProcessors();
    }

    void ExecuteSearch(String filename) {

        // creating map to store indexes of found letters
        lettersMap = new CustomConcurrentHashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename)))
        {
            // reading first line in order to get number of columns in matrix
            String currentLine = br.readLine();
            String[] fistLineArray = currentLine.split(" ");

            int colNumber = Integer.parseInt(fistLineArray[0]);
            int rowNumber = Integer.parseInt(fistLineArray[1]);

            ExecutorService executorService = Executors.newFixedThreadPool(coreNumber);

            for (int i = 0; i < colNumber; i++) {
                // reading new line ane cutting row size to fit settings
                currentLine = br.readLine().substring(0, rowNumber);

                // submitting new search
                executorService.submit(new SearchTask(lettersMap, currentLine, pPattern, i));
            }

            executorService.shutdown();
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        } catch (FileNotFoundException e) {
            System.out.println("Input file not found! :C");
        } catch (IOException e) {
            System.out.println("Something went wrong while working with IO.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Unexpected exception occurred.");
        }
    }

    void SaveToFile(String filename) {
        CheckState();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename)))
        {
            // checking hashmap size to equal pattern size
            if (lettersMap.getSize() == sPattern.length()) {
                bw.write("Output looks this like. (case insensitive) Hope I understand the task correctly. " +
                        "Symbols, stored in hashmap, are case sensitive, but output looks ugly.\n");
                for(Character letter: sPattern.toCharArray()) {
                    bw.write(String.format("%c - %s;%n", letter, lettersMap.getValues(Character.toLowerCase(letter))));
                }
            }
            else {
                bw.write("Impossible");
            }
            System.out.printf("Successfully saved results into file %s", filename);
        } catch (IOException e) {
            System.out.println("Something went wrong while working with IO.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Unexpected exception occurred.");
        }

    }

    private Pattern ConvertPattern(String wordToSeek) {
        return Pattern.compile("[" + wordToSeek + "]", Pattern.CASE_INSENSITIVE);
    }

    private void CheckState() {
        if (lettersMap == null) {
            throw new IllegalStateException();
        }
    }
}
