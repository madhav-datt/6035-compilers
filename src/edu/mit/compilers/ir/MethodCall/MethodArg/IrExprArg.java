package edu.mit.compilers.ir;

public class IrExprArg extends IrArg {
    private final IrExpr content;

    public IrExprArg(IrExpr content, int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.content = content;
    }
}