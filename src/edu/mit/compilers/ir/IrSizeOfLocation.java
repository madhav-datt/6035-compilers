package edu.mit.compilers.ir;

public class IrSizeOfLocation extends IrSizeOf {
    private final IrIdent varName;

    public IrSizeOfLocation(IrIdent varName, int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.varName = varName;
    }
}