package edu.mit.compilers.ll;

/**
 * Created by devinmorgan on 11/18/16.
 */
public class LlAssignStmtUnaryOp extends LlAssignStmt{
    private final LlComponent operand;
    private final String operator;

    public LlAssignStmtUnaryOp(LlLocation storeLocation, LlComponent operand, String operator) {
        super(storeLocation);
        this.operand = operand;
        this.operator = operator;
    }

    public LlComponent getOperand() {
        return operand;
    }

    @Override
    public String toString() {
        return this.storeLocation.toString() + " = " + operator + " " + operand.toString();
    }
}
