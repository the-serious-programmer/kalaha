package com.bol.kalaha.model.comparator;

import java.util.Arrays;
import java.util.List;

import com.bol.kalaha.model.Pit;
import com.bol.kalaha.model.type.PitType;
import com.bol.kalaha.supplier.PitTestSupplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.bol.kalaha.supplier.CompareTestSupplier.GREATER_THAN;
import static com.bol.kalaha.supplier.CompareTestSupplier.LESS_THAN;
import static com.bol.kalaha.supplier.CompareTestSupplier.SAME;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PitPositionComparatorTest {
    private static final Pit REGULAR_ZERO = PitTestSupplier.getPit(0, PitType.REGULAR);
    private static final Pit REGULAR_ONE = PitTestSupplier.getPit(1, PitType.REGULAR);
    private static final Pit SCORE_ZERO = PitTestSupplier.getPit(0, PitType.SCORE);

    private PitPositionComparator pitPositionComparator;

    @BeforeEach
    void setUp() {
        pitPositionComparator = new PitPositionComparator();
    }

    @Test
    void shouldComparePosition() {
        assertEquals(SAME, pitPositionComparator.compare(REGULAR_ZERO, REGULAR_ZERO));
        assertEquals(LESS_THAN, pitPositionComparator.compare(REGULAR_ZERO, REGULAR_ONE));
        assertEquals(GREATER_THAN, pitPositionComparator.compare(REGULAR_ONE, REGULAR_ZERO));
        assertEquals(SAME, pitPositionComparator.compare(SCORE_ZERO, REGULAR_ZERO));
        assertEquals(GREATER_THAN, pitPositionComparator.compare(REGULAR_ONE, SCORE_ZERO));
    }

    @Test
    void shouldSortList() {
        final List<Pit> input = Arrays.asList(REGULAR_ONE, REGULAR_ZERO, REGULAR_ONE, SCORE_ZERO);
        final List<Pit> expectedResult = Arrays.asList(REGULAR_ZERO, SCORE_ZERO, REGULAR_ONE, REGULAR_ONE);
        input.sort(pitPositionComparator);
        assertEquals(expectedResult, input);
    }

    @Test
    void shouldSortListReversed() {
        final List<Pit> input = Arrays.asList(REGULAR_ONE, REGULAR_ZERO, REGULAR_ONE, SCORE_ZERO);
        final List<Pit> expectedResult = Arrays.asList(REGULAR_ONE, REGULAR_ONE, REGULAR_ZERO, SCORE_ZERO);
        input.sort(pitPositionComparator.reversed());
        assertEquals(expectedResult, input);
    }
}
