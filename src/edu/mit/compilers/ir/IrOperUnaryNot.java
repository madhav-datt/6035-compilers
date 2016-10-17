package edu.mit.compilers.ir;

/**
 * Created by devinmorgan on 10/16/16.
 */
public class IrOperUnaryNot extends IrOperUnary{
    public IrOperUnaryNot(String operation, IrExpr operand) {
        super(operation, operand);
    }

    @Override
    public IrType getExpressionType() {
        return new IrTypeInt(this.operand.getLineNumber(), this.operand.getColNumber());
    }
}
