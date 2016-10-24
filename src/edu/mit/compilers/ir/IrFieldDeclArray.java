package edu.mit.compilers.ir;

import edu.mit.compilers.ScopeStack;

public class IrFieldDeclArray extends IrFieldDecl {
    private final int arraySize;

    public IrFieldDeclArray(int arraySize, IrIdent name, IrType fieldType) {
        super(name, fieldType);
        this.arraySize = arraySize;
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        String errorMessage = "";

        // make sure that the array size is greater than 0
        if (arraySize <= 0) {
            errorMessage = "Array size must be a non-zero positive integer" +
                    " line: " + this.getLineNumber() + " col: " + this.getColNumber();
        }

        return errorMessage;
    }
}