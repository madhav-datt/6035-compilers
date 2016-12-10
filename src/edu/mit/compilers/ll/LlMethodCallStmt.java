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
        /*

        .cfi_startproc
     pushq     %rbp
     .cfi_def_cfa_offset 16
     .cfi_offset 6, -16
     movq %rsp, %rbp
     .cfi_def_cfa_register 6
         */

        for(int i = 0; i< argsList.size(); i++){
            if(argsList.get(i) instanceof LlLocationArray){
                String storageLoc = argsList.get(i).generateCode(builder, frame, symbolTable);
                builder.addLinef("movq",  storageLoc + ", %r10");
                builder.addLinef("movq",  "(%rbp, %r10, 8), %r10");
                if(i < 6) {
                    builder.addLinef("movq",  "%r10, " + paramRegs[i]);
                }
                else{
                    int stackBottom = -(16 + (i-6)*8);
                    builder.addLinef("movq", storageLoc + ", " + Integer.toString(stackBottom)+"(%rbp)");
                }
            }
            else{
                if(((LlLocationVar) argsList.get(i)).isStringLoc()){
                    String storedStringLabel = builder.getFromStringTable(((LlLocationVar) argsList.get(i)).getVarName());
                    if(i < 6) {
                        builder.addLinef("movq", "$"+storedStringLabel + ", " + paramRegs[i]);
                    }
                    else{
                        int stackBottom = -(16 + (i-6)*8);
                        builder.addLinef("mov", storedStringLabel + ", %" + Integer.toString(stackBottom)+"rbp");
                    }
                }
                else{
                    String storageLoc = argsList.get(i).generateCode(builder, frame, symbolTable);
                    if(i < 6) {
                        builder.addLinef("movq", storageLoc + ", " + paramRegs[i]);
                    }
                    else{
                        int stackBottom = -(16 + (i-6)*8);
                        builder.addLinef("movq", storageLoc + ", " + Integer.toString(stackBottom)+"(%rbp)");
                    }
                }

            }

        }
        // take care of caller saving here
        builder.addLinef("movq", "$0, %rax");
        builder.addLinef("call", this.methodName);
        /*

          leave
     .cfi_def_cfa 7, 8
     ret
     .cfi_endproc
         */

        // check if the return location is already stored in the stack
        String retLocation;
        if(frame.getLlLocation(this.returnLocation) == null){
            // generate new return location
            retLocation = frame.getNextStackLocation();
            frame.pushToStackFrame(this.returnLocation);
            builder.addLinef("movq", "%rax, " +retLocation);
        }
        else{
            retLocation = frame.getLlLocation(this.returnLocation);
            frame.pushToStackFrame(this.returnLocation);
            builder.addLinef("movq", "%rax, " +retLocation);
        }
        builder.addLine();
        return retLocation;

    }
}
