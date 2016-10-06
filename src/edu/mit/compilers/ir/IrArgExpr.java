package edu.mit.compilers.ir.MethodCall.MethodArg;

import edu.mit.compilers.ir.*;

public class IrArgExpr extends IrArg {
    private final IrExpr content;

    public IrArgExpr(IrExpr content, int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.content = content;
    }
}