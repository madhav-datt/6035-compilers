package edu.mit.compilers.ir.MethodCall.MethodArg;

import edu.mit.compilers.ir.*;

public class IrArgString extends IrArg{
    private final String content;

    public IrArgString(String content, int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.content = content;
    }
}