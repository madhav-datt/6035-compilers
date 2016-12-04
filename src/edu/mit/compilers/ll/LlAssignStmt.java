package edu.mit.compilers.ll;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.LlSymbolTable;
import edu.mit.compilers.StackFrame;

/**
 * Created by devinmorgan on 11/18/16.
 */
public abstract class LlAssignStmt extends LlStatement {
    protected final LlLocation storeLocation;

    public LlAssignStmt(LlLocation storeLocation) {
        this.storeLocation = storeLocation;
    }

    public String generateCode(AssemblyBuilder builder, StackFrame frame, LlSymbolTable symbolTable){
        return null;
    }

}
