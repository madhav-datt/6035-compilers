package edu.mit.compilers.ir;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrAssignStmtPlusEqual extends IrAssignStmt{
    private final IrExpr incrementBy;

    public IrAssignStmtPlusEqual(IrLocation storeLocation, IrExpr incrementBy) {
        super(storeLocation);
        this.incrementBy = incrementBy;
    }
}
