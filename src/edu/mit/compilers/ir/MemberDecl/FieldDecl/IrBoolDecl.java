package edu.mit.compilers.ir;

public class IrBoolDecl extends IrFieldDecl {
    private final IrType type = IrType.BOOL;
    private final IrIdent varName;

    public IrBoolDecl(IrIdent varName) {
        this.varName = varName;
    }

}