package edu.mit.compilers.ir;

import edu.mit.compilers.ScopeStack;

public class IrExternDecl extends IrMemberDecl {
    public IrExternDecl(IrIdent name) {
        super(name, new IrTypeInt(name.getLineNumber(), name.getColNumber()), name.getColNumber(), name.getColNumber());
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        return "";
    }
}