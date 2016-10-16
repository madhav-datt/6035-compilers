package edu.mit.compilers.ir;

public abstract  class IrExpr extends Ir {

    public IrExpr(int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
    }
    public abstract IrType getExpressionType();
    public String toString(){
        return "IrExpr";
    }
}