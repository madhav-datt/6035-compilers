package edu.mit.compilers.ir;

public class IrArgExpr extends IrArg {
    private final IrExpr content;

    public IrArgExpr(IrExpr content) {
        super(content.getLineNumber(), content.getColNumber());
        this.content = content;
    }
}