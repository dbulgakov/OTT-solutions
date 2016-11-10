package com.dbulgakov.itemranger;

import com.dbulgakov.itemranger.interfaces.RangeAble;
import com.dbulgakov.itemranger.other.BaseTest;
import com.dbulgakov.itemranger.other.TestRangeAble;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ItemRangerTest extends BaseTest{

    private ItemRanger itemRanger;
    private final static int MAX_RANGE_NUMBER = 3;
    private final static int WRONG_MAX_RANGE_NUMBER = 10;
    private List<Integer> ranges;

    @Before
    public void setUp() throws Exception {
        ranges = Arrays.asList(0, 100, 1000, 2000, 4000, Integer.MAX_VALUE);
        itemRanger = new ItemRanger(ranges, MAX_RANGE_NUMBER);
    }

    @Test
    public void testLessThanFourItems() throws Exception {
        List<Range> selectedRanges = itemRanger.getSelectedRanges(getRangeAble(3));
        assertEquals(1, selectedRanges.size());
        assertEquals("0 - 1000", selectedRanges.get(0).toString());
    }

    @Test
    public void testSeveralGroupsLessFour() throws Exception {
        List<Range> selectedRanges = itemRanger.getSelectedRanges(getRangeAble(10));
        assertEquals("0+", selectedRanges.get(0).toString());
    }

    @Test
    public void testSeveralGroupsLess() throws Exception {
        List<Range> selectedRanges = itemRanger.getSelectedRanges(getRangeAble(20));
        assertEquals(2, selectedRanges.size());
    }

    @Test
    public void testAllGroups() throws Exception {
        List<Range> selectedRanges = itemRanger.getSelectedRanges(getRangeAble(30));
        assertEquals(3, selectedRanges.size());
    }



    @Test
    public void testCreateInstanceError() throws Exception {
        try
        {
            new ItemRanger(ranges, WRONG_MAX_RANGE_NUMBER);
            fail();
        } catch (Exception expected) {
            assertEquals(true, expected instanceof IllegalStateException);
        }
    }

    private List<RangeAble> getRangeAble(int count) {
        List<RangeAble> rangeAbles = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            rangeAbles.add(new TestRangeAble(i * 100));
        }
        return rangeAbles;
    }
}
