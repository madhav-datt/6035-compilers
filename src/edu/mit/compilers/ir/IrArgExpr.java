package edu.mit.compilers.ir;

import edu.mit.compilers.ScopeStack;

/**
 * Created by devinmorgan on 10/16/16.
 */
public class IrArgExpr extends IrArg {
    public IrArgExpr(IrExpr expr, int lineNum, int colNum) {
        super(expr, lineNum, colNum);
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        String errorMessage = ((IrExpr) this.getArgValue()).semanticCheck(scopeStack);

        return errorMessage;
    }
}
