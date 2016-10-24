package edu.mit.compilers.ir;

import edu.mit.compilers.LocalVariableTable;

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

    // This is not done yet.
    public String generateCode(StringBuilder assemblySoFar, LocalVariableTable table){
        String freeRegister = table.getFreeLocalRegister();
        String leftRegister = leftOperand.generateCode(assemblySoFar, table);
        String rightRegister = rightOperand.generateCode(assemblySoFar, table);
        assemblySoFar.append("cmp " + rightRegister + ", "+ leftRegister);
        assemblySoFar.append("\n");
        return leftRegister;
    }
}
