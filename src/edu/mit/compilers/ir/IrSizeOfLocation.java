package edu.mit.compilers.ir;

public class IrSizeOfLocation extends IrSizeOf {
    private final IrFieldDecl field;

    public IrSizeOfLocation(IrFieldDecl field, int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.field = field;
    }

    @Override
    public IrType getExpressionType() {
        return new IrTypeInt(this.field.getLineNumber(), this.field.getColNumber());
    }
}