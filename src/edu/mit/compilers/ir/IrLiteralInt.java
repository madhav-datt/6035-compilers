package edu.mit.compilers.ir;

public class IrLiteralInt extends IrLiteral {
    private final int value;

    public IrLiteralInt(int value, int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.value = value;
    }
}