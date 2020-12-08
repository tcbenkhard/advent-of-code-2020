package nl.benkhard.aoc;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Main {

    static Function<String, Password> lineToPassword = line -> {
        String[] splitPassword = line.split("-|: | ");
        return new Password(
                Integer.parseInt(splitPassword[0]),
                Integer.parseInt(splitPassword[1]),
                splitPassword[2].charAt(0),
                splitPassword[3]);
    };

    static Predicate<Password> isValidPassword1 = password -> {
        long count = password.password.chars()
                .map(i -> (char) i)
                .filter(c -> c == password.c)
                .count();

        return count >= password.a && count <= password.b;
    };

    static Predicate<Password> isValidPassword2 = password -> {
        return password.password.charAt(password.a-1) == password.c ^ password.password.charAt(password.b-1) == password.c;
    };

    public static void main(String[] args) {
        List<String> lines = FileUtils.readFileAsListOfStrings("input.txt");

        long validPasswords1 = lines.stream()
                .map(lineToPassword)
                .filter(isValidPassword1)
                .count();

        long validPasswords2 = lines.stream()
                .map(lineToPassword)
                .filter(isValidPassword2)
                .count();

        System.out.println(String.format("PART 1: %s valid passwords", validPasswords1));
        System.out.println(String.format("PART 2: %s valid passwords", validPasswords2));
    }
}
