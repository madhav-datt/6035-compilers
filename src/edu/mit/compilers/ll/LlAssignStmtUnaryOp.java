package edu.mit.compilers.ll;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.LlSymbolTable;
import edu.mit.compilers.StackFrame;

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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LlAssignStmtUnaryOp)) {
            return false;
        }
        return ((LlAssignStmtUnaryOp)obj).arg.equals(this.arg)
                &&((LlAssignStmtUnaryOp)obj).operand.equals(this.operand)
                &&((LlAssignStmtUnaryOp)obj).storeLocation.equals(this.storeLocation);
    }
    public String generateCode(AssemblyBuilder builder, StackFrame frame, LlSymbolTable symbolTable){
        // compute the value of the expression and figure out where it is stored
        builder.addComment("generating code for " + this.toString());
        String exprResultLocation = this.arg.generateCode(builder, frame, symbolTable);
        builder.addLinef("movq", exprResultLocation + ", %r10");
        builder.addLine();
        return "%r10";


    }
}
