package edu.mit.compilers.ir;

public abstract class IrFieldDecl extends IrMemberDecl {
    private final abstract IrType fieldType;
    private final abstract IrIdent fieldName;

    public IrFieldDecl(IrType fieldType, IrIdent fieldName, int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.fieldType = fieldType;
        this.fieldType = fieldName;
    }
}