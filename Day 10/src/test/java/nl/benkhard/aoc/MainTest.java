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
    public void defaultResourcePrintsExactlyTwoLabeledAnswers() throws Exception {
        RunResult result = run();

        assertEquals(0, result.status);
        assertEquals(lines("Part 1: 35", "Part 2: 8"), result.output);
        assertEquals("", result.error);
    }

    @Test
    public void explicitResourcesPrintCanonicalAnswers() throws Exception {
        RunResult small = run("small-example.txt");
        RunResult large = run("large-example.txt");

        assertEquals(0, small.status);
        assertEquals(lines("Part 1: 35", "Part 2: 8"), small.output);
        assertEquals(0, large.status);
        assertEquals(lines("Part 1: 220", "Part 2: 19208"), large.output);
    }

    @Test
    public void tooManyArgumentsReturnFailureWithoutAnswers() throws Exception {
        assertFailure(run("small-example.txt", "extra"), "expected at most one argument");
    }

    @Test
    public void missingAndInvalidResourcesReturnControlledFailures() throws Exception {
        assertFailure(run("missing.txt"), "Resource not found: missing.txt");
        assertFailure(run("invalid.txt"), "invalid adapter rating in invalid.txt at line 2");
    }

    @Test
    public void impossibleChainReturnsFailureWithoutAnswers() throws Exception {
        assertFailure(run("impossible-chain.txt"), "No valid complete chain");
    }

    @Test
    public void overflowReturnsFailureWithoutAnswers() throws Exception {
        assertFailure(run("device-overflow.txt"),
                "arithmetic overflow while solving device-overflow.txt");
    }

    private static void assertFailure(RunResult result, String expectedError) {
        assertEquals(1, result.status);
        assertEquals("", result.output);
        assertTrue(result.error, result.error.contains(expectedError));
        assertTrue(result.error.startsWith("Error: "));
        assertFalse(result.error.contains("Exception"));
        assertFalse(result.error.contains("\tat "));
    }

    private static String lines(String... lines) {
        return String.join(System.lineSeparator(), lines) + System.lineSeparator();
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
