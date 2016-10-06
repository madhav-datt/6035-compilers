package edu.mit.compilers.ir;

public class IrSizeOfType extends IrSizeOf {
    private final IrType type;

    public IrSizeOfType(IrType type, int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.type = type;
    }
}