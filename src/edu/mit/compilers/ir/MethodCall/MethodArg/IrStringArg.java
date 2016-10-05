package edu.mit.compilers.ir;

public class IrStringArg extends IrArg{
    private final String content;

    public IrStringArg(String content, int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.content = content;
    }
}