package nl.benkhard.aoc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public final class EncodingErrorInput {
    private EncodingErrorInput() {
    }

    public static List<Long> parse(InputStream input, String sourceName) throws IOException {
        if (input == null) {
            throw new IllegalArgumentException("Resource not found: " + sourceName);
        }

        List<Long> numbers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(input, StandardCharsets.UTF_8))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                String value = line.trim();
                if (value.isEmpty()) {
                    throw new IllegalArgumentException(
                            "Blank line in " + sourceName + " at line " + lineNumber);
                }
                try {
                    numbers.add(Long.parseLong(value));
                } catch (NumberFormatException exception) {
                    throw new IllegalArgumentException(
                            "Invalid integer in " + sourceName + " at line " + lineNumber
                                    + ": " + value,
                            exception);
                }
            }
        }
        return numbers;
    }
}
