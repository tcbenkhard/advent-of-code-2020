package nl.benkhard.aoc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class AdapterSolver {
    public Result solve(List<Long> ratings) {
        List<Long> chain = buildValidatedChain(ratings);
        long oneJoltDifferences = 0;
        long threeJoltDifferences = 0;
        Map<Long, Long> arrangementsByRating = new HashMap<>();
        arrangementsByRating.put(0L, 1L);

        for (int index = 1; index < chain.size(); index++) {
            long rating = chain.get(index);
            long difference = rating - chain.get(index - 1);
            if (difference == 1) {
                oneJoltDifferences = Math.incrementExact(oneJoltDifferences);
            } else if (difference == 3) {
                threeJoltDifferences = Math.incrementExact(threeJoltDifferences);
            }

            long arrangements = 0;
            for (long offset = 1; offset <= 3; offset++) {
                Long previousArrangements = arrangementsByRating.get(rating - offset);
                if (previousArrangements != null) {
                    arrangements = Math.addExact(arrangements, previousArrangements);
                }
            }
            arrangementsByRating.put(rating, arrangements);
        }

        long differenceProduct = multiplyDifferenceCounts(
                oneJoltDifferences, threeJoltDifferences);
        long deviceRating = chain.get(chain.size() - 1);
        return new Result(differenceProduct, arrangementsByRating.get(deviceRating));
    }

    static long multiplyDifferenceCounts(long oneJoltDifferences, long threeJoltDifferences) {
        return Math.multiplyExact(oneJoltDifferences, threeJoltDifferences);
    }

    private List<Long> buildValidatedChain(List<Long> ratings) {
        if (ratings == null) {
            throw new IllegalArgumentException("Adapter ratings must not be null");
        }
        if (ratings.isEmpty()) {
            throw new IllegalArgumentException("Adapter ratings must not be empty");
        }

        List<Long> sortedRatings = new ArrayList<>(ratings.size());
        for (int index = 0; index < ratings.size(); index++) {
            Long rating = ratings.get(index);
            if (rating == null) {
                throw new IllegalArgumentException("Adapter rating at index " + index + " is null");
            }
            if (rating <= 0) {
                throw new IllegalArgumentException("Adapter rating must be positive: " + rating);
            }
            sortedRatings.add(rating);
        }
        Collections.sort(sortedRatings);
        long deviceRating = Math.addExact(sortedRatings.get(sortedRatings.size() - 1), 3L);

        List<Long> chain = new ArrayList<>(sortedRatings.size() + 2);
        chain.add(0L);
        long previous = 0;
        for (long rating : sortedRatings) {
            if (rating == previous) {
                throw new IllegalArgumentException("Duplicate adapter rating: " + rating);
            }
            validateGap(previous, rating);
            chain.add(rating);
            previous = rating;
        }

        chain.add(deviceRating);
        return chain;
    }

    private void validateGap(long lowerRating, long higherRating) {
        long difference = higherRating - lowerRating;
        if (difference < 1 || difference > 3) {
            throw new IllegalArgumentException(
                    "Adapter chain cannot connect " + lowerRating + " to " + higherRating
                            + " (gap " + difference + ")");
        }
    }

    public static final class Result {
        private final long differenceProduct;
        private final long arrangementCount;

        private Result(long differenceProduct, long arrangementCount) {
            this.differenceProduct = differenceProduct;
            this.arrangementCount = arrangementCount;
        }

        public long getDifferenceProduct() {
            return differenceProduct;
        }

        public long getArrangementCount() {
            return arrangementCount;
        }
    }
}
