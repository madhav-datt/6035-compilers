package edu.mit.compilers.ir;

import edu.mit.compilers.ScopeStack;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrLocationArray extends IrLocation {
    private final IrExpr elementIndex;

    public IrLocationArray(IrExpr elementIndex, IrIdent varName, IrType varType, int lineNumber, int colNumber) {
        super(varName, varType, lineNumber, colNumber);
        this.elementIndex = elementIndex;
    }

    @Override
    public IrType getExpressionType() {
        return this.varType;
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        String errorMessage = "";

        // 1) verify that the IrExpr is semantically correct
        errorMessage += this.elementIndex.semanticCheck(scopeStack);

        // 2) make sure the array has been declared already
        if (scopeStack.checkIfSymbolExistsAtAnyScope(this.getLocationName().getValue())) {

            // make sure that var is an array
            Ir object = scopeStack.getSymbol(this.varName.getValue());
            if (!(object instanceof IrFieldDeclArray)) {
                errorMessage += "Non-array variable be accessed as an array" +
                        " line: " + this.elementIndex.getLineNumber() + " col: " + this.elementIndex.getColNumber() + "\n";
            }
        }
        else {
            errorMessage += "Array variable used before declared" +
                    " line: " + this.elementIndex.getLineNumber() + " col: " + this.elementIndex.getColNumber() + "\n";
        }

        // 3) make sure that the IrExpr offset is an IrTypeInt
        if (!(elementIndex.getExpressionType() instanceof IrTypeInt)) {
            errorMessage += "Element offset must be of type int" +
                    " line: " + this.elementIndex.getLineNumber() + " col: " + this.elementIndex.getColNumber() + "\n";
        }

        return errorMessage;
    }
}
