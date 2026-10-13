package nl.benkhard.aoc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class AdapterInput {
    private AdapterInput() {
    }

    public static List<Long> parse(InputStream input, String sourceName) throws IOException {
        if (input == null) {
            throw new IllegalArgumentException("Resource not found: " + sourceName);
        }

        List<Long> ratings = new ArrayList<>();
        Set<Long> seenRatings = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(input, StandardCharsets.UTF_8))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                long rating = parseRating(line, sourceName, lineNumber);
                if (!seenRatings.add(rating)) {
                    throw new IllegalArgumentException(
                            "Duplicate adapter rating in " + sourceName + " at line "
                                    + lineNumber + ": " + rating);
                }
                ratings.add(rating);
            }
        }

        if (ratings.isEmpty()) {
            throw new IllegalArgumentException("No adapter ratings found in " + sourceName);
        }
        return ratings;
    }

    private static long parseRating(String line, String sourceName, int lineNumber) {
        String value = line.trim();
        if (value.isEmpty()) {
            throw new IllegalArgumentException(
                    "Blank line in " + sourceName + " at line " + lineNumber);
        }

        final long rating;
        try {
            rating = Long.parseLong(value);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(
                    "Invalid integer in " + sourceName + " at line " + lineNumber
                            + ": " + value,
                    exception);
        }
        if (rating <= 0) {
            throw new IllegalArgumentException(
                    "Adapter rating must be positive in " + sourceName + " at line "
                            + lineNumber + ": " + rating);
        }
        return rating;
    }
}
