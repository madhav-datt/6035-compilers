package edu.mit.compilers.ir;

public class IrTypeVoid extends IrType {
    public IrTypeVoid(int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
    }
    @Override
    public boolean equals(Object that) {
        if (that == this) {
            return true;
        }
        if (!(that instanceof IrTypeVoid)) {
            return false;
        }
        return true;


    }
}