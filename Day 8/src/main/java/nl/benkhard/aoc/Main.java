package nl.benkhard.aoc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    static Function<String, Instruction> lineToInstruction = line -> {
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

    public static void main(String[] args) {
        List<String> lines = FileUtils.readFileAsListOfStrings("input1.txt");

        int instructionsSize = lines.stream()
                .map(lineToInstruction)
                .collect(Collectors.toList())
                .size();

        for(int corruptedInstructionIndex = 0; corruptedInstructionIndex< instructionsSize; corruptedInstructionIndex++) {
            State state = new State();
            List<Instruction> instructions = lines.stream()
                    .map(lineToInstruction)
                    .collect(Collectors.toList());

            Instruction corruptedInstruction = instructions.get(corruptedInstructionIndex);
            if (corruptedInstruction instanceof JumpInstruction) {
                instructions.set(corruptedInstructionIndex, corruptedInstruction.to(NoOpInstruction.class));
                System.out.printf("Changed instruction on index %d from %s to %s", corruptedInstructionIndex, JumpInstruction.class.getName(), NoOpInstruction.class.getName());
            } else if (corruptedInstruction instanceof NoOpInstruction) {
                instructions.set(corruptedInstructionIndex, corruptedInstruction.to(JumpInstruction.class));
                System.out.printf("Changed instruction on index %d from %s to %s", corruptedInstructionIndex, NoOpInstruction.class.getName(), JumpInstruction.class.getName());
            } else {
                System.out.printf("%d is not a jmp or nop\n", corruptedInstructionIndex);
                continue;
            }

            System.out.println("Starting");

            while(true) {
                if(state.getIndex() >= instructions.size()) {
                    System.out.println("End of program reached, accumulator: "+state.getAccumulator());
                    System.exit(0);
                }
                try {
                    Instruction current = instructions.get(state.getIndex());
                    current.execute(state);
                } catch (InfiniteLoopException e) {
                    System.out.println("INFINITE LOOP");
                    break;
                }
            }
        }
    }
}
