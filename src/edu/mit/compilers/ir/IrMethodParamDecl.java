package edu.mit.compilers.ir;

public class IrMethodParamDecl extends Ir {
    private final IrTypeVar paramType;
    private final IrIdent paramName;

    public IrMethodParamDecl(IrTypeVar paramType, IrIdent paramName) {
        super(paramName.getLineNumber(), paramName.getColNumber());
        this.paramType = paramType;
        this.paramName = paramName;
    }
    public String toString(){
        return paramType.toString() + " " + paramName.toString();
    }
}