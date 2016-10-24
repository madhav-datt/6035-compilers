package edu.mit.compilers.ir;

import edu.mit.compilers.LocalVariableTable;

public class IrLiteralBool extends IrExprLiteral {
    private final boolean value;

    public IrLiteralBool(boolean value, int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.value = value;
    }

    @Override
    public IrType getExpressionType() {
        return new IrTypeBool(this.getLineNumber(), this.getColNumber());
    }

    // Returns a constant corresponding to the bool literal.
    // Doesn't mutate the assemblyCode or the VariableTable
    public String generateCode(StringBuilder assemblySoFar, LocalVariableTable table){
        return this.value ? "$1" : "$0";
    }
}