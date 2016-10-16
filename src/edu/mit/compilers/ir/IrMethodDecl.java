package edu.mit.compilers.ir;

import java.util.*;

public class IrMethodDecl extends IrMemberDecl {
    private final List<IrMethodParamDecl> paramsList;
    private final IrCodeBlock methodBody;

    public IrMethodDecl(IrType returnType, List<IrMethodParamDecl> paramsList,
                        IrCodeBlock methodBody, IrIdent name) {
        super(name, returnType, name.getLineNumber(), name.getColNumber());
        this.paramsList = paramsList;
        this.methodBody = methodBody;
    }
}