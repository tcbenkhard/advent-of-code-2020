package nl.benkhard.aoc;

public class JumpInstruction extends Instruction {
    public JumpInstruction(Integer param) {
        super(param);
    }

    @Override
    protected void modify(State state) {
        state.jmp(getParam());
    }
}
