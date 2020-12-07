package nl.benkhard.aoc;

import java.util.*;

public class Main {
    private final static String STARTING_COLOR = "shiny gold";

    public static void main(String[] args) {
        List<String> lines = FileUtils.readFileAsListOfStrings("input.txt");
        List<Rule> rules = Rule.from(lines);

        partOne(rules);
        partTwo(rules);
    }

    private static void partTwo(List<Rule> rules) {
        Rule startingRule = Rule.getRuleByColor(STARTING_COLOR, rules);
        int containingCount = startingRule.getRecursiveCount(rules);
        System.out.printf("%s should contain %d bags\n", STARTING_COLOR, containingCount);
    }

    private static void partOne(List<Rule> rules) {
        Set<Rule> validContainers = new HashSet<>();
        Set<Rule> loopContainers = getBagsThatCanContain(STARTING_COLOR, rules);
        validContainers.addAll(loopContainers);

        while(loopContainers.size() != 0) {
            Set<Rule> collectedRules = new HashSet<>();
            for(Rule rule : loopContainers) {
                collectedRules.addAll(getBagsThatCanContain(rule.getColor(), rules));
            }
            loopContainers = collectedRules;
            validContainers.addAll(collectedRules);
        }

        System.out.printf("%d bags can contain %s", validContainers.size(), STARTING_COLOR);
    }

    private static Set<Rule> getBagsThatCanContain(String targetColor, List<Rule> rules) {
        Set<Rule> validContainers = new HashSet<>();
        for(Rule rule : rules) {
            if(rule.canContain(targetColor))
                validContainers.add(rule);
        }

        return validContainers;
    }
}
