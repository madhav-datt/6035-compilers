package edu.mit.compilers.ir;

public class IrExternDecl extends IrMemberDecl {
    private final IrIdent name;

    public IrExternDecl(IrIdent name, int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.name = name;
    }
}