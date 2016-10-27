package edu.mit.compilers.ir;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.Register;
import edu.mit.compilers.ScopeStack;
import edu.mit.compilers.StackFrame;

/**
 * Created by devinmorgan on 10/11/16.
 */
public class IrType extends Ir{
    public IrType(int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        return "";
    }
    public AssemblyBuilder generateCode(AssemblyBuilder assembly, Register register, StackFrame stackFrame){

        return assembly;
    }

}
