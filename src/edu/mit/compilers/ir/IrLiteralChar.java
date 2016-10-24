package edu.mit.compilers.ir;

public class IrLiteralChar extends IrExprLiteral {
    private final char value;

    public IrLiteralChar(char value, int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.value = value;
    }

    @Override
    public IrType getExpressionType() {
        // it's definitely not of type void but it
        // is also not of type int or type bool
        return new IrTypeVoid(this.getLineNumber(), this.getColNumber());
    }
}