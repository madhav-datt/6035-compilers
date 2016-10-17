package edu.mit.compilers.ir;

public abstract class IrOperUnary extends IrOper{
    public String operation;
    public IrExpr operand;
    public IrOperUnary(String operation, IrExpr operand){
        super(operand.getLineNumber(), operand.getColNumber());
        this.operation = operation;
        this.operand = operand;
    }
    public String toString(){
        return this.operation + "("+this.operand+")";
    }
}
