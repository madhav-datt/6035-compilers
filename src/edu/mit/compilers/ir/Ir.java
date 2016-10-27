package edu.mit.compilers.ir;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.Register;
import edu.mit.compilers.ScopeStack;
import edu.mit.compilers.StackFrame;

public abstract class Ir {
    // keep track of the line & column number for
    // for error reporting purposes
    private final int lineNumber;
    private final int colNumber;

    public Ir(int lineNumber, int colNumber) {
        this.lineNumber = lineNumber;
        this.colNumber = colNumber;
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

    public int getColNumber() {
        return this.colNumber;
    }

    public abstract String semanticCheck(ScopeStack scopeStack);

    public abstract AssemblyBuilder generateCode(AssemblyBuilder assembly, Register register, StackFrame frame);

    public abstract String prettyPrint(String indentSpace);

}