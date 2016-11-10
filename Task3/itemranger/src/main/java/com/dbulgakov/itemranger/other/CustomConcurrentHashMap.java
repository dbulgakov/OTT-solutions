package com.dbulgakov.itemranger.other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class CustomConcurrentHashMap<K, V> extends java.util.concurrent.ConcurrentHashMap<K, List<V>> {

    private int counter;
    private Map<K, Integer> occurNumber;

    public CustomConcurrentHashMap() {
        super();
        occurNumber = new HashMap<>();
        counter = 0;
    }


    public void putValues(K key, final V value) {
        synchronized (this) {
            if (!containsKey(key)) {
                put(key, new ArrayList<V>() {{ add(value);}});
                counter++;
                return;
            }
            get(key).add(value);
        }
    }


    public V getValues(Object object) {
        if (containsKey(object)) {
            return getValue(object);
        }
        throw new NoSuchElementException();
    }


    @SuppressWarnings({"unchecked", "SuspiciousMethodCalls"})
    private V getValue(Object object) {
        synchronized (this) {
            V value = null;
            List<V> tmpValueList = get(object);

            if (tmpValueList.size() > 1) {
                int currentIndex = 0;
                if (occurNumber.containsKey(object)) {
                    currentIndex = occurNumber.get(object);
                }

                value = tmpValueList.get(currentIndex);
                occurNumber.put((K)object, getNextIndex(tmpValueList, currentIndex));

            } else {
                value = tmpValueList.get(0);
            }
            return value;
        }
    }

    private int getNextIndex(List<V> valueList, int currentIndex) {
        if (currentIndex + 1 < valueList.size()) {
            return currentIndex + 1;
        } else {
            return 0;
        }
    }

    public int getSize() {
        return counter;
    }

    public void removeKey(Object key) {
        if (containsKey(key)) {
            counter -= 1;
            if (occurNumber.containsKey(key)) {
                occurNumber.remove(key);
            }
        }
    }
}