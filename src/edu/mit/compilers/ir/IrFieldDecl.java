package edu.mit.compilers.ir;

public abstract class IrFieldDecl extends IrMemberDecl {
    private IrType fieldType;

    public IrFieldDecl(IrIdent name) {
        super(name, name.getLineNumber(), name.getColNumber());
    }

    public void setType(IrType type) {
        this.fieldType = type;
    }
}