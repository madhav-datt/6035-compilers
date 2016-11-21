package edu.mit.compilers.ir;

import edu.mit.compilers.*;
import edu.mit.compilers.ll.LlAssignStmtUnaryOp;
import edu.mit.compilers.ll.LlLocation;
import edu.mit.compilers.ll.LlLocationVar;

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

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        String errorMessage = "";

        // 1) check that the operand is valid
        errorMessage += this.operand.semanticCheck(scopeStack);

        // 2) verify that the operand is IrTypeBool
        if (!(this.operand.getExpressionType() instanceof IrTypeBool)) {
            errorMessage += "The not '!' operand must be used on a bool" +
                    " line: "+this.getLineNumber() + " col: " +this.getColNumber() + "\n";
        }

        return errorMessage;
    }


    @Override
    public String prettyPrint(String indentSpace) {
        String prettyString = indentSpace + "|--unaryNotOp\n";

        // pretty print the operand
        prettyString += this.operand.prettyPrint("  " + indentSpace);

        return prettyString;
    }

    @Override
    public LlLocation generateLlIr(LlBuilder builder, LlSymbolTable symbolTable) {
        LlLocation operandTemp = this.operand.generateLlIr(builder, symbolTable);

        LlLocationVar returnTemp = builder.generateTemp();
        LlAssignStmtUnaryOp unaryOp = new LlAssignStmtUnaryOp(returnTemp, operandTemp, "!");
        builder.appendStatement(unaryOp);
        return returnTemp;
    }
}
