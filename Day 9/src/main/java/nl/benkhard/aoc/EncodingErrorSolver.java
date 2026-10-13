package nl.benkhard.aoc;

import java.util.List;

public final class EncodingErrorSolver {
    public InvalidNumber findFirstInvalid(List<Long> numbers, int preambleLength) {
        validateInput(numbers, preambleLength);

        for (int candidateIndex = preambleLength; candidateIndex < numbers.size(); candidateIndex++) {
            long candidate = numbers.get(candidateIndex);
            if (!hasPair(numbers, candidateIndex - preambleLength, candidateIndex, candidate)) {
                return new InvalidNumber(candidateIndex, candidate);
            }
        }
        throw new IllegalStateException("No invalid number was found");
    }

    public Range findContiguousRange(List<Long> numbers, int endExclusive, long target) {
        if (numbers == null) {
            throw new IllegalArgumentException("Numbers must not be null");
        }
        if (endExclusive < 0 || endExclusive > numbers.size()) {
            throw new IllegalArgumentException("Range search boundary is outside the input");
        }

        for (int start = 0; start < endExclusive - 1; start++) {
            long sum = numbers.get(start);
            for (int end = start + 1; end < endExclusive; end++) {
                sum = Math.addExact(sum, numbers.get(end));
                if (sum == target) {
                    return new Range(start, end);
                }
            }
        }
        throw new IllegalStateException("No contiguous range was found for " + target);
    }

    public long calculateWeakness(List<Long> numbers, Range range) {
        if (numbers == null || range == null) {
            throw new IllegalArgumentException("Numbers and range must not be null");
        }
        if (range.startIndex < 0 || range.endIndex >= numbers.size()
                || range.startIndex >= range.endIndex) {
            throw new IllegalArgumentException("Range must contain at least two input entries");
        }

        long minimum = numbers.get(range.startIndex);
        long maximum = minimum;
        for (int index = range.startIndex + 1; index <= range.endIndex; index++) {
            long value = numbers.get(index);
            minimum = Math.min(minimum, value);
            maximum = Math.max(maximum, value);
        }
        return Math.addExact(minimum, maximum);
    }

    private boolean hasPair(List<Long> numbers, int start, int endExclusive, long candidate) {
        for (int first = start; first < endExclusive - 1; first++) {
            for (int second = first + 1; second < endExclusive; second++) {
                if (Math.addExact(numbers.get(first), numbers.get(second)) == candidate) {
                    return true;
                }
            }
        }
        return false;
    }

    private void validateInput(List<Long> numbers, int preambleLength) {
        if (numbers == null) {
            throw new IllegalArgumentException("Numbers must not be null");
        }
        if (preambleLength < 2) {
            throw new IllegalArgumentException("Preamble length must be at least 2");
        }
        if (numbers.size() <= preambleLength) {
            throw new IllegalArgumentException(
                    "Input must contain a preamble and at least one candidate");
        }
        for (int index = 0; index < numbers.size(); index++) {
            if (numbers.get(index) == null) {
                throw new IllegalArgumentException("Number at index " + index + " is null");
            }
        }
    }

    public static final class InvalidNumber {
        private final int index;
        private final long value;

        public InvalidNumber(int index, long value) {
            this.index = index;
            this.value = value;
        }

        public int getIndex() {
            return index;
        }

        public long getValue() {
            return value;
        }
    }

    public static final class Range {
        private final int startIndex;
        private final int endIndex;

        public Range(int startIndex, int endIndex) {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }

        public int getStartIndex() {
            return startIndex;
        }

        public int getEndIndex() {
            return endIndex;
        }
    }
}
