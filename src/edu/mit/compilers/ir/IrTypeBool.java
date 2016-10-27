package edu.mit.compilers.ir;

public class IrTypeBool extends IrType {
    public IrTypeBool(int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
    }
    @Override
    public boolean equals(Object that) {
        if (that == this) {
            return true;
        }
        if (!(that instanceof IrTypeBool)) {
            return false;
        }
        return true;


    }
}