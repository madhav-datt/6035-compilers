package edu.mit.compilers.ir;

public class IrExternDecl extends IrMemberDecl {
    public IrExternDecl(IrIdent name, int lineNumber, int colNumber) {
        super(name, lineNumber, colNumber);
    }
}