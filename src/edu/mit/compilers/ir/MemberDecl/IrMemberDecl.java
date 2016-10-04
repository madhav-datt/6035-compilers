package edu.mit.compilers.ir;

public abstract class IrMemberDecl extends Ir {
    public IrMemberDecl(int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
    }
}