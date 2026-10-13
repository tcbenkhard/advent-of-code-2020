package nl.benkhard.aoc;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class AdapterArraySolverTest {
    private final AdapterArraySolver solver = new AdapterArraySolver();

    @Test
    public void solvesSmallCanonicalExample() {
        assertResult(longs(16, 10, 15, 5, 1, 11, 7, 19, 6, 12, 4), 35, 8);
    }

    @Test
    public void solvesLargeCanonicalExample() {
        assertResult(longs(28, 33, 18, 42, 31, 14, 46, 20, 48, 47, 24, 23,
                49, 45, 19, 38, 39, 11, 1, 32, 25, 35, 8, 17, 7, 9, 4, 2,
                34, 10, 3), 220, 19_208);
    }

    @Test
    public void sortsWithoutMutatingCallerInput() {
        List<Long> shuffled = new ArrayList<>(longs(4, 1, 3, 2));
        List<Long> originalOrder = new ArrayList<>(shuffled);

        assertResult(shuffled, 4, 7);
        assertEquals(originalOrder, shuffled);
    }

    @Test
    public void acceptsTwoJoltGapsAndCountsSingleArrangement() {
        assertResult(longs(2, 4), 0, 1);
        assertResult(longs(3, 6), 0, 1);
    }

    @Test
    public void countsBranchesAndOmittableAdapters() {
        assertResult(longs(1, 2, 3, 4), 4, 7);
    }

    @Test
    public void rejectsImpossibleCompleteChain() {
        try {
            solver.solve(longs(1, 5));
            fail("Expected impossible chain failure");
        } catch (IllegalStateException exception) {
            assertEquals("No valid complete chain: gap from 1 to 5 is 4 jolts",
                    exception.getMessage());
        }
    }

    @Test
    public void rejectsInvalidDirectInput() {
        assertIllegalArgument(() -> solver.solve(null));
        assertIllegalArgument(() -> solver.solve(new ArrayList<Long>()));
        assertIllegalArgument(() -> solver.solve(Arrays.asList(1L, null)));
        assertIllegalArgument(() -> solver.solve(longs(0)));
        assertIllegalArgument(() -> solver.solve(longs(-1)));
        assertIllegalArgument(() -> solver.solve(longs(1, 1)));
    }

    @Test(expected = ArithmeticException.class)
    public void detectsDeviceRatingOverflow() {
        solver.solve(longs(Long.MAX_VALUE));
    }

    @Test(expected = ArithmeticException.class)
    public void detectsArrangementCountOverflow() {
        List<Long> ratings = new ArrayList<>();
        for (long rating = 1; rating <= 100; rating++) {
            ratings.add(rating);
        }
        solver.solve(ratings);
    }

    private void assertResult(List<Long> ratings, long partOne, long partTwo) {
        AdapterArraySolver.Result result = solver.solve(ratings);
        assertEquals(partOne, result.getDifferenceProduct());
        assertEquals(partTwo, result.getArrangementCount());
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

    private interface ThrowingRunnable {
        void run();
    }
}
