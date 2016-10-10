package edu.mit.compilers.ir;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrAssignStmtPlusEqual extends IrAssignStmt{
    private final IrExpr incrementBy;

    public IrAssignStmtPlusEqual(IrLocation storeLocation, IrExpr incrementBy, int lineNumber, int colNumber) {
        super(storeLocation, lineNumber, colNumber);
        this.incrementBy = incrementBy;
    }
}
