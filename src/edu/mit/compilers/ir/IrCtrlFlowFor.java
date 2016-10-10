package edu.mit.compilers.ir;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrCtrlFlowFor extends IrCtrlFlow {
    private final IrIdent counter;
    private final IrAssignStmt updateCounterStmt;

    public IrCtrlFlowFor(IrIdent counter, IrAssignStmt updateCounterStmt,
                      IrExpr condExpr, IrCodeBlock stmtBody, int lineNumber, int colNumber) {
        super(condExpr, stmtBody, lineNumber, colNumber);
        this.counter = counter;
        this.updateCounterStmt = updateCounterStmt;
    }
}
