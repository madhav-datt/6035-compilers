package edu.mit.compilers.ir;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrStmtReturnExpr {
    private final IrExpr result;

    public IrStmtReturnExpr(IrExpr result, int lineNumber, int colNumber) {
        this.result = result;
    }
}
