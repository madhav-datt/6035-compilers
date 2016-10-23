package edu.mit.compilers.ir;

import edu.mit.compilers.ScopeStack;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrCtrlFlowFor extends IrCtrlFlow {
    private final IrLocationVar counter;
    private final IrAssignStmt compoundAssignStmt;

    public IrCtrlFlowFor(IrLocationVar counter, IrAssignStmt updateCounterStmt,
                      IrExpr condExpr, IrCodeBlock stmtBody) {
        super(condExpr, stmtBody);
        this.counter = counter;
        this.compoundAssignStmt = updateCounterStmt;
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        String errorMessage = "";

        // 1) verify the IrLocationVar
        errorMessage += this.counter.semanticCheck(scopeStack);

        // 2) verify that locationVar is not an array
        Ir object = scopeStack.getSymbol(this.counter.getLocationName().getValue());
        if (object instanceof IrFieldDeclArray) {
            errorMessage += "For loop index cannot be an array variable" +
                    " line: "+this.counter.getLineNumber() + " col: " +this.counter.getColNumber() + "\n";
        }

        // 3) verify that the condition is IrTypeBool
        if (!(this.condExpr.getExpressionType() instanceof IrTypeBool)) {
            errorMessage += "For loop condition does not evaluate to bool" +
                    " line: "+this.condExpr.getLineNumber() + " col: " +this.condExpr.getColNumber() + "\n";
        }

        // 4) verify the compoundAssignStmt
        errorMessage += this.compoundAssignStmt.semanticCheck(scopeStack);

        // 5) verify that the for-block is valid
        errorMessage += this.stmtBody.semanticCheck(scopeStack);

        return errorMessage;
    }
}
