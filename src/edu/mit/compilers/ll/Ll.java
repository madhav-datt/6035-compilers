package edu.mit.compilers.ll;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.LlSymbolTable;
import edu.mit.compilers.StackFrame;

/**
 * Created by devinmorgan on 11/18/16.
 */
public abstract class Ll {
//    private final String methodName;
//    public Ll(String methodName){
//        this.methodName  = methodName;
//    }
    /*
        All the inheriting classes should implement this function.
     */
    public String generateCode(AssemblyBuilder builder, StackFrame frame, LlSymbolTable symbolTable){
        return null;
    }

//    @Override
//    public boolean equals(Object obj) {
//        return super.equals(obj);
//    }
}
