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

    @Override
    public int hashCode() {
        return this.getVarName().hashCode();
    }

    public boolean isStringLoc(){
        return this.getVarName().contains("str");
    }

    public String generateCode(AssemblyBuilder builder, StackFrame frame, LlSymbolTable symbolTable){
        // A variable can either be declared in the method body or be passed as a param.
        // Meaning we check the symbolTable and if it is not there, it must be declared in the method
        // body.
        // If it is declared in the method body ...
        if(symbolTable.isInGlobalVarsTable(this)){
            return this.getVarName() + "(%rip)";
        }
        String allocatedReg = builder.getAllocatedReg(this);
        if(allocatedReg == null){
            String stackLocation = frame.getLlLocation(this);
            if(stackLocation == null) {
                String checkParamTable  = symbolTable.getFromParamTable(this);
                if (checkParamTable == null){
                    String newVarLoc = frame.getNextStackLocation();
                    frame.pushToStackFrame(this);
                    return newVarLoc;
                }
                else{
                    return checkParamTable;
                }
            }
            return stackLocation;
        }
        return allocatedReg;


    }
}
