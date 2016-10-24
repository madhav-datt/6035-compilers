package edu.mit.compilers.ir;

import edu.mit.compilers.LocalVariableTable;

public class IrLiteralInt extends IrExprLiteral {
    private final long value;

    public IrLiteralInt(long value, int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.value = value;
    }

    @Override
    public IrType getExpressionType() {
        return new IrTypeInt(this.getLineNumber(), this.getColNumber());
    }

    public String generateCode(StringBuilder assemblySoFar, LocalVariableTable table){

        return ("$" + Long.toString(value));
    }
}