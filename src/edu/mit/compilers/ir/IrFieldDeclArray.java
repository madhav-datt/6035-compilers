package edu.mit.compilers.ir;

public class IrFieldDeclArray extends IrFieldDecl {
    private final int arraySize;

    public IrFieldDeclArray(int arraySize, IrIdent name) {
        super(name);
        this.arraySize = arraySize;
    }

}