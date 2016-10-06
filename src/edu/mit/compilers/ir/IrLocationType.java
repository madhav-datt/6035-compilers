package edu.mit.compilers.ir;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrLocationType extends IrLocation {
    public IrLocationType(IrIdent varName, int lineNumber, int colNumber) {
        super(varName, lineNumber, colNumber);
    }
}
