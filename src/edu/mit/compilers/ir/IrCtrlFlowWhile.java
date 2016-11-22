package edu.mit.compilers.ir;

import edu.mit.compilers.*;
import edu.mit.compilers.ll.*;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrCtrlFlowWhile extends IrCtrlFlow{
    public IrCtrlFlowWhile(IrExpr condExpr, IrCodeBlock stmtBody) {
        super(condExpr, stmtBody);
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        String errorMessage = "";

        // create the scope for the while loop
        scopeStack.createNewLoopScope();

        // 1) verify that the expression is valid
        errorMessage += this.condExpr.semanticCheck(scopeStack);

        // 2) make sure that the condition expression is IrTypeBool
        if (!(this.condExpr.getExpressionType() instanceof IrTypeBool)) {
            errorMessage += "Condition for while-statement must be a boolean" +
                    " line: "+this.getLineNumber() + " col: " +this.getColNumber() + "\n";
        }

        // 3) verify that the while block is valid
        errorMessage += this.stmtBody.semanticCheck(scopeStack);

        // delete the scope for the while loop
        scopeStack.deleteCurrentScope();

        return errorMessage;
    }

    @Override
    public String prettyPrint(String indentSpace) {
        String prettyString = indentSpace + "|--whileLoop\n";

        // print the condition expr
        prettyString += ("  " + indentSpace + "|--condExpr\n");
        prettyString += (this.condExpr.prettyPrint("    " + indentSpace));

        // print the for loop body
        prettyString += "  " + indentSpace + "|--body\n";
        prettyString += this.stmtBody.prettyPrint("    " + indentSpace);

        return prettyString;
    }

    @Override
    public LlLocation generateLlIr(LlBuilder builder, LlSymbolTable symbolTable) {


        String loopCondition = "WHILE_COND_" + builder.generateLabel();
        String startLoopLabel = "WHILE_" + builder.generateLabel();

        LlEmptyStmt forConditionalLabelStmt = new LlEmptyStmt();

        String endLoopLabel = "END_" + startLoopLabel;

        builder.appendStatement(loopCondition, forConditionalLabelStmt);


        //generate the goto Statemennt
        LlLocation conditionResultTemp = this.condExpr.generateLlIr(builder, symbolTable);
        LlJumpConditional conditionalJump = new LlJumpConditional(startLoopLabel, conditionResultTemp);

        builder.appendStatement(conditionalJump);

        //if the conditional evaluates to false, jump to the end of the loop
        LlJumpUnconditional endOfForLoopJump = new LlJumpUnconditional(endLoopLabel);
        builder.appendStatement(endOfForLoopJump);

        // Else execute the for loop body.
        LlEmptyStmt forStart = new LlEmptyStmt();
        builder.appendStatement(startLoopLabel, forStart);

        // if the condition fails, jump to the end of the fotr loop
        LlEmptyStmt endForLabelEmptyStmt = new LlEmptyStmt();


        // get into block
        builder.getInBlock(loopCondition);
        // generate the code block Ll

        this.stmtBody.generateLlIr(builder, symbolTable);



        // update the counter.

        builder.appendStatement(new LlJumpUnconditional(loopCondition));


        LlEmptyStmt emptyStmt = new LlEmptyStmt();

        // now add the end of loop label
        builder.appendStatement(endLoopLabel, emptyStmt);

        return null;
    }
}
