package edu.mit.compilers.ir;

public class IrExternDecl extends IrMemberDecl {
    public IrExternDecl(IrIdent name) {
        super(name, new IrTypeInt(name.getLineNumber(), name.getColNumber()), name.getColNumber(), name.getColNumber());
    }
}