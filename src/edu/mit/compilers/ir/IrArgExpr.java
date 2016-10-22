package edu.mit.compilers.ir;

/**
 * Created by devinmorgan on 10/16/16.
 */
public class IrArgExpr extends IrArg {
    public IrArgExpr(IrExpr expr, int lineNum, int colNum) {
        super(expr, lineNum, colNum);
    }
}
