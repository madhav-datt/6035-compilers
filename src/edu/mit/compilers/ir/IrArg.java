package edu.mit.compilers.ir;

/**
 * Created by devinmorgan on 10/16/16.
 */
public abstract class IrArg extends Ir{
    private final Object argValue;

    public IrArg(Object argValue, int lineNum, int colNum) {
        super(lineNum, colNum);

        // we would like to canonicalize the expression if possible
        if (argValue instanceof IrExpr) {
            IrExpr expr = (IrExpr) argValue;
            this.argValue = IrExpr.canonicalizeExpr(expr);
        }
        else {
            this.argValue = argValue;
        }
    }

    public Object getArgValue() {
        return argValue;
    }

}
