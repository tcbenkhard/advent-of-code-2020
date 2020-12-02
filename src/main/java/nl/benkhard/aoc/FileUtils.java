package nl.benkhard.aoc;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileUtils {

    public static List<Integer> readFileAsListOfIntegers(ClassLoader  loader, String filename) {
        Scanner scanner = new Scanner(loader.getResourceAsStream(filename));
        List<Integer> inputNumbers = new ArrayList<Integer>();
        while(scanner.hasNext()) {
            inputNumbers.add(scanner.nextInt());
        }
        return inputNumbers;
    }

    public static List<String> readFileAsListOfStrings(ClassLoader loader, String filename) {
        Scanner scanner = new Scanner(loader.getResourceAsStream(filename));
        List<String> inputStrings = new ArrayList();
        while(scanner.hasNext()) {
            inputStrings.add(scanner.nextLine());
        }
        return inputStrings;
    }
}
