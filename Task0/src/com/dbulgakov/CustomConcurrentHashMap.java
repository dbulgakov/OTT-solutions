package com.dbulgakov;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

class CustomConcurrentHashMap<K, V> extends java.util.concurrent.ConcurrentHashMap<K, List<V>> {

    private int counter;

    CustomConcurrentHashMap() {
        super();
        counter = 0;
    }

    // Updating CustomConcurrentHashMap logic to fit currents tasks
    void putValues(K key, V value) {
        synchronized (this) {
            // increasing custom size value
            counter++;
            if (!containsKey(key)) {
                // creating arraylist and putting it into hashmap
                put(key, new ArrayList<V>() {{ add(value);}});
                return;
            }
            get(key).add(value);
        }
    }


    V getValues(Object object) {
        if (containsKey(object)) {
            return getValue(object);
        } else if (object instanceof Character && containsKey(Character.toUpperCase((Character)object))) {
            return getValue(Character.toUpperCase((Character)object));
        }
        throw new NoSuchElementException();
    }

    private V getValue(Object object) {
        synchronized (this) {
            V value = get(object).get(0);
            get(object).remove(0);
            if (get(object).size() == 0) {
                remove(object);
            }
            return value;
        }
    }

    int getSize() {
        return counter;
    }
}
