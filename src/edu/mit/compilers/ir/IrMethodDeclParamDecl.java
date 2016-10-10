package edu.mit.compilers.ir;

public class IrMethodDeclParamDecl extends Ir {
    private final IrType paramType;
    private final IrIdent paramName;

    public IrMethodDeclParamDecl(IrType paramType, IrIdent paramName, int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.paramType = paramType;
        this.paramName = paramName;
    }
    public String toString(){
        return paramType.toString() + " " + paramName.toString();
    }
}