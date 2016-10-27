package edu.mit.compilers.ir;

public class IrTypeInt extends IrType {
    public IrTypeInt(int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
    }
    @Override
    public boolean equals(Object that) {
        if (that == this) {
            return true;
        }
        if (!(that instanceof IrTypeInt)) {
            return false;
        }
        return true;
    }

    @Override
    public String prettyPrint(String indentSpace) {
        return indentSpace + "|__type: int\n";
    }
}