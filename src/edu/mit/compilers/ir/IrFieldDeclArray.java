package edu.mit.compilers.ir;

public class IrFieldDeclArray extends IrFieldDecl {
    private final int arraySize;

    public IrFieldDeclArray(int arraySize, IrType fieldType, IrIdent name) {
        super(fieldType, name);
        this.arraySize = arraySize;
    }

}