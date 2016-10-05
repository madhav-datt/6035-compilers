package edu.mit.compilers.ir;

public class IrCharLiteral extends IrLiteral {
    private final char value;

    public IrCharLiteral(char value, int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.value = value;
    }
}