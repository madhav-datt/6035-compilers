package edu.mit.compilers.ir.Location;

import edu.mit.compilers.ir.IrIdent;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrTypeLocation extends IrLocation {
    public IrTypeLocation(IrIdent varName, int lineNumber, int colNumber) {
        super(varName, lineNumber, colNumber);
    }
}
