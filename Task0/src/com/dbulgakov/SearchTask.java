package com.dbulgakov;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


class SearchTask implements Runnable{

    private final CustomConcurrentHashMap<Character, Pair<Integer, Integer>> lettersMap;
    private final String row;
    private Pattern pattern;
    private final int colIndex;

    SearchTask(CustomConcurrentHashMap<Character, Pair<Integer, Integer>> lettersMap, String row, Pattern pattern, int colIndex) {
        this.lettersMap = lettersMap;
        this.row = row;
        this.pattern = pattern;
        this.colIndex = colIndex;
    }

    @Override
    public void run() {
        Matcher matcher = pattern.matcher(row);

        while (matcher.find()) {
            //System.out.println(matcher.group());
            // adding found character into hashmap
            lettersMap.putValues(matcher.group().charAt(0), new Pair<>(colIndex, matcher.start()));

            // if only found character and braces left (3 characters), so breaking the loop
            if (pattern.toString().length() == 3) {
                notifyAll();
                break;
            }

            // updating regex expression
            pattern = Pattern.compile(pattern.toString().replaceFirst(matcher.group(), ""), Pattern.CASE_INSENSITIVE);
        }
    }
}
