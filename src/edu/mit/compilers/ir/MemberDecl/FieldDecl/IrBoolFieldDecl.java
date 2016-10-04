package edu.mit.compilers.ir;

public class IrBoolFieldDecl extends IrFieldDecl {
    private final IrType type = IrType.BOOL;
    private final IrIdent varName;

    public IrBoolFieldDecl(IrIdent varName) {
        this.varName = varName;
    }

}