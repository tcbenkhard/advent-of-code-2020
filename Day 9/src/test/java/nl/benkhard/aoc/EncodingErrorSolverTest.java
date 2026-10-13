package nl.benkhard.aoc;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class EncodingErrorSolverTest {
    private final EncodingErrorSolver solver = new EncodingErrorSolver();

    @Test
    public void solvesStandardSample() {
        List<Long> numbers = longs(35, 20, 15, 25, 47, 40, 62, 55, 65, 95,
                102, 117, 150, 182, 127, 219, 299, 277, 309, 576);

        EncodingErrorSolver.InvalidNumber invalid = solver.findFirstInvalid(numbers, 5);
        EncodingErrorSolver.Range range =
                solver.findContiguousRange(numbers, invalid.getIndex(), invalid.getValue());

        assertEquals(14, invalid.getIndex());
        assertEquals(127L, invalid.getValue());
        assertEquals(2, range.getStartIndex());
        assertEquals(5, range.getEndIndex());
        assertEquals(62L, solver.calculateWeakness(numbers, range));
    }

    @Test
    public void pairRequiresDifferentPositionsButAllowsDuplicateValues() {
        assertEquals(2, solver.findFirstInvalid(longs(20, 1, 40), 2).getIndex());
        assertEquals(3, solver.findFirstInvalid(longs(20, 20, 40, 41), 2).getIndex());
    }

    @Test
    public void pairMustBeInsideCurrentWindowAndCannotUseFutureValues() {
        assertEquals(3, solver.findFirstInvalid(longs(5, 7, 12, 17), 2).getIndex());
        assertEquals(2, solver.findFirstInvalid(longs(5, 7, 20, 15), 2).getIndex());
    }

    @Test
    public void returnsFirstInvalidCandidate() {
        EncodingErrorSolver.InvalidNumber invalid =
                solver.findFirstInvalid(longs(1, 2, 3, 10, 20), 2);

        assertEquals(3, invalid.getIndex());
        assertEquals(10L, invalid.getValue());
    }

    @Test
    public void rangeMayContainExactlyTwoEntriesButNotOne() {
        EncodingErrorSolver.Range range = solver.findContiguousRange(longs(4, 6, 20), 3, 10);
        assertEquals(0, range.getStartIndex());
        assertEquals(1, range.getEndIndex());

        assertIllegalState(() -> solver.findContiguousRange(longs(10, 1), 2, 10));
    }

    @Test
    public void rangeSelectionUsesEarliestStartThenEarliestEnd() {
        EncodingErrorSolver.Range range =
                solver.findContiguousRange(longs(1, 2, 3, 3, 0), 5, 6);

        assertEquals(0, range.getStartIndex());
        assertEquals(2, range.getEndIndex());
    }

    @Test
    public void reportsFullyValidInputAndMissingRange() {
        assertIllegalState(() -> solver.findFirstInvalid(longs(1, 2, 3), 2));
        assertIllegalState(() -> solver.findContiguousRange(longs(1, 2, 8), 2, 8));
    }

    @Test
    public void supportsValuesBeyondIntegerRange() {
        EncodingErrorSolver.InvalidNumber invalid =
                solver.findFirstInvalid(longs(3_000_000_000L, 4_000_000_000L,
                        7_000_000_000L, 8_000_000_000L), 2);

        assertEquals(3, invalid.getIndex());
        assertEquals(8_000_000_000L, invalid.getValue());
    }

    @Test
    public void rejectsInvalidPreambleAndInsufficientInput() {
        assertIllegalArgument(() -> solver.findFirstInvalid(longs(1, 2), 1));
        assertIllegalArgument(() -> solver.findFirstInvalid(longs(1, 2), 2));
    }

    @Test(expected = ArithmeticException.class)
    public void detectsPairSumOverflow() {
        solver.findFirstInvalid(longs(Long.MAX_VALUE, 1, 5), 2);
    }

    @Test(expected = ArithmeticException.class)
    public void detectsRangeSumOverflow() {
        solver.findContiguousRange(longs(Long.MAX_VALUE, 1), 2, 0);
    }

    @Test(expected = ArithmeticException.class)
    public void detectsWeaknessOverflow() {
        solver.calculateWeakness(
                longs(Long.MAX_VALUE, Long.MAX_VALUE), new EncodingErrorSolver.Range(0, 1));
    }

    private static List<Long> longs(long... values) {
        Long[] boxed = new Long[values.length];
        for (int index = 0; index < values.length; index++) {
            boxed[index] = values[index];
        }
        return Arrays.asList(boxed);
    }

    private static void assertIllegalArgument(ThrowingRunnable operation) {
        try {
            operation.run();
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // Expected.
        }
    }

    private static void assertIllegalState(ThrowingRunnable operation) {
        try {
            operation.run();
            fail("Expected IllegalStateException");
        } catch (IllegalStateException expected) {
            // Expected.
        }
    }

    private interface ThrowingRunnable {
        void run();
    }
}
