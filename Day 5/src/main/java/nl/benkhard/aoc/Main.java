package nl.benkhard.aoc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {


    public static void main(String[] args) {
        List<String> lines = FileUtils.readFileAsListOfStrings("input.txt");
        int largest = 0;
        List<Integer> allSeats = new ArrayList<>();
        for (String line : lines) {
            try {
                int code = getSeatId(line);
                if (code > largest) largest = code;
                allSeats.add(code);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Collections.sort(allSeats);
        System.out.printf("The highest code is %d\n", largest);
        int expected = 0;
        int seat;
        for(int i = 0; i < allSeats.size(); i++) {
            int actual = allSeats.get(i);
            if(expected != 0) {
                if(actual != expected && actual == expected+1) {
                    System.out.printf("Your seat is %d\n", expected);
                    return;
                }
            }
            expected = allSeats.get(i)+1;
        }
    }

    private static int getSeatId(String seatCode) throws Exception {
        SeatRange range = new SeatRange();
        for (char c : seatCode.toCharArray()) {
            range.cut(c);
        }

        return range.getCode();
    }
}

class SeatRange {
    private int rowMin;
    private int rowMax;
    private int colMin;
    private int colMax;

    public SeatRange() {
        this.rowMin = 0;
        this.rowMax = 128;
        this.colMin = 0;
        this.colMax = 8;
    }

    public void cut(char c) throws Exception {
        switch (c) {
            case 'F':
                 rowMax -= (rowMax - rowMin) / 2;
                break;
            case 'B':
                rowMin += (rowMax - rowMin) / 2;
                break;
            case 'L':
                colMax -= (colMax - colMin) /2;
                break;
            case 'R':
                colMin += (colMax - colMin) / 2;
                break;
            default:
                throw new Exception();
        }
    }

    public int getCode() throws Exception {
        if (rowMin+1 == rowMax && colMin+1 == colMax) {
            return rowMin * 8 + colMin;
        }
        throw new Exception();
    }
}
