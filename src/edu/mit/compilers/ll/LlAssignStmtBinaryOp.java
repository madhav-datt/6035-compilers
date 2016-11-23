package edu.mit.compilers.ll;

/**
 * Created by devinmorgan on 11/18/16.
 */
public class LlAssignStmtBinaryOp extends LlAssignStmt{
    private final LlComponent leftOperand;
    private final String operation;
    private final LlComponent rightOperand;

    public LlAssignStmtBinaryOp(LlLocation storeLocation, LlComponent leftOperand, String operand, LlComponent rightOperand) {
        super(storeLocation);
        this.leftOperand = leftOperand;
        this.operation = operand;
        this.rightOperand = rightOperand;
    }

    public LlComponent getLeftOperand() {
        return this.leftOperand;
    }

    public LlComponent getRightOperand() {
        return this.rightOperand;
    }

    @Override
    public String toString() {
        return this.storeLocation.toString() + " = " + this.leftOperand.toString() + " " +this.operation  + " " + this.rightOperand.toString();
    }
}