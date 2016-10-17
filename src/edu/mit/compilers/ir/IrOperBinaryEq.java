package edu.mit.compilers.ir;

/**
 * Created by devinmorgan on 10/16/16.
 */
public class IrOperBinaryEq extends IrOperBinary {

    public IrOperBinaryEq(String operation, IrExpr leftOperand, IrExpr rightOperand) {
        super(operation, leftOperand, rightOperand);
    }

    @Override
    public IrType getExpressionType() {
        return new IrTypeBool(this.leftOperand.getLineNumber(), this.leftOperand.getColNumber());
    }
}
