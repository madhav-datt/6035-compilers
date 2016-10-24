package edu.mit.compilers.ir;

import edu.mit.compilers.LocalVariableTable;
import javafx.beans.binding.StringBinding;

/**
 * Created by devinmorgan on 10/16/16.
 */
public class IrOperBinaryArith extends IrOperBinary {

    public IrOperBinaryArith(String operation, IrExpr leftOperand, IrExpr rightOperand) {
        super(operation, leftOperand, rightOperand);
    }
    private String getCommand(String operation){
        String retCommand = "";
        switch (operation){
            case "+":
                retCommand = "add ";
                break;
            case "-":
                retCommand = "sub ";
                break;
            case "*":
                retCommand = "imul ";
                break;
            case "/":
                retCommand = "idiv ";
                break;
            default:
                System.err.print("Runtime Error: Unrecognized Operation");
                break;
        }
        return retCommand;
    }
    @Override
    public IrType getExpressionType() {
        return new IrTypeInt(this.leftOperand.getLineNumber(), this.leftOperand.getColNumber());
    }

    public String generateCode(StringBuilder assemblySoFar, LocalVariableTable table){
        String freeRegister = table.getFreeLocalRegister();
        String leftRegister = leftOperand.generateCode(assemblySoFar, table);
        String rightRegister = rightOperand.generateCode(assemblySoFar, table);
        assemblySoFar.append(getCommand(this.getOperation()) + rightRegister + ", "+ leftRegister);
        assemblySoFar.append("\n");
        return leftRegister;
    }
}
