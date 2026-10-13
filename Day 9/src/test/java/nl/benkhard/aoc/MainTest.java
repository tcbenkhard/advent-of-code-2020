package nl.benkhard.aoc;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MainTest {
    @Test
    public void printsLabeledSampleAnswers() throws Exception {
        RunResult result = run("test.txt", "5");

        assertEquals(0, result.status);
        assertTrue(result.output.contains("Part 1: 127"));
        assertTrue(result.output.contains("Part 2: 62"));
        assertEquals("", result.error);
    }

    @Test
    public void runsBundledProductionInputWithDefaultPreamble() throws Exception {
        RunResult result = run();

        assertEquals(0, result.status);
        assertTrue(result.output.contains("Part 1: 100"));
        assertTrue(result.output.contains("Part 2: 25"));
    }

    @Test
    public void missingResourceReturnsFailureWithoutAnswers() throws Exception {
        RunResult result = run("missing.txt", "5");

        assertEquals(1, result.status);
        assertEquals("", result.output);
        assertTrue(result.error.contains("Resource not found: missing.txt"));
    }

    @Test
    public void invalidPreambleReturnsFailureWithoutAnswers() throws Exception {
        RunResult result = run("test.txt", "1");

        assertEquals(1, result.status);
        assertEquals("", result.output);
        assertTrue(result.error.contains("Preamble length must be at least 2"));
    }

    @Test
    public void missingRangeKeepsPartOneAndDoesNotFabricatePartTwo() throws Exception {
        RunResult result = run("no-range.txt", "2");

        assertEquals(1, result.status);
        assertTrue(result.output.contains("Part 1: 4"));
        assertFalse(result.output.contains("Part 2:"));
        assertTrue(result.error.contains("No contiguous range was found"));
    }

    private static RunResult run(String... args) throws Exception {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ByteArrayOutputStream error = new ByteArrayOutputStream();
        int status;
        try (PrintStream outputStream = new PrintStream(output, true, "UTF-8");
             PrintStream errorStream = new PrintStream(error, true, "UTF-8")) {
            status = Main.run(args, outputStream, errorStream);
        }
        return new RunResult(status,
                new String(output.toByteArray(), StandardCharsets.UTF_8),
                new String(error.toByteArray(), StandardCharsets.UTF_8));
    }

    private static final class RunResult {
        private final int status;
        private final String output;
        private final String error;

        private RunResult(int status, String output, String error) {
            this.status = status;
            this.output = output;
            this.error = error;
        }
    }
}
