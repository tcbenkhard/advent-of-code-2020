package nl.benkhard.aoc.instruction;

import nl.benkhard.aoc.State;

import java.util.function.Function;

abstract public class Instruction {
    private int param;
    private boolean executed = false;

    Instruction(Integer param) {
        this.param = param;
    }

    protected abstract void modify(State state);

    public void execute(State state) {
        if(!executed) {
            modify(state);
            executed = true;
        } else {
            throw new InfiniteLoopException();
        }
    }

    public int getParam() {
        return this.param;
    }

    public <T extends Instruction> Instruction to(Class<T> targetType) {
        try {
            return targetType.getConstructor(Integer.class).newInstance(getParam());
        } catch (Exception e) {
            throw new RuntimeException("Failed to change instruction");
        }
    }

    static public Function<String, Instruction> lineToInstruction = line -> {
        String[] instructionArray = line.split(" ");
        switch (instructionArray[0]) {
            case "acc":
                return new AccumulatorInstruction(Integer.parseInt(instructionArray[1]));
            case "jmp":
                return new JumpInstruction(Integer.parseInt(instructionArray[1]));
            case "nop":
                return new NoOpInstruction(Integer.parseInt(instructionArray[1]));
        }
        throw new RuntimeException(String.format("Invalid command '%s'", instructionArray[0]));
    };
}
