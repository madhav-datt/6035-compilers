package edu.mit.compilers.ir;

/**
 * Created by devinmorgan on 10/23/16.
 */
public abstract class IrStmtReturn extends IrStatement {
    public IrStmtReturn(int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
    }

    public abstract IrType getExpressionType();

}