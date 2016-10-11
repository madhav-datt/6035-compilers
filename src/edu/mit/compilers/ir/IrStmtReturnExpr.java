package edu.mit.compilers.ir;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrStmtReturnExpr extends Ir{
    private final IrExpr result;

    public IrStmtReturnExpr(IrExpr result) {
        super(result.getLineNumber(),result.getColNumber());
        this.result = result;
    }
}
