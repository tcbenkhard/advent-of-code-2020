package nl.benkhard.aoc;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class EncodingErrorInputTest {
    @Test
    public void parsesLongsAndPreservesOrderAndDuplicates() throws IOException {
        assertEquals(Arrays.asList(3_000_000_000L, 4L, 4L),
                EncodingErrorInput.parse(input(" 3000000000 \n4\n4\n"), "numbers.txt"));
    }

    @Test
    public void reportsMissingResource() throws IOException {
        try {
            EncodingErrorInput.parse(null, "missing.txt");
            fail("Expected missing resource failure");
        } catch (IllegalArgumentException exception) {
            assertTrue(exception.getMessage().contains("missing.txt"));
        }
    }

    @Test
    public void reportsBlankLineWithLineNumber() throws IOException {
        assertBadInput("1\n \n2", "line 2");
    }

    @Test
    public void reportsMalformedAndOutOfRangeValuesWithLineNumber() throws IOException {
        assertBadInput("1\nnot-a-number", "line 2");
        assertBadInput("9223372036854775808", "line 1");
    }

    private static void assertBadInput(String text, String expectedMessage) throws IOException {
        try {
            EncodingErrorInput.parse(input(text), "numbers.txt");
            fail("Expected malformed input failure");
        } catch (IllegalArgumentException exception) {
            assertTrue(exception.getMessage().contains(expectedMessage));
        }
    }

    private static ByteArrayInputStream input(String text) {
        return new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8));
    }
}
