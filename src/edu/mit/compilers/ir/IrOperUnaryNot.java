package edu.mit.compilers.ir;

/**
 * Created by devinmorgan on 10/16/16.
 */
public class IrOperUnaryNot extends IrOperUnary{
    public IrOperUnaryNot(IrExpr operand) {
        super(operand);
    }

    @Override
    public IrType getExpressionType() {
        return new IrTypeBool(this.operand.getLineNumber(), this.operand.getColNumber());
    }

    public String generateCode(String reg){
        return "not " + reg;
    }
}
