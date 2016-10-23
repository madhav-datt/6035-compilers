package edu.mit.compilers.ir;

import edu.mit.compilers.ScopeStack;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrLocationVar extends IrLocation {
    public IrLocationVar(IrIdent varName, IrType varType, int lineNumber, int colNumber) {
        super(varName, varType,lineNumber, colNumber);
    }

    @Override
    public IrType getExpressionType() {
        return this.varType;
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        String errorMessage = "";

        // 1) make sure the variable has been declared already
        if (!scopeStack.checkIfSymbolExistsAtAnyScope(this.varName.getValue())) {
            errorMessage += "Variable used before declared" +
                    " line: " + this.varName.getLineNumber() + "col: " + this.varName.getColNumber() + "\n";
        }

        return errorMessage;
    }
}
