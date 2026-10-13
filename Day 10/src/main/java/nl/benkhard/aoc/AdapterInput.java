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
                String value = line.trim();
                if (value.isEmpty()) {
                    throw invalidInput(sourceName, lineNumber, "blank line");
                }

                final long rating;
                try {
                    rating = Long.parseLong(value);
                } catch (NumberFormatException exception) {
                    throw invalidInput(sourceName, lineNumber, "invalid adapter rating", exception);
                }

                if (rating <= 0) {
                    throw invalidInput(sourceName, lineNumber,
                            "adapter rating must be positive");
                }
                if (!seenRatings.add(rating)) {
                    throw invalidInput(sourceName, lineNumber,
                            "duplicate adapter rating " + rating);
                }
                ratings.add(rating);
            }
        }

        if (ratings.isEmpty()) {
            throw new IllegalArgumentException("No adapter ratings in " + sourceName);
        }
        return ratings;
    }

    private static IllegalArgumentException invalidInput(
            String sourceName, int lineNumber, String reason) {
        return new IllegalArgumentException(
                reason + " in " + sourceName + " at line " + lineNumber);
    }

    private static IllegalArgumentException invalidInput(
            String sourceName, int lineNumber, String reason, Exception cause) {
        return new IllegalArgumentException(
                reason + " in " + sourceName + " at line " + lineNumber, cause);
    }
}
