package edu.mit.compilers.ir;

public class IrBoolFieldDecl extends IrFieldDecl {
    public IrBoolFieldDecl(IrType fieldType, IrIdent fieldName, int lineNumber, int colNumber) {
        super(fieldType, fieldName, lineNumber, colNumber);
    }
}