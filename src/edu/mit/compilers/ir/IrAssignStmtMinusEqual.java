package edu.mit.compilers.ir;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrAssignStmtMinusEqual extends IrAssignStmt {
    private final IrExpr decrementBy;

    public IrAssignStmtMinusEqual(IrLocation storeLocation, IrExpr decrementBy) {
        super(storeLocation);
        this.decrementBy = decrementBy;
    }
}
