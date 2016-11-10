package com.dbulgakov.itemranger;

import android.annotation.TargetApi;
import android.os.Build;

import com.dbulgakov.itemranger.interfaces.RangeAble;
import com.dbulgakov.itemranger.other.CustomConcurrentHashMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ItemRanger {

    public final static int DEFAULT_ELEMENTS_RANGE = -1;
    private final static int MIN_RANGE_SIZE = 4;
    private List<Integer> filterRanges;
    private HashMap<Integer, Integer> rangeCountMap;
    private CustomConcurrentHashMap<Integer, Integer> countRangeMap;
    private List<Integer> topRanges;
    private int maxBetweenRangesNumber;
    private int minExistingRangeValue = DEFAULT_ELEMENTS_RANGE;
    private int maxExistingRangeValue = DEFAULT_ELEMENTS_RANGE;
    private List<RangeAble> itemsList;

    public ItemRanger(List<Integer> ranges, int maxBetweenRangesNumber) {
        this.filterRanges = ranges;
        this.maxBetweenRangesNumber = maxBetweenRangesNumber;
        if (maxBetweenRangesNumber > ranges.size()) {
            throw new IllegalStateException("max range number must be smaller then ranges list!");
        }
        rangeCountMap = new HashMap<>();
        countRangeMap = new CustomConcurrentHashMap<>();
    }

    private void countRanges(List<RangeAble> rangeableItemsList) {
        calcExistingRanges(rangeableItemsList);
        topRanges = calculateTopRanges();
        Collections.sort(topRanges);
    }

    private void calcExistingRanges(List<RangeAble> rangeableItemsList){
        itemsList = new ArrayList<>(rangeableItemsList);
        Collections.sort(itemsList, Comparable::compareTo);

        int currentRange = filterRanges.get(0);
        int currentRangeId = 0;
        int itemCounter = 0;

        for (RangeAble item : itemsList) {
            while (item.getValueToRangeBy() >= currentRange) {
                countRangeMap.putValues(itemCounter, currentRange);
                currentRangeId++;
                currentRange = filterRanges.get(currentRangeId);
                itemCounter = 0;
            }

            if (minExistingRangeValue == -1) {
                minExistingRangeValue = filterRanges.get(currentRangeId - 1);
            }

            if (currentRange > maxExistingRangeValue) {
                maxExistingRangeValue = currentRange;
            }

            rangeCountMap.put(currentRange, ++itemCounter);
        }

        countRangeMap.putValues(itemCounter, currentRange);
    }


    private List<Integer> calcSortedRanges(){
        List<Integer> sortedRangesList = new ArrayList<>(rangeCountMap.values());
        sortDescending(sortedRangesList);
        return sortedRangesList;
    }

    private List<Integer> calculateTopRanges() {
        int rangeIndex = -1;


        List<Integer> sortedRanges = calcSortedRanges();
        List<Integer> foundRanges = new ArrayList<>();

        while ((rangeIndex < sortedRanges.size() - 1) && foundRanges.size() < maxBetweenRangesNumber) {
            rangeIndex++;

            if (sortedRanges.get(rangeIndex) >= MIN_RANGE_SIZE) {
                foundRanges.add(countRangeMap.getValues(sortedRanges.get(rangeIndex)));
            }
        }

        return foundRanges;
    }


    public List<Range> getSelectedRanges(List<RangeAble> rangeableItemsList) {

        countRanges(rangeableItemsList);

        List<Range> selectedRanges;
        if (!topRanges.isEmpty()) {
            selectedRanges =  getSelectedRangesNormally();
        } else {
            selectedRanges = Collections.singletonList(new Range(minExistingRangeValue, maxExistingRangeValue, itemsList.size()));
        }
        return selectedRanges;
    }


    @TargetApi(Build.VERSION_CODES.N)
    private List<Range> getSelectedRangesNormally(){
        List<Range> selectedRanges = new ArrayList<>();
        List<Integer> allExistingRanges = getAllRangesBetween(minExistingRangeValue, maxExistingRangeValue);

        int rangeMin = allExistingRanges.get(0);
        int allRangesIterator = 0;
        boolean hitLastTopValue = false;

        for(int topRange:topRanges) {
            int elementCounter = 0;
            while (allRangesIterator < allExistingRanges.size() && (allExistingRanges.get(allRangesIterator) <= topRange || hitLastTopValue)) {
                elementCounter += rangeCountMap.getOrDefault(allExistingRanges.get(allRangesIterator), 0);

                if (topRange == topRanges.get(topRanges.size() - 1)) {
                    hitLastTopValue = true;
                }

                allRangesIterator += 1;
            }

            selectedRanges.add(new Range(rangeMin, topRange, elementCounter));
            rangeMin = topRange;
        }

        selectedRanges.get(selectedRanges.size() - 1).setEndValue(Integer.MAX_VALUE);
        return selectedRanges;
    }


    private void sortDescending(List<Integer> listToSort) {
        Collections.sort(listToSort, Collections.reverseOrder());
    }

    @TargetApi(Build.VERSION_CODES.N)
    private List<Integer> getAllRangesBetween(int leftEdge, int rightEdge) {
        return filterRanges.stream().filter(range -> range >= leftEdge && range <= rightEdge).
                collect(Collectors.toList());
    }
}