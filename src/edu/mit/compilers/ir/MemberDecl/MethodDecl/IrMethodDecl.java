package edu.mit.compilers.ir;
import java.util.*;

public abstract class IrMethodDecl extends IrMemberDecl {
    private final IrType methodType;
    private final IrIdent methodName;
    private final List<IrParamDecl> parameters;
    // TODO: implement methodBody parameter here!!
//    private final IrBlock methodBody;

    public IrMethodDecl(IrType methodType, IrIdent methodName, List<IrParamDecl> parameters,
                       int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.methodType = methodType;
        this.methodName = methodName;
        this.parameters = parameters;
    }
}