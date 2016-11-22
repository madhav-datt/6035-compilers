package edu.mit.compilers.ir;

import edu.mit.compilers.*;
import edu.mit.compilers.ll.LlLocation;

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

    /*
        - pass a global LlBuilder object to every funciton implementing this.

     */
    public abstract LlLocation generateLlIr(LlBuilder builder, LlSymbolTable symbolTable);

    public abstract String prettyPrint(String indentSpace);

}