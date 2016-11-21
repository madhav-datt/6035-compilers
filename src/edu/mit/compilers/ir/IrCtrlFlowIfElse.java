package edu.mit.compilers.ir;

import edu.mit.compilers.*;
import edu.mit.compilers.ll.*;

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


        // create the scope for the else block only
        scopeStack.createNewBlockScope();

        // 2) verify that the else-block is valid
        errorMessage += this.stmtBody.semanticCheck(scopeStack);

        // delete the else block scope
        scopeStack.deleteCurrentScope();

        return errorMessage;
    }


    @Override
    public String prettyPrint(String indentSpace) {
        String prettyString = "";

        // print the if statement
        prettyString += this.ifStmt.prettyPrint(indentSpace);

        // print the else body
        prettyString += indentSpace + "|--elseBody\n";
        prettyString += this.stmtBody.prettyPrint("  " + indentSpace);

        return prettyString;
    }
    // TODO: Check
    @Override
    public LlLocation generateLlIr(LlBuilder builder, LlSymbolTable symbolTable) {
        // condition goto(label)
        // if not go to end of the if block
        String ifBlockLabel = "IF_" + builder.generateLabel();
        String endIfLabel = "END_" + ifBlockLabel;

        String elseBlockLabel = "ELSE_IF_" + builder.generateLabel();

        // Generate the conditional statement.
        LlLocation conditionalTemp = this.condExpr.generateLlIr(builder, symbolTable);
        LlJumpConditional conditionalJump = new LlJumpConditional(ifBlockLabel, conditionalTemp);
        builder.appendStatement(conditionalJump);

        LlEmptyStmt elseLabelEmptyStmt = new LlEmptyStmt();
        LlJumpUnconditional unconditionalJumpToElse = new LlJumpUnconditional(elseBlockLabel);
        builder.appendStatement(unconditionalJumpToElse);
        builder.appendStatement(elseBlockLabel, elseLabelEmptyStmt);
        this.elseBlock.generateLlIr(builder, symbolTable);

        // after the else block is executed, jump to the end of the if block.
        LlJumpUnconditional unconditionalJump = new LlJumpUnconditional(endIfLabel);

        // add the label to the if body block
        LlEmptyStmt emptyStmt = new LlEmptyStmt();
        builder.appendStatement(ifBlockLabel, emptyStmt);

        //  finally generate the if statement body itself
        this.stmtBody.generateLlIr(builder, symbolTable);

        // append end if label
        LlEmptyStmt endIfEmptyStmt = new LlEmptyStmt();
        builder.appendStatement(endIfLabel, endIfEmptyStmt);

        // return the last known ...
        return null;
    }
}
