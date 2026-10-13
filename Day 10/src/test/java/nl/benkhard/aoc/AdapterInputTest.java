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
    public void parsesPositiveLongsInInputOrderAndTrimsWhitespace() throws IOException {
        assertEquals(Arrays.asList(3_000_000_000L, 4L, 9L),
                AdapterInput.parse(input(" 3000000000 \n\t4\t\n9\n"), "adapters.txt"));
    }

    @Test
    public void reportsMissingAndEmptyResources() throws IOException {
        assertBadInput(null, "missing.txt", "Resource not found: missing.txt");
        assertBadInput(input(""), "empty.txt", "No adapter ratings in empty.txt");
    }

    @Test
    public void rejectsBlankLinesWithSourceAndLineNumber() throws IOException {
        assertBadInput(input("1\n \n2"), "adapters.txt", "adapters.txt at line 2");
    }

    @Test
    public void rejectsMalformedAndOutOfRangeRatingsWithLineNumber() throws IOException {
        assertBadInput(input("1\nnot-a-number"), "adapters.txt", "line 2");
        assertBadInput(input("9223372036854775808"), "adapters.txt", "line 1");
    }

    @Test
    public void rejectsNonPositiveAndDuplicateRatings() throws IOException {
        assertBadInput(input("0"), "zero.txt", "positive in zero.txt at line 1");
        assertBadInput(input("-2"), "negative.txt", "positive in negative.txt at line 1");
        assertBadInput(input("3\n5\n3"), "duplicate.txt",
                "duplicate adapter rating 3 in duplicate.txt at line 3");
    }

    private static void assertBadInput(
            ByteArrayInputStream input, String sourceName, String expectedMessage)
            throws IOException {
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
}
