package nl.benkhard.aoc;

import java.util.*;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        List<String> lines = FileUtils.readFileAsListOfStrings("input.txt");
        List<Map<String, String>> passports = parsePassports(lines);

        int validCount = 0;

        for(Map<String, String> passport : passports) {
            try {
                validateYear(passport,"byr", 1920, 2002);
                validateYear(passport,"iyr", 2010, 2020);
                validateYear(passport,"eyr", 2020, 2030);
                validateHeight(passport);
                validateHcl(passport);
                validateEcl(passport);
                validatePid(passport);

                System.out.printf("VALID: %s\n", passport);
                validCount++;
            } catch (Exception e) {
                System.out.printf("INVALID: %s\n", passport);
            }
        }
        System.out.printf("Valid passports: %d", validCount);
    }

    public static void validatePid(Map<String, String> passport) throws Exception {
        String pid = passport.get("pid");
        Pattern pattern = Pattern.compile("0*[0-9]{9}");
        if(!pattern.matcher(pid).find()) throw new Exception();
    }

    public static void validateEcl(Map<String, String> passport) throws Exception {
        List<String> colors = Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth");
        if(!colors.contains(passport.get("ecl"))) {
            System.out.printf("Invalid ECL: %s\n", passport.get("ecl"));
            throw new Exception();
        }
    }

    public static void validateHcl(Map<String, String> passport) throws Exception {
        String hcl = passport.get("hcl");
        Pattern pattern = Pattern.compile("#[0-9a-f]{6}");
        if(!pattern.matcher(hcl).find()) {
            System.out.printf("Invalid HCL: %s\n", hcl);
            throw new Exception();
        }
    }

    public static void validateHeight(Map<String, String> passport) throws Exception {
        String height = passport.get("hgt");
        if(height.endsWith("cm")){
            int heightValue = Integer.parseInt(height.substring(0, height.indexOf("cm")));
            if(heightValue < 150 || heightValue > 193){
                System.out.printf("Invalid Height: %s\n", height);
                throw new Exception();
            }
        } else if (height.endsWith("in")){
            int heightValue = Integer.parseInt(height.substring(0, height.indexOf("in")));
            if(heightValue < 59 || heightValue > 76) {
                System.out.printf("Invalid Height: %s\n", height);
                throw new Exception();
            }
        } else {
            throw new Exception();
        }
    }

    public static void validateYear(Map<String, String> passport, String field, int min, int max) throws Exception {
        int val = Integer.parseInt(passport.get(field));
        if(val < min || val > max) {
            System.out.printf("Invalid Year: %s\n", val);
            throw new Exception();
        }
    }

    public static List<Map<String, String>> parsePassports(List<String> lines) {
        List<Map<String, String>> passports = new ArrayList<>();
        String passportLine = "";
        for(String line : lines) {
            if(line.equals("\n\r") || line.equals("\r")|| line.equals("")) {
                passports.add(lineToMap(passportLine));
                passportLine = "";
            } else {
                passportLine += " "+line.trim();
            }
        }
        passports.add(lineToMap(passportLine));
        return passports;
    }

    private static Map<String, String> lineToMap(String line) {
        String[] passportAttributes = line.trim().split(" ");
        Map<String, String> passportMap = new HashMap<>();
        for(String kv : passportAttributes) {
            String[] keyValue = kv.split(":");
            passportMap.put(keyValue[0], keyValue[1]);
        }
        return passportMap;
    }

}
