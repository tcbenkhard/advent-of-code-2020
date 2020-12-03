package nl.benkhard.aoc;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<String> lines = FileUtils.readFileAsListOfStrings("input.txt");
        int validPasswords = 0;
        for(String line : lines) {
            String[] splitLine = line.split(": ");
            if(isValid(splitLine[0], splitLine[1]))
                validPasswords++;
        }

        System.out.println(String.format("%s valid passwords", validPasswords));
    }

    public static boolean isValid(String policyString, String password) {
        String[] policy = policyString.split(" ");
        String[] minMax = policy[0].split("-");
        int min = Integer.valueOf(minMax[0]);
        int max = Integer.valueOf(minMax[1]);
        char character = policy[1].charAt(0);

        return password.charAt(min-1) == character ^ password.charAt(max-1) == character;
    }
}
