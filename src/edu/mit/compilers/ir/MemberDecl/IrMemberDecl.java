package edu.mit.compilers.ir;

public abstract class IrMemberDecl extends Ir {
    private final IrIdent name;

    public IrMemberDecl(IrIdent name, int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.name = name;
    }
}