package edu.mit.compilers.ll;


import java.util.ArrayList;
import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.LlSymbolTable;
import edu.mit.compilers.StackFrame;
import java.util.List;
import java.util.Stack;

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

    public String getMethodName() {
        return this.methodName;
    }

    public LlLocation getReturnLocation() {
        return this.returnLocation;
    }

    public List<LlComponent> getArgsList() {
        return new ArrayList<>(this.argsList);
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

    @Override
    public int hashCode() {
        return this.returnLocation.hashCode() * this.methodName.hashCode() * this.argsList.hashCode();
    }

    public String generateCode(AssemblyBuilder builder, StackFrame frame, LlSymbolTable symbolTable){
        builder.addComment("generating code for " + this.toString());
        String paramRegs[] = {"%rdi", "%rsi", "%rdx", "%rcx", "%r8", "%r9"};

        Stack<String> argPushStack = new Stack<>();
        for(int i = 0; i< argsList.size(); i++){
            if(argsList.get(i) instanceof LlLocationArray){
                if(symbolTable.isInGlobalArraysTable(new LlLocationVar(((LlLocationArray)argsList.get(i)).getVarName()))){

                    if(i < 6) {
                        String storageLoc = argsList.get(i).generateCode(builder, frame, symbolTable);
                        builder.addLinef("movq",  storageLoc + ", %r10");
                        builder.addLinef("movq",  "%r10, " + paramRegs[i]);
                    }
                    else{
                        String storageLoc = argsList.get(i).generateCode(builder, frame, symbolTable);
                        argPushStack.push(storageLoc);
                    }
                }
                else{
                    String storageLoc = argsList.get(i).generateCode(builder, frame, symbolTable);
                    builder.addLinef("movq",  storageLoc + ", %r10");

                    if(i < 6) {
                        builder.addLinef("movq",  "%r10, " + paramRegs[i]);
                    }
                    else{
                        builder.addLinef("movq",  "%r10, " + storageLoc);
                        argPushStack.push(storageLoc);
                    }
                }

            }
            else{
                if(((LlLocationVar) argsList.get(i)).isStringLoc()){

                    String storedStringLabel = builder.getFromStringTable(symbolTable.getMethodName()+"_"+((LlLocationVar) argsList.get(i)).getVarName());
                    if(i < 6) {
                        builder.addLinef("movq", "$"+storedStringLabel + ", " + paramRegs[i]);
                    }
                    else{
                        argPushStack.push(storedStringLabel);
                    }
                    while(argPushStack.size() > 0){
                        builder.addLinef("push",  argPushStack.pop());
                    }
                }
                else{

                    String storageLoc = argsList.get(i).generateCode(builder, frame, symbolTable);
                    if(i < 6) {
                        builder.addLinef("movq", storageLoc + ", " + paramRegs[i]);
                    }
                    else{
                        argPushStack.push(storageLoc);
                    }
                }
            }
        }
        while(argPushStack.size() > 0){
            builder.addLinef("push",  argPushStack.pop());
        }
        // take care of caller saving here
        builder.addLinef("movq", "$0, %rax");
        builder.addLinef("call", this.methodName);

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
