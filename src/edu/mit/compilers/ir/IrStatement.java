package edu.mit.compilers.ir;

/**
 * Created by devinmorgan on 10/5/16.
 */
public abstract class IrStatement extends Ir {
    public IrStatement(int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
    }
}
