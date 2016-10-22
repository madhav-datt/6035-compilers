package edu.mit.compilers.ir;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrAssignStmtEqual extends IrAssignStmt {
    private final IrExpr newValue;

    public IrAssignStmtEqual(IrLocation storeLocation, IrExpr newValue) {
        super(storeLocation);
        this.newValue = newValue;
    }
}
