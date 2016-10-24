package edu.mit.compilers.ir;

/**
 * Created by devinmorgan on 10/16/16.
 */
public class IrOperUnaryNeg extends IrOperUnary{
    public IrOperUnaryNeg(IrExpr operand) {
        super(operand);
    }

    @Override
    public IrType getExpressionType() {
        return new IrTypeInt(this.operand.getLineNumber(), this.operand.getColNumber());
    }
    public String generateCode(String reg1){
        return "neg " + reg1;
    }
}
