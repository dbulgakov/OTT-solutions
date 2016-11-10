package com.dbulgakov.itemranger.other;


import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


public class CustomConcurrentHashMapTest extends BaseTest{
    private CustomConcurrentHashMap<Integer, Integer> customConcurrentHashMap;
    private final static Integer KEY = 1;
    private final static Integer WRONG_KEY = 3;
    private final static Integer VALUE = 2;
    private final static Integer SECOND_VALUE = 5;

    @Before
    public void setUp() throws Exception {
        customConcurrentHashMap = new CustomConcurrentHashMap<>();
        customConcurrentHashMap.putValues(KEY, VALUE);
    }

    @Test
    public void testInsertion() throws Exception {
        assertEquals(true, customConcurrentHashMap.containsKey(KEY));
        assertEquals(2, (int) customConcurrentHashMap.getValues(KEY));
        assertEquals(1, customConcurrentHashMap.getSize());
    }

    @Test
    public void testInsertionSeveralItems() throws Exception {
        customConcurrentHashMap.putValues(KEY, SECOND_VALUE);
        assertEquals(true, customConcurrentHashMap.containsKey(KEY));
        assertEquals(1, customConcurrentHashMap.getSize());

        assertEquals(VALUE, customConcurrentHashMap.getValues(KEY));
        assertEquals(SECOND_VALUE, customConcurrentHashMap.getValues(KEY));
        assertEquals(VALUE, customConcurrentHashMap.getValues(KEY));
    }

    @Test
    public void testDeletion() throws Exception {
        customConcurrentHashMap.removeKey(KEY);
        assertEquals(0, customConcurrentHashMap.getSize());
    }

    @Test
    public void testNoElement() throws Exception {
        try {
            customConcurrentHashMap.getValues(WRONG_KEY);
            fail();
        } catch (Exception expected) {
            assertEquals(true, expected instanceof NoSuchElementException);
        }
    }
}
