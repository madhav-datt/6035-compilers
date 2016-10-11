package edu.mit.compilers.ir;

/**
 * Created by abel on 10/5/16.
 */
public class IrOperBinary extends  IrOper {
    public String operation;
    public IrExpr leftOperand;
    public IrExpr rightOperand;
    public IrOperBinary(String operation, IrExpr leftOperand, IrExpr rightOperand){
        super(leftOperand.getLineNumber(), leftOperand.getColNumber());
        this.operation = operation;
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

   public String toString(){
       return leftOperand.toString() + " " +  operation + " "+ rightOperand.toString();
   }
}

