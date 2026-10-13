package nl.benkhard.aoc;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class AdapterSolverTest {
    private final AdapterSolver solver = new AdapterSolver();

    @Test
    public void solvesCanonicalSmallSample() {
        assertResult(longs(16, 10, 15, 5, 1, 11, 7, 19, 6, 12, 4), 35, 8);
    }

    @Test
    public void solvesCanonicalLargeSample() {
        assertResult(longs(28, 33, 18, 42, 31, 14, 46, 20, 48, 47, 24, 23,
                49, 45, 19, 38, 39, 11, 1, 32, 25, 35, 8, 17, 7, 9, 4, 2,
                34, 10, 3), 220, 19208);
    }

    @Test
    public void includesOutletAndDeviceAndSupportsTwoJoltTransitions() {
        assertResult(longs(1, 4), 2, 1);
        assertResult(longs(2, 3), 1, 2);
        assertResult(longs(3), 0, 1);
    }

    @Test
    public void inputOrderDoesNotMatterAndCallerInputIsUnchanged() {
        List<Long> ratings = new ArrayList<>(longs(4, 1, 3, 2));
        List<Long> original = new ArrayList<>(ratings);

        AdapterSolver.Result forward = solver.solve(ratings);
        Collections.reverse(ratings);
        AdapterSolver.Result reverse = solver.solve(ratings);

        assertEquals(4, forward.getDifferenceProduct());
        assertEquals(7, forward.getArrangementCount());
        assertEquals(forward.getDifferenceProduct(), reverse.getDifferenceProduct());
        assertEquals(forward.getArrangementCount(), reverse.getArrangementCount());
        Collections.reverse(ratings);
        assertEquals(original, ratings);
    }

    @Test
    public void rejectsInvalidSolverInputsAndUnreachableChains() {
        assertIllegalArgument(() -> solver.solve(null));
        assertIllegalArgument(() -> solver.solve(Collections.<Long>emptyList()));
        assertIllegalArgument(() -> solver.solve(Arrays.asList(1L, null)));
        assertIllegalArgument(() -> solver.solve(longs(0)));
        assertIllegalArgument(() -> solver.solve(longs(-1)));
        assertIllegalArgument(() -> solver.solve(longs(1, 1)));
        assertIllegalArgument(() -> solver.solve(longs(1, 5)));
        assertIllegalArgument(() -> solver.solve(longs(4)));
    }

    @Test(expected = ArithmeticException.class)
    public void detectsDeviceRatingOverflow() {
        solver.solve(longs(Long.MAX_VALUE));
    }

    @Test(expected = ArithmeticException.class)
    public void detectsDifferenceProductOverflow() {
        AdapterSolver.multiplyDifferenceCounts(Long.MAX_VALUE, 2);
    }

    @Test(expected = ArithmeticException.class)
    public void detectsArrangementCountOverflow() {
        List<Long> ratings = new ArrayList<>();
        for (long rating = 1; rating <= 73; rating++) {
            ratings.add(rating);
        }
        solver.solve(ratings);
    }

    @Test
    public void solvesLongDenseChainWithoutEnumeratingSubsets() {
        List<Long> ratings = new ArrayList<>();
        for (long rating = 1; rating <= 40; rating++) {
            ratings.add(rating);
        }

        assertEquals(23_837_527_729L, solver.solve(ratings).getArrangementCount());
    }

    private void assertResult(List<Long> ratings, long partOne, long partTwo) {
        AdapterSolver.Result result = solver.solve(ratings);
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
