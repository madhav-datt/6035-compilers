package edu.mit.compilers.ir;

public class IrArrayFieldDecl extends IrFieldDecl {
    private final int arraySize;

    public IrArrayFieldDecl(int arraySize, IrType fieldType, IrIdent name, int lineNumber, int colNumber) {
        super(fieldType, name, lineNumber, colNumber);
        this.arraySize = arraySize;
    }

}