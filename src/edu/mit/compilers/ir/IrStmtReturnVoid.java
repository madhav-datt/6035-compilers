package edu.mit.compilers.ir;

import edu.mit.compilers.ScopeStack;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrStmtReturnVoid extends IrStmtReturn {
    public IrStmtReturnVoid(int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        return "";
    }

    @Override
    public IrType getExpressionType() {
        return new IrTypeVoid(this.getLineNumber(), this.getColNumber());
    }
}
