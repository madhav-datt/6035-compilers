package edu.mit.compilers.ir;
import java.util.*;

public abstract class IrMethodDecl extends IrMemberDecl {
    private final IrType returnType;
    private final List<IrParamDecl> paramsList;
    private final IrCodeBlock methodBody;

    public IrMethodDecl(IrType returnType, List<IrParamDecl> paramsList,
                       IrCodeBlock methodBody, IrIdent name, int lineNumber, int colNumber) {
        super(name, lineNumber, colNumber);
        this.returnType = returnType;
        this.paramsList = paramsList;
        this.methodBody = methodBody;
    }
}