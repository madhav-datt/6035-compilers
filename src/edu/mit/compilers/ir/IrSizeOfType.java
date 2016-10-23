package edu.mit.compilers.ir;

import edu.mit.compilers.ScopeStack;

public class IrSizeOfType extends IrSizeOf {
    private final IrType type;

    public IrSizeOfType(IrType type) {
        super(type.getLineNumber(), type.getColNumber());
        this.type = type;
    }

    @Override
    public IrType getExpressionType() {
        return this.type;
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        return "";
    }
}