package edu.mit.compilers.ll;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.LlSymbolTable;
import edu.mit.compilers.StackFrame;

/**
 * Created by devinmorgan on 11/18/16.
 */
public class LlLiteralBool extends LlLiteral {
    private final boolean boolValue;

    public LlLiteralBool(boolean boolValue) {
        this.boolValue = boolValue;
    }

    public boolean getBoolValue() {
        return this.boolValue;
    }

    @Override
    public String toString() {
        return Boolean.toString(this.boolValue);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LlLiteralBool)) {
            return false;
        }
        return ((LlLiteralBool)obj).boolValue == (this.boolValue);

    }

    public String generateCode(AssemblyBuilder builder, StackFrame frame, LlSymbolTable symbolTable){
        builder.addComment("generating code for " + this.toString());
        builder.addLine();
        return this.boolValue ? "$1" : "$0";
    }
}
