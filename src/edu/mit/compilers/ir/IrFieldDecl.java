package edu.mit.compilers.ir;

public abstract class IrFieldDecl extends IrMemberDecl {
    private final IrType fieldType;

    public IrFieldDecl(IrType fieldType, IrIdent name) {
        super(name, name.getLineNumber(), name.getColNumber());
        this.fieldType = fieldType;
    }
}