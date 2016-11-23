package edu.mit.compilers.ll;

/**
 * Created by devinmorgan on 11/18/16.
 */
public class LlAssignStmtRegular extends LlAssignStmt {
    private final LlComponent operand;

    public LlAssignStmtRegular(LlLocation storeLocation, LlComponent operand) {
        super(storeLocation);
        this.operand = operand;
    }

    public LlComponent getOperand() {
        return operand;
    }

    @Override
    public String toString() {
        return this.storeLocation.toString() + " = " + this.operand.toString();
    }
}
