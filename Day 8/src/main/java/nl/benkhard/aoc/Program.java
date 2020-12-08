package nl.benkhard.aoc;

import nl.benkhard.aoc.instruction.InfiniteLoopException;
import nl.benkhard.aoc.instruction.Instruction;

import java.util.List;

public class Program {
    private final List<Instruction> code;


    public Program(List<Instruction> code) {
        this.code = code;
    }

    public int execute() {
        State state = new State();
        while(true) {
            if(state.getIndex() >= code.size()) {
                System.out.println("End of program reached, accumulator: "+state.getAccumulator());
                return 0;
            }

            try {
                Instruction current = code.get(state.getIndex());
                current.execute(state);
            } catch (InfiniteLoopException e) {
                System.out.println(String.format("Infinite loop after %d instructions!", state.getIndex()));
                return 1;
            }
        }
    }
}
