package edu.mit.compilers.ir;

import edu.mit.compilers.LlBuilder;
import edu.mit.compilers.ll.LlLocationVar;

public abstract  class IrExpr extends Ir {

    public IrExpr(int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
    }
    public abstract IrType getExpressionType();
    public String toString(){
        return "IrExpr";
    }

}