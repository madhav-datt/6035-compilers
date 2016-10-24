package edu.mit.compilers.ir;

import edu.mit.compilers.ScopeStack;

public class IrLiteralInt extends IrLiteral {
    private final long value;

    public IrLiteralInt(long value, int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.value = value;
    }

    @Override
    public IrType getExpressionType() {
        return new IrTypeInt(this.getLineNumber(), this.getColNumber());
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        return "";
    }
}