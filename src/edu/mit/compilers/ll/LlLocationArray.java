package edu.mit.compilers.ll;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.LlSymbolTable;
import edu.mit.compilers.StackFrame;

/**
 * Created by devinmorgan on 11/18/16.
 */
public class LlLocationArray extends LlLocation {
    private final LlLocationVar elementIndex;

    public LlLocationArray(String varName, LlLocationVar elementIndex) {
        super(varName);
        this.elementIndex = elementIndex;
    }

    public LlLocationVar getElementIndex() {
        return this.elementIndex;
    }

    @Override
    public String toString() {
        return this.getVarName() + "[" + elementIndex.toString() + "] ";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LlLocationArray)) {
            return false;
        }
        return ((LlLocationArray)obj).elementIndex.equals(this.elementIndex)
                && ((LlLocationArray)obj).getVarName().equals(this.getVarName());

    }
    public String generateCode(AssemblyBuilder builder, StackFrame frame, LlSymbolTable symbolTable){

        return "";

    }
}
