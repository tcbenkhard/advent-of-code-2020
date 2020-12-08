package nl.benkhard.aoc;

import nl.benkhard.aoc.instruction.Instruction;
import nl.benkhard.aoc.instruction.JumpInstruction;
import nl.benkhard.aoc.instruction.NoOpInstruction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static nl.benkhard.aoc.instruction.Instruction.lineToInstruction;

public class Main {

    public static void main(String[] args) {
        boolean run = true;
        int corruptedIndex = 0;
        while(run) {
            List<Instruction> instructions = loadInstructions();
            if(patchInstructions(instructions, corruptedIndex)) {
                Program program = new Program(instructions);
                if(program.execute() == 0)
                    run = false;
            }
            corruptedIndex++;
        }
    }

    private static boolean patchInstructions(List<Instruction> instructions, int index) {
        Instruction corrupted = instructions.get(index);
        if(corrupted instanceof NoOpInstruction) {
            instructions.set(index, corrupted.to(JumpInstruction.class));
            return true;
        } else if(corrupted instanceof JumpInstruction) {
            instructions.set(index, corrupted.to(NoOpInstruction.class));
            return true;
        }

        return false;
    }

    private static List<Instruction> loadInstructions() {
        List<String> lines = FileUtils.readFileAsListOfStrings("input1.txt");

        return lines.stream()
                .map(lineToInstruction)
                .collect(Collectors.toList());
    }
}
