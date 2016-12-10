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
        // check if the storeLocation already exists, if not, create a new one
        String storeToLoc;
        // check if the storage location is an array access
        if(this.storeLocation instanceof LlLocationArray){
            storeToLoc = this.storeLocation.generateCode(builder, frame, symbolTable);
            builder.addComment("adding something to " + storeToLoc);
            builder.addLinef("movq",  storeToLoc + ", %r10");
            builder.addLinef("movq",  "(%rbp, %r10, 8), %r10");
            builder.addLinef("movq", "%r10, " + storeToLoc);
        }
        else {
            String checkFrame = frame.getLlLocation(this.storeLocation);
            if (checkFrame == null) {
                storeToLoc = frame.getNextStackLocation();
                frame.pushToStackFrame(this.storeLocation);
            } else {
                storeToLoc = checkFrame;
            }
            builder.addLinef("movq", exprResultLocation+", %r10" );
            builder.addLinef("movq",  "%r10, " + storeToLoc);
        }

        builder.addLine();
        return storeToLoc;

    }
}
