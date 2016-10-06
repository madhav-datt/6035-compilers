package edu.mit.compilers.ir.MethodCall.MethodArg;

import edu.mit.compilers.ir.*;

public abstract class IrArg extends Ir {
    public IrArg(int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
    }
}