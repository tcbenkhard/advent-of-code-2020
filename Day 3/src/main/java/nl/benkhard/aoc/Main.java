package nl.benkhard.aoc;

import java.math.BigInteger;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        BigInteger total = BigInteger.valueOf(slope(1, 1));
        total = total.multiply(BigInteger.valueOf(slope(3, 1)));
        total = total.multiply(BigInteger.valueOf(slope(5, 1)));
        total = total.multiply(BigInteger.valueOf(slope(7, 1)));
        total = total.multiply(BigInteger.valueOf(slope(1, 2)));

        System.out.println(total);
    }

    private static int slope(int offsetX, int offsetY) {
        List<String> lines = FileUtils.readFileAsListOfStrings(Main.class.getClassLoader(), "input.txt");
        int charsInRow = lines.get(0).length();
        int treecount = 0;
        int posX = 0;
        int posY = 0;

        if(lines.get(posY).charAt(posX) == '#') treecount++;
        while(posY+offsetY < lines.size()) {
            posX = (posX + offsetX) % charsInRow;
            posY+= offsetY;
            if(lines.get(posY).charAt(posX) == '#')
                treecount++;
        }

        return treecount;
    }
}