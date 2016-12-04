package edu.mit.compilers.ll;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.LlSymbolTable;
import edu.mit.compilers.StackFrame;

/**
 * Created by devinmorgan on 11/18/16.
 */
public class LlAssignStmtRegular extends LlAssignStmt {
    private final LlComponent arg;

    public LlAssignStmtRegular(LlLocation storeLocation, LlComponent arg) {
        super(storeLocation);
        this.arg = arg;
    }

    @Override
    public String toString() {
        return this.storeLocation.toString() + " = " + this.arg.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LlAssignStmtRegular)) {
            return false;
        }
        return ((LlAssignStmtRegular)obj).arg.equals(this.arg)
                &&((LlAssignStmtBinaryOp)obj).storeLocation.equals(this.storeLocation);
    }
    public String generateCode(AssemblyBuilder builder, StackFrame frame, LlSymbolTable symbolTable){
        // compute the value of the expression and figure out where it is stored
        builder.addComment("generating code for " + this.toString());
        String exprResultLocation = this.arg.generateCode(builder, frame, symbolTable);
        // might be replaced by reg-alloc vals
        String storeToLoc = frame.getNextStackLocation();
        frame.pushToStackFrame(this.storeLocation);

        builder.addLinef("movq", exprResultLocation+", " + storeToLoc);
        builder.addLine();
        return storeToLoc;

    }
}
