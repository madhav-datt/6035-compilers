package edu.mit.compilers.ir;

import edu.mit.compilers.LocalVariableTable;

public abstract  class IrExpr extends Ir {

    public IrExpr(int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
    }
    public abstract IrType getExpressionType();
    public String toString(){
        return "IrExpr";
    }

    // Expects the inheriting classes to have this method implemented.
    public String generateCode(StringBuilder assemblySoFar, LocalVariableTable table){
        return "";
    }
}