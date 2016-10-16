package edu.mit.compilers.ir;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrLocationArray extends IrLocation {
    private final IrExpr elementIndex;

    public IrLocationArray(IrExpr elementIndex, IrIdent varName, IrTypeVar varType, int lineNumber, int colNumber) {
        super(varName, varType, lineNumber, colNumber);
        this.elementIndex = elementIndex;
    }
}
