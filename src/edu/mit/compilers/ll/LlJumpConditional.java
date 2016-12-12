package edu.mit.compilers.ll;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.LlSymbolTable;
import edu.mit.compilers.StackFrame;

/**
 * Created by devinmorgan on 11/18/16.
 */
public class LlJumpConditional extends LlJump{

    private final LlComponent condition;

    public LlJumpConditional(String jumpToLabel, LlComponent condition) {
        super(jumpToLabel);
        this.condition = condition;
    }

    public LlComponent getCondition() {
        return this.condition;
    }

    @Override
    public String toString() {
        return "if " + condition.toString() + " goto " + this.jumpToLabel.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LlJumpConditional)) {
            return false;
        }
        return ((LlJumpConditional)obj).jumpToLabel.equals(this.jumpToLabel);
    }

    @Override
    public int hashCode() {
        return this.jumpToLabel.hashCode();
    }

    public String generateCode(AssemblyBuilder builder, StackFrame frame, LlSymbolTable symbolTable){
        // compute the value of the expression and figure out where it is stored

        builder.addComment("generating code for " + this.toString());
        String resultOfConditional = this.condition.generateCode(builder, frame, symbolTable);
        builder.addLinef("movq", resultOfConditional + ", %r10");
        builder.addLinef("movq", "$0, %r11");
        builder.addLinef("cmp", "%r10, %r11");
        builder.addLinef("jne ", symbolTable.getMethodName()+"_"+this.jumpToLabel);
        builder.addLine();
        return "";

    }
}
