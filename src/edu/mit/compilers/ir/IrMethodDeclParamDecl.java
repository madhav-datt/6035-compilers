package edu.mit.compilers.ir.MemberDecl.MethodDecl;

import edu.mit.compilers.ir.Type;

public class IrMethodDeclParamDecl extends Ir {
    private final IrType paramType;
    private final IrIdent paramName;

    public IrMethodDeclParamDecl(IrType paramType, IrIdent paramName, int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.paramType = paramType;
        this.paramName = paramName;
    }
}