package edu.mit.compilers.ir;

/**
 * Created by devinmorgan on 10/5/16.
 */
public abstract class IrAssignStmt extends IrStatement{
    private final IrLocation storeLocation;

    public IrAssignStmt(IrLocation storeLocation) {
        super(storeLocation.getLineNumber(), storeLocation.getColNumber());
        this.storeLocation = storeLocation;
    }

    public IrLocation getStoreLocation() {
        return this.storeLocation;
    }

}
