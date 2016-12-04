package edu.mit.compilers.ll;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.LlSymbolTable;
import edu.mit.compilers.StackFrame;

import java.util.List;

/**
 * Created by devinmorgan on 11/18/16.
 */
public class LlMethodCallStmt extends LlStatement {
    private final LlLocation returnLocation;
    private final String methodName;
    private final List<LlComponent> argsList;

    public LlMethodCallStmt(String methodName, List<LlComponent> argsList, LlLocation returnLocation) {
        this.methodName = methodName;
        this.argsList = argsList;
        this.returnLocation = returnLocation;
    }

    @Override
    public String toString() {
        String argsString = "";
        for(LlComponent arg : argsList){
            argsString +=  arg.toString() +",";
        }
        return this.returnLocation.toString() + " = " + this.methodName + "(" + argsString +")" ;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LlMethodCallStmt)) {
            return false;
        }
        return ((LlMethodCallStmt)obj).returnLocation.equals(this.returnLocation)
                && ((LlMethodCallStmt)obj).methodName.equals(this.methodName)
                && ((LlMethodCallStmt)obj).argsList.equals(this.argsList);

    }
    public String generateCode(AssemblyBuilder builder, StackFrame frame, LlSymbolTable symbolTable){
        builder.addComment("generating code for " + this.toString());
        String paramRegs[] = {"%rdi", "%rsi", "%rdx", "%rcx", "%r8", "%r9"};
        for(int i = 0; i< argsList.size(); i++){
            if(((LlLocationVar) argsList.get(i)).isStringLoc()){
                String storedStringLabel = builder.getFromStringTable(((LlLocationVar) argsList.get(i)).getVarName());
                if(i < 6) {
                    builder.addLinef("mov", storedStringLabel + ", " + paramRegs[i]);
                }
                else{
                    int stackBottom = -(16 + (i-6)*8);
                    builder.addLinef("mov", storedStringLabel + ", %" + Integer.toString(stackBottom)+"rbp");
                }
            }
            else{
                String storageLoc = argsList.get(i).generateCode(builder, frame, symbolTable);
                if(i < 6) {
                    builder.addLinef("mov", storageLoc + ", " + paramRegs[i]);
                }
                else{
                    int stackBottom = -(16 + (i-6)*8);
                    builder.addLinef("mov", storageLoc + ", %" + Integer.toString(stackBottom)+"rbp");
                }
            }

        }
        // take care of caller saving here
        builder.addLinef("call", this.methodName);
        builder.addLine();
        return "%rax";

    }
}
