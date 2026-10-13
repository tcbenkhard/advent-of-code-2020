package nl.benkhard.aoc;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class AdapterInputTest {
    @Test
    public void parsesPositiveLongsAndPreservesOrder() throws IOException {
        assertEquals(Arrays.asList(3_000_000_000L, 4L, 2L),
                AdapterInput.parse(input(" 3000000000 \n4\n2\n"), "ratings.txt"));
    }

    @Test
    public void closesInputStream() throws IOException {
        CloseTrackingInputStream input = new CloseTrackingInputStream("1\n");

        AdapterInput.parse(input, "ratings.txt");

        assertTrue(input.closed);
    }

    @Test
    public void rejectsMissingAndEmptyResources() throws IOException {
        assertBadInput(null, "Resource not found: missing.txt", "missing.txt");
        assertBadInput(input(""), "No adapter ratings", "empty.txt");
    }

    @Test
    public void rejectsBlankMalformedAndOutOfRangeLines() throws IOException {
        assertBadInput(input("1\n \n2"), "line 2", "ratings.txt");
        assertBadInput(input("1\nnot-a-number"), "line 2", "ratings.txt");
        assertBadInput(input("9223372036854775808"), "line 1", "ratings.txt");
    }

    @Test
    public void rejectsNonPositiveAndDuplicateRatings() throws IOException {
        assertBadInput(input("0\n"), "positive", "ratings.txt");
        assertBadInput(input("-1\n"), "positive", "ratings.txt");
        assertBadInput(input("2\n1\n2\n"), "Duplicate adapter rating", "ratings.txt");
    }

    private static void assertBadInput(ByteArrayInputStream input, String expectedMessage,
                                       String sourceName) throws IOException {
        try {
            AdapterInput.parse(input, sourceName);
            fail("Expected invalid input failure");
        } catch (IllegalArgumentException exception) {
            assertTrue(exception.getMessage(), exception.getMessage().contains(expectedMessage));
        }
    }

    private static ByteArrayInputStream input(String text) {
        return new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8));
    }

    private static final class CloseTrackingInputStream extends ByteArrayInputStream {
        private boolean closed;

        private CloseTrackingInputStream(String text) {
            super(text.getBytes(StandardCharsets.UTF_8));
        }

        @Override
        public void close() throws IOException {
            closed = true;
            super.close();
        }
    }
}
