package nl.benkhard.aoc.instruction;

import nl.benkhard.aoc.State;

public class AccumulatorInstruction extends Instruction {
    public AccumulatorInstruction(Integer param) {
        super(param);
    }

    @Override
    protected void modify(State state) {
        state.acc(getParam());
    }
}
