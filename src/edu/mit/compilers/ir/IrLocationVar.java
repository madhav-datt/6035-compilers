package edu.mit.compilers.ir;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrLocationVar extends IrLocation {
    public IrLocationVar(IrIdent varName, IrTypeVar varType, int lineNumber, int colNumber) {
        super(varName, varType,lineNumber, colNumber);
    }
}
