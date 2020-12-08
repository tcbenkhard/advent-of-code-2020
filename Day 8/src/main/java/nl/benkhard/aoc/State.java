package nl.benkhard.aoc;

public class State {
    private int index = 0;
    private int accumulator = 0;

    public State acc(int inc) {
        accumulator += inc;
        return jmp(1);
    }

    public State jmp(int by) {
        index += by;
        return this;
    }

    public int getIndex() {
        return index;
    }

    public int getAccumulator() {
        return accumulator;
    }
}
