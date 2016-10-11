package edu.mit.compilers.ir;

public class IrExternDecl extends IrMemberDecl {

    public IrExternDecl(IrIdent name) {
        super(name, name.getLineNumber(), name.getColNumber());
    }
}