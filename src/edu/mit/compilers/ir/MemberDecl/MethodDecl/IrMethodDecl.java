package edu.mit.compilers.ir;
import java.util.*;

public abstract class IrMethodDecl extends IrMemberDecl {
    private final IrType returnType;
    private final IrIdent methodName;
    private final List<IrParamDecl> parameters;
    private final IrCodeBlock methodBody;

    public IrMethodDecl(IrType returnType, IrIdent methodName, List<IrParamDecl> parameters,
                       IrCodeBlock methodBody, int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.returnType = returnType;
        this.methodName = methodName;
        this.parameters = parameters;
        this.methodBody = methodBody;
    }
}