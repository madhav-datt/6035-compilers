package edu.mit.compilers.ir;

public abstract class IrFieldDecl extends IrMemberDecl {
    private final IrType fieldType;
    private final IrIdent fieldName;

    public IrFieldDecl(IrType fieldType, IrIdent fieldName, int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.fieldType = fieldType;
        this.fieldName = fieldName;
    }
}