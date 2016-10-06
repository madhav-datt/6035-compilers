package edu.mit.compilers.ir;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrAssignStmtMinusEqual extends IrAssignStmt {
    private final IrExpr decrementBy;

    public IrAssignStmtMinusEqual(IrLocation storeLocation, IrExpr decrementBy, int lineNumber, int colNumber) {
        super(storeLocation, lineNumber, colNumber);
        this.decrementBy = decrementBy;
    }
}
