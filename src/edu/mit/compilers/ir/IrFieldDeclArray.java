package edu.mit.compilers.ir.MemberDecl.FieldDecl;

import edu.mit.compilers.ir.*;


public class IrFieldDeclArray extends IrFieldDecl {
    private final int arraySize;

    public IrFieldDeclArray(int arraySize, IrType fieldType, IrIdent name, int lineNumber, int colNumber) {
        super(fieldType, name, lineNumber, colNumber);
        this.arraySize = arraySize;
    }

}