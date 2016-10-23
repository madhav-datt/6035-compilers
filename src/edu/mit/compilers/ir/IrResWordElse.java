package edu.mit.compilers.ir;

import edu.mit.compilers.ScopeStack;

/**
 * Created by devinmorgan on 10/22/16.
 */
public class IrResWordElse extends Ir {
    public IrResWordElse(int lineNum, int colNum) {
        super(lineNum, colNum);
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        return "";
    }
}
