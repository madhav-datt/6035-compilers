package edu.mit.compilers.ir;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.Register;
import edu.mit.compilers.ScopeStack;
import edu.mit.compilers.StackFrame;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrStmtReturnVoid extends IrStmtReturn {
    public IrStmtReturnVoid(int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
    }

    @Override
    public IrType getExpressionType() {
        return new IrTypeVoid(this.getLineNumber(), this.getColNumber());
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        String errorMessage = "";

        // 1) check to make sure that the IrStmtReturn exists within a non-void method
        IrType methodType = scopeStack.getScopeReturnType();
        if (methodType != null) {

            if (!(methodType instanceof IrTypeVoid)) {
                errorMessage += "Returning void in a non-void method"+
                        " line: "+this.getLineNumber() + " col: " +this.getColNumber() + "\n";
            }
        }

        return errorMessage;
    }

    @Override
    public String prettyPrint(String indentSpace) {
        String prettyString = indentSpace + "|--returnVoid";
        return prettyString;
    }
}
