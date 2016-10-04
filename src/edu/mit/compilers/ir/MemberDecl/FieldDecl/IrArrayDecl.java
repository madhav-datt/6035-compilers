package edu.mit.compilers.ir;

public class IrArrayDecl extends IrFieldDecl {
    private final IrType arrayType;
    private final IrIdent varName;

    public IrIntDecl(IrIdent varName, IrType arrayType) {
        this.varName = varName;
        this.arrayType = arrayType;
    }

}