package edu.mit.compilers.ir;

import edu.mit.compilers.LlBuilder;
import edu.mit.compilers.LlSymbolTable;
import edu.mit.compilers.ll.LlLocation;

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

    @Override
    public String prettyPrint(String indentSpace) {
        return indentSpace + "|--type: bool\n";
    }

    @Override
    public LlLocation generateLlIr(LlBuilder builder, LlSymbolTable symbolTable) {
        return null;
    }
}