package edu.mit.compilers.ll;

/**
 * Created by devinmorgan on 11/18/16.
 */
public class LlAssignStmtUnaryOp extends LlAssignStmt{
    public LlComponent getArg() {
        return arg;
    }

    private final LlComponent arg;
    private final String operand;

    public LlAssignStmtUnaryOp(LlLocation storeLocation, LlComponent arg, String operand) {
        super(storeLocation);
        this.arg = arg;
        this.operand = operand;
    }

    @Override
    public String toString() {
        return this.storeLocation.toString() + " = " + operand + " " + arg.toString();
    }
}
