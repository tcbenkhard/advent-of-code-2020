package nl.benkhard.aoc;

public class State {
    private int index = 0;
    private int accumulator = 0;

    public State acc(int inc) {
//        System.out.printf("Increment by %d (%d -> %d)\n", inc, accumulator, accumulator+inc);
        accumulator += inc;
        return jmp(1);
    }

    public State jmp(int by) {
//        System.out.printf("Jump %d from %d to %d\n", by, index, index + by);
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
