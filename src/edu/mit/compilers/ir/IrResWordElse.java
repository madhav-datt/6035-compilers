package edu.mit.compilers.ir;

import edu.mit.compilers.*;
import edu.mit.compilers.ll.LlLocation;

/**
 * Created by devinmorgan on 10/22/16.
 */
public class IrResWordElse extends Ir {
    public IrResWordElse(int lineNum, int colNum) {
        super(lineNum, colNum);
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        return "";
    }
    public AssemblyBuilder generateCode(AssemblyBuilder assembly, Register register, StackFrame stackFrame){

        return assembly;
    }

    @Override
    public String prettyPrint(String indentSpace) {
        return "";
    }

    @Override
    public LlLocation generateLlIr(LlBuilder builder, LlSymbolTable symbolTable) {
        return null;
    }
}
