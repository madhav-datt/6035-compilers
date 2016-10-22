package edu.mit.compilers.ir;

public abstract class IrSizeOf extends IrExpr {
    public IrSizeOf(int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
    }
}