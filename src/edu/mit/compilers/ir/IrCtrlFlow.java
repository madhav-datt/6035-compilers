package edu.mit.compilers.ir;

/**
 * Created by devinmorgan on 10/5/16.
 */
public abstract class IrCtrlFlow extends IrStatement {
    final private IrExpr condExpr;
    final private IrCodeBlock stmtBody;

    public IrCtrlFlow(IrExpr condExpr, IrCodeBlock stmtBody, int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.condExpr = condExpr;
        this.stmtBody = stmtBody;
    }

}
