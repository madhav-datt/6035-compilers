package edu.mit.compilers.ir;

import edu.mit.compilers.ScopeStack;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrStmtReturnExpr extends IrStmtReturn{
    private final IrExpr result;

    public IrStmtReturnExpr(IrExpr result) {
        super(result.getLineNumber(),result.getColNumber());
        this.result = result;
    }

    @Override
    public IrType getExpressionType() {
        return this.result.getExpressionType();
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        return "";
    }
}
