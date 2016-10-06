package edu.mit.compilers.ir.MemberDecl.FieldDecl;

import edu.mit.compilers.ir.*;


public abstract class IrFieldDecl extends IrMemberDecl {
    private final IrType fieldType;

    public IrFieldDecl(IrType fieldType, IrIdent name, int lineNumber, int colNumber) {
        super(name, lineNumber, colNumber);
        this.fieldType = fieldType;
    }
}