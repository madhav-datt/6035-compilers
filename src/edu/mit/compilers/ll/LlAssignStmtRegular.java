package edu.mit.compilers.ll;

/**
 * Created by devinmorgan on 11/18/16.
 */
public class LlAssignStmtRegular extends LlAssignStmt {
    public LlComponent getArg() {
        return arg;
    }

    private final LlComponent arg;

    public LlAssignStmtRegular(LlLocation storeLocation, LlComponent arg) {
        super(storeLocation);
        this.arg = arg;
    }

    @Override
    public String toString() {
        return this.storeLocation.toString() + " = " + this.arg.toString();
    }
}
