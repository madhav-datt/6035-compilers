package edu.mit.compilers.ir;

import edu.mit.compilers.*;
import edu.mit.compilers.ll.LlJumpUnconditional;
import edu.mit.compilers.ll.LlLocation;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrStmtContinue extends IrStatement {
    public IrStmtContinue(int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        String errorMessage = "";

        // 1) check to see if this statement resides in a loop scope or not
        boolean isInALoop = scopeStack.isScopeForALoop();
        if (!isInALoop) {
            errorMessage += "Continue statement cannot be used outside of a for or while loop" +
                    " line: "+this.getLineNumber() + " col: " +this.getColNumber() + "\n";
        }

        return errorMessage;
    }

    @Override
    public String prettyPrint(String indentSpace) {
        String prettyString = indentSpace + "|--continueStmt";
        return prettyString;
    }

    @Override
    public LlLocation generateLlIr(LlBuilder builder, LlSymbolTable symbolTable) {

        String endBlock = builder.getCurrentBlock();
        LlJumpUnconditional unconditionalJump = new LlJumpUnconditional(endBlock);
        builder.appendStatement(unconditionalJump);
        return null;
    }
}
