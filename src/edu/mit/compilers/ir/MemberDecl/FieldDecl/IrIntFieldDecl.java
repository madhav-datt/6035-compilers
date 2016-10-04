package edu.mit.compilers.ir;

public class IrIntFieldDecl extends IrFieldDecl {
    public IrIntFieldDecl(IrType fieldType, IrIdent fieldName, int lineNumber, int colNumber) {
        super(fieldType, fieldName, lineNumber, colNumber);
    }
}