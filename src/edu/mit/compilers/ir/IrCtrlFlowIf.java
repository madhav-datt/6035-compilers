package edu.mit.compilers.ir;

import edu.mit.compilers.*;
import edu.mit.compilers.ll.*;

import java.util.ArrayList;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrCtrlFlowIf extends IrCtrlFlow {
    public IrCtrlFlowIf(IrExpr condExpr, IrCodeBlock stmtBody) {
        super(condExpr, stmtBody);
    }

    public IrExpr getIfCondition() {
        return this.condExpr;
    }

    public IrCodeBlock getIfBodyBlock() {
        return this.stmtBody;
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        String errorMessage = "";

        // create the scope for the if-statement
        scopeStack.createNewBlockScope();

        // 1) verify that the expression is valid
        errorMessage += this.condExpr.semanticCheck(scopeStack);

        // 2) make sure that the condition expression is IrTypeBool
        if (!(this.condExpr.getExpressionType() instanceof IrTypeBool)) {
            errorMessage += "Condition for if-statement must be a boolean" +
                    " line: "+this.getLineNumber() + " col: " +this.getColNumber() + "\n";
        }

        // 3) verify that the block is valid
        errorMessage += this.stmtBody.semanticCheck(scopeStack);

        // delete the current scope
        scopeStack.deleteCurrentScope();

        return errorMessage;
    }


    @Override
    public String prettyPrint(String indentSpace) {
        String prettyString = indentSpace + "|--ifStmt\n";

        // print the condition expr
        prettyString += ("  " + indentSpace + "|--condExpr\n");
        prettyString += (this.condExpr.prettyPrint("    " + indentSpace));

        // print the if loop body
        prettyString += "  " + indentSpace + "|--body\n";
        prettyString += this.stmtBody.prettyPrint("    " + indentSpace);

        return prettyString;
    }

    @Override
    public LlLocation generateLlIr(LlBuilder builder, LlSymbolTable symbolTable) {

        // condition goto(label)
        // if not go to end of the if block
        String ifBlockLabel = "IF_" + builder.generateLabel();
        String endIfLabel = "END_" + ifBlockLabel;

        // Generate the conditional statement.
        LlLocation conditionalTemp = this.condExpr.generateLlIr(builder, symbolTable);
        LlJumpConditional conditionalJump = new LlJumpConditional(ifBlockLabel, conditionalTemp);
        builder.appendStatement(conditionalJump);

        // if the conditional doesnt work, jump to the end of the if block.
        LlJumpUnconditional unconditionalJump = new LlJumpUnconditional(endIfLabel);
        builder.appendStatement(unconditionalJump);
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
