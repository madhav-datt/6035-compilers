package edu.mit.compilers.ir;

import edu.mit.compilers.ScopeStack;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrCtrlFlowIfElse extends IrCtrlFlow{
    private final IrCodeBlock elseBlock;
    private final IrCtrlFlowIf ifStmt;

    public IrCtrlFlowIfElse(IrCtrlFlowIf ifStmt, IrCodeBlock elseBlock) {
        super(ifStmt.getIfCondition(), ifStmt.getIfBodyBlock());
        this.ifStmt = ifStmt;
        this.elseBlock = elseBlock;
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        String errorMessage = "";

        // 1) verify that the IrCtrlFlowIf stmt is valid
        errorMessage += this.ifStmt.semanticCheck(scopeStack);

        // 2) verify that the else-block is valid
        errorMessage += this.stmtBody.semanticCheck(scopeStack);

        return errorMessage;
    }
}
