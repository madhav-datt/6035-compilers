package edu.mit.compilers.ir;

public abstract class IrOper extends IrExpr{

    public IrOper(int lineNumber, int colNumber) {
        super(lineNumber, colNumber);

    }
    public String toString(){
        return "IrOper";
    }

}
