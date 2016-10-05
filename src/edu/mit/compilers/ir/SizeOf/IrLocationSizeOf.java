package edu.mit.compilers.ir;

public class IrLocationSizeOf extends IrSizeOf {
    private final IrIdent varName;

    public IrLocationSizeOf(IrIdent varName, int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.varName = varName;
    }
}