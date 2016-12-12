package edu.mit.compilers.ll;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.LlSymbolTable;
import edu.mit.compilers.StackFrame;

/**
 * Created by devinmorgan on 11/18/16.
 */
public abstract class LlJump extends LlStatement {
    protected final String jumpToLabel;

    public LlJump(String jumpToLabel) {
        this.jumpToLabel = jumpToLabel;
    }

    public String getJumpToLabel() {
        return jumpToLabel;
    }

    public String generateCode(AssemblyBuilder builder, StackFrame frame, LlSymbolTable symbolTable){
        // compute the value of the expression and figure out where it is store
        builder.addComment("generating code for " + this.toString());
        builder.addLine("je "+ this.jumpToLabel);
        builder.addLine();
        return "";
    }
}
