package edu.mit.compilers.ir;

public class IrExpr extends Ir {

    public IrExpr(int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
    }
    public String toString(){
        return "IrExpr";
    }
}