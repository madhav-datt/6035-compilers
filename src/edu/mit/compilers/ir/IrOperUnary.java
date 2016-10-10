package edu.mit.compilers.ir;

public class IrOperUnary extends IrOper{
    public String operation;
    public IrExpr operand;
    public IrOperUnary(String operation, IrExpr operand, int lineNum, int colNum){
        super(lineNum, colNum);
        this.operation = operation;
        this.operand = operand;
    }
    public String toString(){
        return this.operation + "("+this.operand+")";
    }
}
