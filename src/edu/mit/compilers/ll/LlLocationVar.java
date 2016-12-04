package edu.mit.compilers.ll;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.LlSymbolTable;
import edu.mit.compilers.StackFrame;

/**
 * Created by devinmorgan on 11/18/16.
 */
public class LlLocationVar extends LlLocation {
    public LlLocationVar(String varName) {
        super(varName);
    }

    @Override
    public String toString() {
        return this.getVarName().toString();
    }

    public boolean isStringLoc(){
        return this.getVarName().contains("str");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LlLocationVar)) {
            return false;
        }
        return ((LlLocationVar)obj).getVarName().equals(this.getVarName());


    }
    public String generateCode(AssemblyBuilder builder, StackFrame frame, LlSymbolTable symbolTable){
        // A variable can either be declared in the method body or be passed as a param.
        // Meaning we check the symbolTable and if it is not there, it must be declared in the method
        // body.
        // If it is declared in the method body ...

        String stackLocation = frame.getLlLocation(this);
        if(stackLocation == null)
            return symbolTable.getFromParamTable(this);
        return stackLocation;

    }
}
