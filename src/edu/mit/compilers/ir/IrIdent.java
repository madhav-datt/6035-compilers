package edu.mit.compilers.ir;

import edu.mit.compilers.*;
import edu.mit.compilers.ll.LlLocation;

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

    @Override
    public String prettyPrint(String indentSpace) {
        return "";
    }

    @Override
    public LlLocation generateLlIr(LlBuilder builder, LlSymbolTable symbolTable) {
        return null;
    }
}