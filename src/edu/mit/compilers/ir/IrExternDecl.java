package edu.mit.compilers.ir;

class IrExternDecl extends Ir{
    private final IrIdent name;

    public IrExternDecl(IrIdent name) {
        this.name = name;
    }
}