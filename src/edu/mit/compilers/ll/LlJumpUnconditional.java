package edu.mit.compilers.ll;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.LlSymbolTable;
import edu.mit.compilers.StackFrame;

/**
 * Created by devinmorgan on 11/18/16.
 */
public class LlJumpUnconditional extends LlJump{
    public LlJumpUnconditional(String jumpToLabel) {
        super(jumpToLabel);
    }

    @Override
    public String toString() {
        return  "goto " + this.jumpToLabel.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LlJumpUnconditional)) {
            return false;
        }
        return ((LlJumpUnconditional)obj).jumpToLabel.equals(this.jumpToLabel);

    }

    @Override
    public String generateCode(AssemblyBuilder builder, StackFrame frame, LlSymbolTable symbolTable) {
        builder.addComment("generating code for " + this.toString());
        builder.addLinef("jmp", symbolTable.getMethodName()+"_"+this.jumpToLabel);
        builder.addLine();
        return "";
    }
}
