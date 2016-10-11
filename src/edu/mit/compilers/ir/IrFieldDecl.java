package edu.mit.compilers.ir;

public abstract class IrFieldDecl extends IrMemberDecl {
    private IrTypeVar fieldType;

    public IrFieldDecl(IrIdent name) {
        super(name, name.getLineNumber(), name.getColNumber());
    }

    public void setType(IrTypeVar type) {
        this.fieldType = type;
    }
}