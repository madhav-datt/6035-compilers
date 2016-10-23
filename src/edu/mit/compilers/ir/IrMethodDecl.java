package edu.mit.compilers.ir;

import edu.mit.compilers.ScopeStack;

import java.util.*;

public class IrMethodDecl extends IrMemberDecl {
    private final List<IrParamDecl> paramsList;
    private final IrCodeBlock methodBody;

    public IrMethodDecl(IrType returnType, List<IrParamDecl> paramsList,
                        IrCodeBlock methodBody, IrIdent name) {
        super(name, returnType, name.getLineNumber(), name.getColNumber());
        this.paramsList = paramsList;
        this.methodBody = methodBody;
    }

    public List<IrParamDecl> getParamsList() {
        return new ArrayList<>(this.paramsList);
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        return null;
    }
}