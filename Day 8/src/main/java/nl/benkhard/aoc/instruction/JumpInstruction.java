package nl.benkhard.aoc.instruction;

import nl.benkhard.aoc.State;

public class JumpInstruction extends Instruction {
    public JumpInstruction(Integer param) {
        super(param);
    }

    @Override
    protected void modify(State state) {
        state.jmp(getParam());
    }
}
