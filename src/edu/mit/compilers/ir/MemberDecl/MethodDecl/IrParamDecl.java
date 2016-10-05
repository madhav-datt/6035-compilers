package edu.mit.compilers.ir;

public class IrParamDecl extends Ir {
    private final IrType paramType;
    private final IrIdent paramName;

    public IrParamDecl(IrType paramType, IrIdent paramName, int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.paramType = paramType;
        this.paramName = paramName;
    }
}