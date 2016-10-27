package edu.mit.compilers.ir;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.Register;
import edu.mit.compilers.ScopeStack;
import edu.mit.compilers.StackFrame;

public class IrIdent extends Ir {

    private final String name;

    public IrIdent(String name, int lineNumber, int colNumber){
        super(lineNumber, colNumber);
        this.name = name;
    }

    public String getValue(){
        return this.name;
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        return "";
    }
    public AssemblyBuilder generateCode(AssemblyBuilder assembly, Register register, StackFrame stackFrame){

        return assembly;
    }
    @Override
    public boolean equals(Object that) {
        if (that == this) {
            return true;
        }
        if (!(that instanceof IrIdent)) {
            return false;
        }
        return(this.getValue().equals(((IrIdent)that).getValue()));

    }
}