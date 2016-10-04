package edu.mit.compilers.ir;

public class IrArrayFieldDecl extends IrFieldDecl {
    private final int arraySize;

    public IrArrayFieldDecl(int arraySize, IrType fieldType, IrIdent fieldName, int lineNumber, int colNumber) {
        super(fieldType, fieldName, lineNumber, colNumber);
        this.arraySize = arraySize;
    }

}