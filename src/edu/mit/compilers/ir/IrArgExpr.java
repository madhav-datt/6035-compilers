package edu.mit.compilers.ir;

import edu.mit.compilers.*;
import edu.mit.compilers.ll.LlLocation;

/**
 * Created by devinmorgan on 10/16/16.
 */
public class IrArgExpr extends IrArg {
    public IrArgExpr(IrExpr expr, int lineNum, int colNum) {
        super(expr, lineNum, colNum);
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        String errorMessage = ((IrExpr) this.getArgValue()).semanticCheck(scopeStack);

        return errorMessage;
    }

    public IrType getArgumentType() {
        return ((IrExpr) this.getArgValue()).getExpressionType();
    }

    //argument Expressions can be equivalent to expressions. Such as locations and method calls.
    @Override
    public boolean equals(Object that) {
        if (that == this) {
            return true;
        }
        if ((that instanceof IrExpr)) {
            return ((IrExpr) this.getArgValue()).equals((IrExpr)that);
        }
        if ((that instanceof IrArgExpr)) {
            Ir otherIr = (IrArgExpr)that;
            return ((IrExpr) this.getArgValue()).equals((IrExpr)((IrArgExpr)that).getArgValue());
        }
        return false;
    }

    @Override
    public int hashCode() {
        // if the argValue is an IrLocation, then
        // hash it according to its locationName
        if (this.getArgValue() instanceof IrLocation) {
            IrLocation argVar= (IrLocation) this.getArgValue();
            return argVar.getLocationName().hashCode();
        }

        return this.getArgValue().hashCode();
    }

    @Override
    public String prettyPrint(String indentSpace) {
        String prettyString = indentSpace + "|--argExpr:\n";

        // pretty print the expression
        prettyString += ((IrExpr) this.getArgValue()).prettyPrint("  " + indentSpace);

        return prettyString;
    }

    @Override
    public LlLocation generateLlIr(LlBuilder builder, LlSymbolTable symbolTable) {
        LlLocation tempVar = ((IrExpr) this.getArgValue()).generateLlIr(builder, symbolTable);
        return tempVar;
    }
}
