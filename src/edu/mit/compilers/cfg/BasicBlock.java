package edu.mit.compilers.cfg;

import edu.mit.compilers.ll.LlStatement;

import java.util.ArrayList;

/**
 * Created by devinmorgan on 11/20/16.
 */
public class BasicBlock {
    private final ArrayList<LlStatement> stmtsList;
    private final BasicBlock optionA;
    private final BasicBlock optionB;

    public BasicBlock(ArrayList<LlStatement> stmtsList, BasicBlock optionA, BasicBlock optionB) {
        this.stmtsList = new ArrayList<>(stmtsList);
        this.optionA = optionA;
        this.optionB = optionB;
    }

    public BasicBlock(ArrayList<LlStatement> stmtsList, BasicBlock optionA) {
        this(stmtsList, optionA, null);
    }
}
