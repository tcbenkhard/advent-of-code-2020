package nl.benkhard.aoc;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;

public class Main {
    private static final String DEFAULT_RESOURCE = "input.txt";

    public static void main(String[] args) {
        int status = run(args, System.out, System.err);
        if (status != 0) {
            System.exit(status);
        }
    }

    static int run(String[] args, PrintStream output, PrintStream error) {
        if (args.length > 1) {
            error.println("Error: expected at most one argument: [resource]");
            return 1;
        }

        String resource = args.length == 1 ? args[0] : DEFAULT_RESOURCE;
        try {
            InputStream stream = Main.class.getClassLoader().getResourceAsStream(resource);
            List<Long> ratings = AdapterInput.parse(stream, resource);
            AdapterArraySolver.Result result = new AdapterArraySolver().solve(ratings);
            output.println("Part 1: " + result.getDifferenceProduct());
            output.println("Part 2: " + result.getArrangementCount());
            return 0;
        } catch (IOException exception) {
            error.println("Error: could not read " + resource + ": " + exception.getMessage());
            return 1;
        } catch (IllegalArgumentException | IllegalStateException exception) {
            error.println("Error: " + exception.getMessage());
            return 1;
        } catch (ArithmeticException exception) {
            error.println("Error: arithmetic overflow while solving " + resource);
            return 1;
        }
    }
}
