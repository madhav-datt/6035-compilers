package edu.mit.compilers.ir;

import edu.mit.compilers.ScopeStack;

public class IrSizeOfLocation extends IrSizeOf {
    private final IrFieldDecl field;

    public IrSizeOfLocation(IrFieldDecl field, int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.field = field;
    }

    @Override
    public IrType getExpressionType() {
        return new IrTypeInt(this.field.getLineNumber(), this.field.getColNumber());
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        String errorMessage = "";

        // 1) make sure the variable has been declared already
        if (!scopeStack.checkIfSymbolExistsAtAnyScope(this.field.getName())) {
            errorMessage += "Argument in sizeof hasn't been declared" +
                    " line: " + this.field.getLineNumber() + "col: " + this.field.getColNumber() + "\n";
        }

        return errorMessage;
    }
}