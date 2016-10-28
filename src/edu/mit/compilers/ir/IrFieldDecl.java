package edu.mit.compilers.ir;

public abstract class IrFieldDecl extends IrMemberDecl {
    public IrFieldDecl(IrIdent name, IrType fieldType) {
        super(name, fieldType, name.getLineNumber(), name.getColNumber());
    }

}