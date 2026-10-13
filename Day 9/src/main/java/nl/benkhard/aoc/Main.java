package nl.benkhard.aoc;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;

public class Main {
    private static final String DEFAULT_RESOURCE = "input.txt";
    private static final int DEFAULT_PREAMBLE_LENGTH = 25;

    public static void main(String[] args) {
        int status = run(args, System.out, System.err);
        if (status != 0) {
            System.exit(status);
        }
    }

    static int run(String[] args, PrintStream output, PrintStream error) {
        if (args.length > 2) {
            error.println("Error: expected at most two arguments: [resource] [preamble length]");
            return 1;
        }

        String resource = args.length >= 1 ? args[0] : DEFAULT_RESOURCE;
        final int preambleLength;
        try {
            preambleLength = args.length == 2
                    ? Integer.parseInt(args[1])
                    : DEFAULT_PREAMBLE_LENGTH;
        } catch (NumberFormatException exception) {
            error.println("Error: invalid preamble length: " + args[1]);
            return 1;
        }

        EncodingErrorSolver solver = new EncodingErrorSolver();
        try {
            InputStream stream = Main.class.getClassLoader().getResourceAsStream(resource);
            List<Long> numbers = EncodingErrorInput.parse(stream, resource);
            EncodingErrorSolver.InvalidNumber invalid =
                    solver.findFirstInvalid(numbers, preambleLength);
            output.println("Part 1: " + invalid.getValue());

            try {
                EncodingErrorSolver.Range range =
                        solver.findContiguousRange(numbers, invalid.getIndex(), invalid.getValue());
                output.println("Part 2: " + solver.calculateWeakness(numbers, range));
                return 0;
            } catch (IllegalStateException exception) {
                error.println("Error: " + exception.getMessage());
                return 1;
            }
        } catch (IOException | IllegalArgumentException | IllegalStateException exception) {
            error.println("Error: " + exception.getMessage());
            return 1;
        } catch (ArithmeticException exception) {
            error.println("Error: arithmetic overflow while solving " + resource);
            return 1;
        }
    }
}
