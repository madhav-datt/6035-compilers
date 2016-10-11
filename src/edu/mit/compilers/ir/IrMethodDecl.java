package edu.mit.compilers.ir;

import java.util.*;

public class IrMethodDecl extends IrMemberDecl {
    private final IrTypeVar returnType;
    private final List<IrMethodParamDecl> paramsList;
    private final IrCodeBlock methodBody;

    public IrMethodDecl(IrTypeVar returnType, List<IrMethodParamDecl> paramsList,
                        IrCodeBlock methodBody, IrIdent name) {
        super(name, name.getLineNumber(), name.getColNumber());
        this.returnType = returnType;
        this.paramsList = paramsList;
        this.methodBody = methodBody;
    }
}