package edu.mit.compilers.ir;

public class IrBoolLiteral extends IrLiteral {
    private final boolean value;

    public IrBoolLiteral(boolean value, int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.value = value;
    }
}