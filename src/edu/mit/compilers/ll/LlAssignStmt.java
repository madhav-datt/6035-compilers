package edu.mit.compilers.ll;

/**
 * Created by devinmorgan on 11/18/16.
 */
public abstract class LlAssignStmt extends LlStatement {
    protected final LlLocation storeLocation;

    public LlAssignStmt(LlLocation storeLocation) {
        this.storeLocation = storeLocation;
    }
}
