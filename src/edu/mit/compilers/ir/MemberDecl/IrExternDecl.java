package edu.mit.compilers.ir;

public class IrExternDecl extends IrMemberDecl {
    private final IrIdent name;

    public IrExternDecl(IrIdent name) {
        this.name = name;
    }
}