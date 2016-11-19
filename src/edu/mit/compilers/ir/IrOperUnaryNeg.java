package edu.mit.compilers.ir;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.Register;
import edu.mit.compilers.ScopeStack;
import edu.mit.compilers.StackFrame;

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

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        String errorMessage = "";

        // 1) check that the operand is valid
        errorMessage += this.operand.semanticCheck(scopeStack);

        // 2) verify that the operand is IrTypeInt
        if (!(this.operand.getExpressionType() instanceof IrTypeInt)) {
            errorMessage += "The negation '-' operand must be used on an int" +
                    " line: "+this.getLineNumber() + " col: " +this.getColNumber() + "\n";
        }

        return errorMessage;
    }


    @Override
    public String prettyPrint(String indentSpace) {
        String prettyString = indentSpace + "|--unaryNegateOp\n";

        // pretty print the operand
        prettyString += this.operand.prettyPrint("  " + indentSpace);

        return prettyString;
    }
}
