package nl.benkhard.aoc.instruction;

import nl.benkhard.aoc.State;

public class NoOpInstruction extends Instruction {
    public NoOpInstruction(Integer param) {
        super(param);
    }

    @Override
    protected void modify(State state) {
        state.jmp(1);
    }
}
