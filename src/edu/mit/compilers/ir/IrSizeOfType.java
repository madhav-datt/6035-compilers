package edu.mit.compilers.ir;

public class IrSizeOfType extends IrSizeOf {
    private final IrType type;

    public IrSizeOfType(IrType type) {
        super(type.getLineNumber(), type.getColNumber());
        this.type = type;
    }

    @Override
    public IrType getExpressionType() {
        return this.type;
    }
}