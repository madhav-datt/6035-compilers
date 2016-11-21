package edu.mit.compilers.cfg;

import edu.mit.compilers.ll.LlStatement;

import java.util.ArrayList;

/**
 * Created by devinmorgan on 11/20/16.
 */
public class BasicBlock {
    private final ArrayList<LlStatement> stmtsList;
    private BasicBlock defaultBranch;
    private BasicBlock alternativeBranch;

    public BasicBlock(ArrayList<LlStatement> stmtsList) {
        this.stmtsList = new ArrayList<>(stmtsList);
    }

    public void setDefaultBranch(BasicBlock defaultBranch) {
        this.defaultBranch = defaultBranch;
    }

    public BasicBlock getDefaultBranch() {
        return defaultBranch;
    }

    public void setAlternativeBranch(BasicBlock alternativeBranch) {
        this.alternativeBranch = alternativeBranch;
    }

    public BasicBlock getAlternativeBranch() {
        return alternativeBranch;
    }

    public ArrayList<LlStatement> getStmtsList() {
        return new ArrayList<>(this.stmtsList);
    }
}
