package edu.mit.compilers.ll;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.LlSymbolTable;
import edu.mit.compilers.StackFrame;

/**
 * Created by devinmorgan on 11/18/16.
 */
public class LlLiteralInt extends LlLiteral {
    private final long intValue;

    public LlLiteralInt(long intValue) {
        this.intValue = intValue;
    }

    public long getIntValue() {
        return this.intValue;
    }

    @Override
    public String toString() {
        return Long.toString(this.intValue);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LlLiteralInt)) {
            return false;
        }
        return ((LlLiteralInt)obj).intValue == this.intValue;

    }
    public String generateCode(AssemblyBuilder builder, StackFrame frame, LlSymbolTable symbolTable){
        builder.addComment("generating code for " + this.toString());
        builder.addLine();
        return "$" + Long.toString(this.intValue);
    }
}
