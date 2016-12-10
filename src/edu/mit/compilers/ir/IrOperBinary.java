package edu.mit.compilers.ir;

/**
 * Created by abel on 10/5/16.
 */
public abstract class IrOperBinary extends IrOper {
    public String operation;
    public IrExpr leftOperand;
    public IrExpr rightOperand;

    public IrOperBinary(String operation, IrExpr leftOperand, IrExpr rightOperand){
        super(leftOperand.getLineNumber(), leftOperand.getColNumber());
        this.operation = operation;
        this.leftOperand = IrExpr.canonicalizeExpr(leftOperand);
        this.rightOperand = IrExpr.canonicalizeExpr(rightOperand);
    }

    public IrExpr getLeftOperand() {
        return this.leftOperand;
    }

    public IrExpr getRightOperand() {
        return this.rightOperand;
    }

    public String getOperation(){
        return this.operation;
    }

    public String toString(){
       return leftOperand.toString() + " " +  operation + " "+ rightOperand.toString();
   }

}

