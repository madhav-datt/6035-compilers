package edu.mit.compilers.ir;

/**
 * Created by devinmorgan on 10/16/16.
 */
public abstract class IrArg extends Ir{
    private final Object argValue;

    public IrArg(Object argValue, int lineNum, int colNum) {
        super(lineNum, colNum);
        this.argValue = argValue;
    }

    public Object getArgValue() {
        return argValue;
    }

}
