package edu.mit.compilers.ir;

import edu.mit.compilers.ScopeStack;

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
}