package com.bol.kalaha.model.comparator;

import java.util.Arrays;
import java.util.List;

import com.bol.kalaha.model.Pit;
import com.bol.kalaha.model.type.PitType;
import com.bol.kalaha.supplier.PitTestSupplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PitComparatorTest {
    private static final int LESS_THAN = -1;
    private static final int GREATER_THAN = 1;
    private static final int SAME = 0;
    private static final Pit REGULAR_ZERO = PitTestSupplier.getPit(0, PitType.REGULAR);
    private static final Pit REGULAR_ONE = PitTestSupplier.getPit(1, PitType.REGULAR);
    private static final Pit SCORE_ZERO = PitTestSupplier.getPit(0, PitType.SCORE);

    private PitComparator pitComparator;

    @BeforeEach
    void setUp() {
        pitComparator = new PitComparator();
    }

    @Test
    void shouldCompareByTypeAndPosition() {
        assertEquals(SAME, pitComparator.compare(REGULAR_ZERO, REGULAR_ZERO));
        assertEquals(LESS_THAN, pitComparator.compare(REGULAR_ZERO, REGULAR_ONE));
        assertEquals(GREATER_THAN, pitComparator.compare(REGULAR_ONE, REGULAR_ZERO));
        assertEquals(GREATER_THAN, pitComparator.compare(SCORE_ZERO, REGULAR_ZERO));
        assertEquals(LESS_THAN, pitComparator.compare(REGULAR_ONE, SCORE_ZERO));
    }

    @Test
    void shouldSortList() {
        final List<Pit> input = Arrays.asList(REGULAR_ONE, SCORE_ZERO, REGULAR_ONE, REGULAR_ZERO);
        final List<Pit> expectedResult = Arrays.asList(REGULAR_ZERO, REGULAR_ONE, REGULAR_ONE, SCORE_ZERO);
        input.sort(pitComparator);
        assertEquals(expectedResult, input);
    }

    @Test
    void shouldSortListReversed() {
        final List<Pit> input = Arrays.asList(REGULAR_ONE, SCORE_ZERO, REGULAR_ONE, REGULAR_ZERO);
        final List<Pit> expectedResult = Arrays.asList(SCORE_ZERO, REGULAR_ONE, REGULAR_ONE, REGULAR_ZERO);
        input.sort(pitComparator.reversed());
        assertEquals(expectedResult, input);
    }
}
