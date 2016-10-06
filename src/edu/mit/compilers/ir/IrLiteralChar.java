package edu.mit.compilers.ir;

public class IrLiteralChar extends IrLiteral {
    private final char value;

    public IrLiteralChar(char value, int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.value = value;
    }
}