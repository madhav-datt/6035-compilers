package edu.mit.compilers.ir;

/**
 * Created by devinmorgan on 10/5/16.
 */
public abstract class IrAssignStmt extends Ir{
    private final IrLocation storeLocation;

    public IrAssignStmt(IrLocation storeLocation, int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.storeLocation = storeLocation;
    }
}
