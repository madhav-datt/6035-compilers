package edu.mit.compilers.ir;

import edu.mit.compilers.ScopeStack;

/**
 * Created by devinmorgan on 10/11/16.
 */
public class IrFieldDeclVar extends IrFieldDecl {
    public IrFieldDeclVar(IrIdent fieldName, IrType fieldType) {
        super(fieldName, fieldType);
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        return "";
    }
}
