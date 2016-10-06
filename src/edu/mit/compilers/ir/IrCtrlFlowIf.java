package edu.mit.compilers.ir;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrCtrlFlowIf extends IrCtrlFlow {
    public IrCtrlFlowIf(IrExpr condExpr, IrCodeBlock stmtBody, int lineNumber, int colNumber) {
        super(condExpr, stmtBody, lineNumber, colNumber);
    }
}
