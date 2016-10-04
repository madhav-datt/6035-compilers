package edu.mit.compilers.ir;

public class IrIntFieldDecl extends IrFieldDecl {
    private final IrType type = IrType.INT;
    private final IrIdent varName;

    public IrIntFieldDecl(IrIdent varName) {
        this.varName = varName;
    }

}