package edu.mit.compilers.ir;

public class IrLiteralBool extends IrLiteral {
    private final boolean value;

    public IrLiteralBool(boolean value, int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.value = value;
    }
}