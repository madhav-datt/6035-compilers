package edu.mit.compilers.ir.Location;

import edu.mit.compilers.ir.IrExpr;
import edu.mit.compilers.ir.IrIdent;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrArrayElementLocation extends IrLocation {
    private final IrExpr elementIndex;

    public IrArrayElementLocation(IrExpr elementIndex, IrIdent varName, int lineNumber, int colNumber) {
        super(varName, lineNumber, colNumber);
        this.elementIndex = elementIndex;
    }
}
