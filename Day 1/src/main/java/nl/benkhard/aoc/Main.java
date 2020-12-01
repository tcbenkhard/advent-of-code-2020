package nl.benkhard.aoc;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private final static int TARGET_VALUE = 2020;

    public static void main(String[] args) throws FileNotFoundException {
        List<Integer> input = readInputFile();
        System.out.println(findTargetValue(input, TARGET_VALUE));
    }

    private static int findTargetValue(List<Integer> values, int target) {
        for(int firstIndex = 0; firstIndex <= values.size()-3; firstIndex++) {
            int firstValue = values.get(firstIndex);
            for(int secondIndex = 1; firstIndex + secondIndex <= values.size()-2; secondIndex++) {
                int secondValue = values.get(firstIndex + secondIndex);
                if(firstValue + secondValue < TARGET_VALUE) {
                    for(int thirdIndex = 1; firstIndex + secondIndex + thirdIndex <= values.size()-1; thirdIndex++) {
                        int thirdValue = values.get(firstIndex + secondIndex + thirdIndex);
                        int total = firstValue + secondValue + thirdValue;
                        if(total == TARGET_VALUE)
                            return firstValue * secondValue * thirdValue;
                    }
                }
            }
        }
        throw new RuntimeException("List does not contain three values that add together");
    }

    private static List<Integer> readInputFile() throws FileNotFoundException {

        Scanner scanner = new Scanner(Main.class.getClassLoader().getResourceAsStream("input.txt"));
        List<Integer> inputNumbers = new ArrayList<Integer>();
        while(scanner.hasNext()) {
            inputNumbers.add(scanner.nextInt());
        }
        return inputNumbers;
    }
}
