package edu.mit.compilers.ir;

import edu.mit.compilers.ScopeStack;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrLocationVar extends IrLocation {
    public IrLocationVar(IrIdent varName, int lineNumber, int colNumber) {
        super(varName, lineNumber, colNumber);
    }

    @Override
    public IrType getExpressionType() {
        return this.varType;
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        String errorMessage = "";

        // 1) make sure the variable has been declared already
        if (scopeStack.checkIfSymbolExistsAtAnyScope(this.getLocationName().getValue())) {
            Ir object = scopeStack.getSymbol(this.getLocationName().getValue());

            // make sure that the identifier is a field, param, or array (but not a method)
            if (object instanceof IrFieldDeclVar) {
                IrFieldDecl var = (IrFieldDecl) object;

                // IMPORTANT: set the IrType of the IrLocationVar
                this.setLocationType(var.getType());
            }
            else if (object instanceof IrParamDecl) {
                IrParamDecl var = (IrParamDecl) object;

                // IMPORTANT: set the IrType of the IrLocationVar
                this.setLocationType(var.getParamType());
            }
            else {
                errorMessage += "Invalid method call or array assignment" +
                        " line: " + this.getLocationName().getLineNumber() + " col: " + this.getLocationName().getColNumber() + "\n";
            }
        }
        else {
            errorMessage += "Variable used before declared" +
                    " line: " + this.getLocationName().getLineNumber() + " col: " + this.getLocationName().getColNumber() + "\n";
        }

        return errorMessage;
    }
}
