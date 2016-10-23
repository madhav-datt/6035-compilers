package edu.mit.compilers.ir;

import edu.mit.compilers.ScopeStack;

public class IrParamDecl extends Ir {
    private final IrType paramType;
    private final IrIdent paramName;

    public IrParamDecl(IrType paramType, IrIdent paramName) {
        super(paramName.getLineNumber(), paramName.getColNumber());
        this.paramType = paramType;
        this.paramName = paramName;
    }
    public String toString(){
        return paramType.toString() + " " + paramName.toString();
    }

    public IrType getExpressionType() {
        return this.paramType;
    }

    public IrIdent getParamName() {
        return this.paramName;
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        return "";
    }
}