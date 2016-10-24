package edu.mit.compilers.ir;

public class IrFieldDeclArray extends IrFieldDecl {
    private final int arraySize;

    public IrFieldDeclArray(int arraySize, IrIdent name, IrType fieldType) {
        super(name, fieldType);
        this.arraySize = arraySize;
    }

    public int getArraySize(){
        return this.arraySize;
    }

}