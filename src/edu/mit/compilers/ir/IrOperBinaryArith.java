package edu.mit.compilers.ir;

import edu.mit.compilers.*;
import edu.mit.compilers.ll.LlAssignStmtBinaryOp;
import edu.mit.compilers.ll.LlLocation;
import edu.mit.compilers.ll.LlLocationVar;

/**
 * Created by devinmorgan on 10/16/16.
 */
public class IrOperBinaryArith extends IrOperBinary {

    public IrOperBinaryArith(String operation, IrExpr leftOperand, IrExpr rightOperand) {
        super(operation, leftOperand, rightOperand);
    }

    @Override
    public IrType getExpressionType() {
        return new IrTypeInt(this.leftOperand.getLineNumber(), this.leftOperand.getColNumber());
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        String errorMessage = "";

        // 1) check that rhs and lhs are valid
        errorMessage += this.rightOperand.semanticCheck(scopeStack);
        errorMessage += this.leftOperand.semanticCheck(scopeStack);

        // 2) verify that both lhs and rhs are IrType int
        if (!((this.rightOperand.getExpressionType() instanceof IrTypeInt)
                && (this.leftOperand.getExpressionType() instanceof IrTypeInt))) {
            errorMessage += "The lhs and rhs of an arithmetic expression must be of type int" +
                    " line: "+this.getLineNumber() + " col: " +this.getColNumber() + "\n";
        }

        return errorMessage;
    }

    @Override
    public String prettyPrint(String indentSpace) {
        String prettyString = indentSpace + "|--binaryArithOper\n";

        // pretty print the lhs
        prettyString += "  " + indentSpace + "|--lhs\n";
        prettyString += this.leftOperand.prettyPrint("    " + indentSpace);

        // print the operator
        prettyString += "  " + indentSpace + "|--op: " + this.operation + "\n";

        // pretty print the rhs
        prettyString += "  " + indentSpace + "|--rhs\n";
        prettyString += this.rightOperand.prettyPrint("    " + indentSpace);

        return prettyString;
    }

    @Override
    public LlLocation generateLlIr(LlBuilder builder, LlSymbolTable symbolTable) {
        LlLocation leftTemp = this.leftOperand.generateLlIr(builder, symbolTable);
        LlLocation rightTemp = this.rightOperand.generateLlIr(builder, symbolTable);
        LlLocationVar returnTemp = builder.generateTemp();
        LlAssignStmtBinaryOp assignStmtBinaryOp = new LlAssignStmtBinaryOp(returnTemp, leftTemp, this.getOperation() ,rightTemp);
        builder.appendStatement(assignStmtBinaryOp);
        return returnTemp;
    }
}
