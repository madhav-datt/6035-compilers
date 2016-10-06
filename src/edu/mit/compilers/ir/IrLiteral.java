package edu.mit.compilers.ir;

public abstract class IrLiteral extends Ir {
    public IrLiteral(int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
    }
}