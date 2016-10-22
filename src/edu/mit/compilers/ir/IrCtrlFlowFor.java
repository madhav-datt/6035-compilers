package edu.mit.compilers.ir;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrCtrlFlowFor extends IrCtrlFlow {
    private final IrLocationVar counter;
    private final IrAssignStmt compoundAssignStmt;

    public IrCtrlFlowFor(IrLocationVar counter, IrAssignStmt updateCounterStmt,
                      IrExpr condExpr, IrCodeBlock stmtBody) {
        super(condExpr, stmtBody);
        this.counter = counter;
        this.compoundAssignStmt = updateCounterStmt;
    }
}
