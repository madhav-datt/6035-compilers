package edu.mit.compilers.ir;

import edu.mit.compilers.LlBuilder;
import edu.mit.compilers.LlSymbolTable;
import edu.mit.compilers.ll.LlLocation;

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

    @Override
    public int hashCode() {
        return 13; // some arbitrary prime;
    }

    @Override
    public String prettyPrint(String indentSpace) {
        return indentSpace + "|--type: void\n";
    }

    @Override
    public LlLocation generateLlIr(LlBuilder builder, LlSymbolTable symbolTable) {
        return null;
    }
}