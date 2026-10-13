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
    public void printsExactLabeledAnswersForExplicitResource() throws Exception {
        RunResult result = run("small-sample.txt");

        assertEquals(0, result.status);
        assertEquals("Part 1: 35" + System.lineSeparator()
                + "Part 2: 8" + System.lineSeparator(), result.output);
        assertEquals("", result.error);
    }

    @Test
    public void usesBundledInputByDefault() throws Exception {
        RunResult result = run();

        assertEquals(0, result.status);
        assertEquals("Part 1: 220" + System.lineSeparator()
                + "Part 2: 19208" + System.lineSeparator(), result.output);
        assertEquals("", result.error);
    }

    @Test
    public void rejectsUnsupportedArgumentCountWithoutAnswers() throws Exception {
        assertFailure(run("small-sample.txt", "extra"), "at most one argument");
    }

    @Test
    public void reportsMissingAndMalformedResourcesWithoutAnswers() throws Exception {
        assertFailure(run("missing.txt"), "Resource not found: missing.txt");
        assertFailure(run("malformed.txt"), "line 2");
    }

    @Test
    public void reportsInvalidChainWithoutAnswers() throws Exception {
        assertFailure(run("invalid-gap.txt"), "cannot connect");
    }

    @Test
    public void reportsArithmeticOverflowWithoutAnswersOrStackTrace() throws Exception {
        RunResult result = run("device-overflow.txt");

        assertFailure(result, "arithmetic overflow while solving device-overflow.txt");
        assertFalse(result.error.contains("Exception"));
        assertFalse(result.error.contains("\tat "));
    }

    private static void assertFailure(RunResult result, String expectedError) {
        assertEquals(1, result.status);
        assertEquals("", result.output);
        assertTrue(result.error, result.error.contains(expectedError));
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
