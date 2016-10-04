package edu.mit.compilers.ir;

public class IrIntDecl extends IrFieldDecl {
    private final IrType type = IrType.INT;
    private final IrIdent varName;

    public IrIntDecl(IrIdent varName) {
        this.varName = varName;
    }

}