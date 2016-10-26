package edu.mit.compilers.ir;

import edu.mit.compilers.ScopeStack;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrLocationArray extends IrLocation {
    private final IrExpr elementIndex;

    public IrLocationArray(IrExpr elementIndex, IrIdent varName, int lineNumber, int colNumber) {
        super(varName, lineNumber, colNumber);
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

            // 3) make sure that var is an array (and not a method or non-array)
            Ir object = scopeStack.getSymbol(this.getLocationName().getValue());
            if (!(object instanceof IrFieldDeclArray)) {
                errorMessage += "Non-array variable be accessed as an array" +
                        " line: " + this.elementIndex.getLineNumber() + " col: " + this.elementIndex.getColNumber() + "\n";
            }
            else {
                IrFieldDeclArray array = (IrFieldDeclArray) object;

                // IMPORTANT: set the IrType of the IrLocationArray
                this.setLocationType(array.getType());
            }
        }
        else {
            errorMessage += "Array variable used before declared" +
                    " line: " + this.elementIndex.getLineNumber() + " col: " + this.elementIndex.getColNumber() + "\n";
        }

        // 4) make sure that the IrExpr offset is an IrTypeInt
        if (!(this.elementIndex.getExpressionType() instanceof IrTypeInt)) {
            errorMessage += "Element offset must be of type int" +
                    " line: " + this.elementIndex.getLineNumber() + " col: " + this.elementIndex.getColNumber() + "\n";
        }

        return errorMessage;
    }
}
