package edu.mit.compilers.ir;

import edu.mit.compilers.ScopeStack;

public class IrSizeOfLocation extends IrSizeOf {
    private final IrIdent fieldName;

    public IrSizeOfLocation(IrIdent fieldName, int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.fieldName = fieldName;
    }

    @Override
    public IrType getExpressionType() {
        return new IrTypeInt(this.fieldName.getLineNumber(), this.fieldName.getColNumber());
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        String errorMessage = "";

        // 1) make sure the variable has been declared already
        if (scopeStack.checkIfSymbolExistsAtAnyScope(this.fieldName.getValue())) {
            Ir object = scopeStack.getSymbol(this.fieldName.getValue());

            // make sure that argument is a var (and not a method)
            if (!(object instanceof IrFieldDecl)) {
                errorMessage += "Argument for sizeof is not a type, variable, or array" +
                        " line: " + this.fieldName.getLineNumber() + " col: " + this.fieldName.getColNumber() + "\n";
            }
        }
        else {
            errorMessage += "Argument in sizeof hasn't been declared" +
                    " line: " + this.fieldName.getLineNumber() + " col: " + this.fieldName.getColNumber() + "\n";
        }

        return errorMessage;
    }
}