package edu.mit.compilers.ir;

public class IrTypeSizeOf extends IrSizeOf {
    private final IrType type;

    public IrTypeSizeOf(IrType type, int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.type = type;
    }
}