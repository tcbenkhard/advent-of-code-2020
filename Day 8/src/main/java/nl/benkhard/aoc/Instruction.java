package nl.benkhard.aoc;

abstract class Instruction {
    private int param;
    private boolean executed = false;

    Instruction(Integer param) {
        this.param = param;
    }

    protected abstract void modify(State state);

    public void execute(State state) {
        System.out.printf("Executing index: %d\n", state.getIndex());
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
}
