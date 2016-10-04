package edu.mit.compilers.ir;

public class IrArrayFieldDecl extends IrFieldDecl {
    private final IrIdent varName;
    private final IrType arrayType;
    private final int arraySize;

    public IrArrayFieldDecl(IrIdent varName, IrType arrayType, int arraySize) {
        this.varName = varName;
        this.arrayType = arrayType;
        this.arraySize = arraySize;
    }

}