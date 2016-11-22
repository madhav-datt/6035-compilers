package edu.mit.compilers.ll;

/**
 * Created by devinmorgan on 11/18/16.
 */
public class LlAssignStmtBinaryOp extends LlAssignStmt{
    private final LlComponent leftArg;
    private final String operation;
    private final LlComponent rightArg;

    public LlAssignStmtBinaryOp(LlLocation storeLocation, LlComponent leftArg, String operand, LlComponent rightArg) {
        super(storeLocation);
        this.leftArg = leftArg;
        this.operation = operand;
        this.rightArg = rightArg;
    }

    @Override
    public String toString() {
        return this.storeLocation.toString() + " = " + this.leftArg.toString() + " " +this.operation  + " " + this.rightArg.toString();
    }
}