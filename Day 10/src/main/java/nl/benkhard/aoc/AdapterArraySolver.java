package nl.benkhard.aoc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class AdapterArraySolver {
    public Result solve(List<Long> ratings) {
        List<Long> sortedRatings = validateAndSort(ratings);
        long deviceRating = Math.addExact(sortedRatings.get(sortedRatings.size() - 1), 3L);

        long oneJoltDifferences = 0;
        long threeJoltDifferences = 0;
        long previousRating = 0;
        for (long rating : sortedRatings) {
            long difference = rating - previousRating;
            validateDifference(previousRating, rating, difference);
            if (difference == 1) {
                oneJoltDifferences++;
            } else if (difference == 3) {
                threeJoltDifferences++;
            }
            previousRating = rating;
        }

        // The device is always three jolts above the highest adapter.
        threeJoltDifferences++;
        long differenceProduct = Math.multiplyExact(oneJoltDifferences, threeJoltDifferences);
        long arrangementCount = countArrangements(sortedRatings, deviceRating);
        return new Result(differenceProduct, arrangementCount);
    }

    private List<Long> validateAndSort(List<Long> ratings) {
        if (ratings == null || ratings.isEmpty()) {
            throw new IllegalArgumentException("At least one adapter rating is required");
        }

        List<Long> sortedRatings = new ArrayList<>(ratings.size());
        Set<Long> seenRatings = new HashSet<>();
        for (int index = 0; index < ratings.size(); index++) {
            Long rating = ratings.get(index);
            if (rating == null) {
                throw new IllegalArgumentException("Adapter rating at index " + index + " is null");
            }
            if (rating <= 0) {
                throw new IllegalArgumentException("Adapter ratings must be positive");
            }
            if (!seenRatings.add(rating)) {
                throw new IllegalArgumentException("Duplicate adapter rating: " + rating);
            }
            sortedRatings.add(rating);
        }
        Collections.sort(sortedRatings);
        return sortedRatings;
    }

    private void validateDifference(long lower, long upper, long difference) {
        if (difference < 1 || difference > 3) {
            throw new IllegalStateException(
                    "No valid complete chain: gap from " + lower + " to " + upper
                            + " is " + difference + " jolts");
        }
    }

    private long countArrangements(List<Long> sortedRatings, long deviceRating) {
        Map<Long, Long> pathsByRating = new HashMap<>();
        pathsByRating.put(0L, 1L);

        for (long rating : sortedRatings) {
            pathsByRating.put(rating, pathsTo(rating, pathsByRating));
        }
        return pathsTo(deviceRating, pathsByRating);
    }

    private long pathsTo(long rating, Map<Long, Long> pathsByRating) {
        long paths = 0;
        for (long difference = 1; difference <= 3; difference++) {
            Long precedingPaths = pathsByRating.get(rating - difference);
            if (precedingPaths != null) {
                paths = Math.addExact(paths, precedingPaths);
            }
        }
        return paths;
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
