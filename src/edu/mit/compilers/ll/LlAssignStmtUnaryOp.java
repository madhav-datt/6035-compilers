package edu.mit.compilers.ll;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.LlSymbolTable;
import edu.mit.compilers.StackFrame;

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
        return this.operand;
    }

    public String getOperator() {
        return this.operator;
    }

    @Override
    public String toString() {
        return this.storeLocation.toString() + " = " + operator + " " + operand.toString();
    }



    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LlAssignStmtUnaryOp)) {
            return false;
        }
        return ((LlAssignStmtUnaryOp)obj).operand.equals(this.operand)
                &&((LlAssignStmtUnaryOp)obj).operand.equals(this.operand)
                &&((LlAssignStmtUnaryOp)obj).storeLocation.equals(this.storeLocation);
    }
    public String generateCode(AssemblyBuilder builder, StackFrame frame, LlSymbolTable symbolTable){
        // compute the value of the expression and figure out where it is stored
        builder.addComment("generating code for " + this.toString());
        String exprResultLocation = this.operand.generateCode(builder, frame, symbolTable);
        String returnLocation = frame.getNextStackLocation();

        if(this.operand.equals("!")){
            builder.addLinef("movq", exprResultLocation + ", %r10");
            builder.addLinef("xorq", "$1, %r10");

            builder.addLinef("movq", "%r10, " + returnLocation);
        }
        else if(this.operand.equals("-")){
            builder.addLinef("movq", exprResultLocation + ", %r10");
            builder.addLinef("neg",  "%r10");
            builder.addLinef("movq", "%r10, " + returnLocation);
        }

        frame.pushToStackFrame(this.storeLocation);
        builder.addLine();
        return returnLocation;


    }
}
