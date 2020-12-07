package nl.benkhard.aoc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rule {
    private String color;
    private Map<String, Integer> contains;

    private Rule(String ruleString) {
        contains = new HashMap<>();
        String[] ruleArray = ruleString.split(" contain ");
        this.color = ruleArray[0].replace("bags", "").replace("bag", "").trim();
        String[] containsArray = ruleArray[1].replace(".", "").split(", ");
        for(String containsColor: containsArray) {
            if(containsColor.startsWith("no "))
                continue;

            String[] containsColorArray = containsColor.split(" ", 2);
            contains.put(containsColorArray[1].replace(" bags", "").replace(" bag", "")
                    , Integer.valueOf(containsColorArray[0]));
        }
    }

    public static List<Rule> from (List<String> rulesStrings) {
        List<Rule> rules = new ArrayList<>();
        for(String rule : rulesStrings) {
            rules.add(new Rule(rule));
        }

        return rules;
    }

    public boolean canContain(String color) {
        for(String key : contains.keySet()) {
            if(key.contains(color))
                return true;
        }

        return false;
    }

    public String getColor() {
        return color;
    }

    public Map<String, Integer> getContainers() {
        return contains;
    }

    public int getContainerCount() {
        int count = 0;
        for(String color : contains.keySet()) {
            count += contains.get(color);
        }
        return count;
    }

    public int getRecursiveCount(List<Rule> rules) {
        int count = 0;
        for(String color : contains.keySet()) {
            int amountOfColor = contains.get(color);
            count += amountOfColor;
            Rule rule = getRuleByColor(color, rules);
            if(rule != null) {
                count += rule.getRecursiveCount(rules) * amountOfColor;
            }
        }

        return count;
    }

    public static Rule getRuleByColor(String targetColor, List<Rule> rules) {
        for(Rule rule : rules) {
            if(rule.getColor().contains(targetColor))
                return rule;
        }

        return null;
    }
}
