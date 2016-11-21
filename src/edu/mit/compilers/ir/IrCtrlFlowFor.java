package edu.mit.compilers.ir;

import edu.mit.compilers.*;
import edu.mit.compilers.ll.*;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrCtrlFlowFor extends IrCtrlFlow {
    private final IrLocationVar counter;
    private final IrAssignStmt compoundAssignStmt;
    private final IrExpr intialIndexExpr;

    public IrCtrlFlowFor(IrLocationVar counter, IrExpr intialIndexerExpr, IrAssignStmt updateCounterStmt,
                      IrExpr condExpr, IrCodeBlock stmtBody) {
        super(condExpr, stmtBody);
        this.counter = counter;
        this.compoundAssignStmt = updateCounterStmt;
        this.intialIndexExpr = intialIndexerExpr;
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        String errorMessage = "";

        // create the scope for the loop
        scopeStack.createNewLoopScope();

        // 1) verify the IrLocationVar
        errorMessage += this.counter.semanticCheck(scopeStack);

        // 2) verify that locationVar is not an array
        Ir object = scopeStack.getSymbol(this.counter.getLocationName().getValue());
        if (object instanceof IrFieldDeclArray) {
            errorMessage += "For loop index cannot be an array variable" +
                    " line: "+this.counter.getLineNumber() + " col: " +this.counter.getColNumber() + "\n";
        }

        // 3) make sure that initialIndexExpr is semantically correct
        errorMessage += this.intialIndexExpr.semanticCheck(scopeStack);

        // 4) verify that the initialIndexExpr is IrTypeInt
        if (!(this.intialIndexExpr.getExpressionType() instanceof IrTypeInt)) {
            errorMessage += "Initial index value in for loop must be type int" +
                    " line: "+this.intialIndexExpr.getLineNumber() + " col: " +this.intialIndexExpr.getColNumber() + "\n";
        }

        // 5) verify that the condition is semantically correct
        errorMessage += this.condExpr.semanticCheck(scopeStack);

        // 3) verify that the condition is IrTypeBool
        if (!(this.condExpr.getExpressionType() instanceof IrTypeBool)) {
            errorMessage += "For loop condition does not evaluate to bool" +
                    " line: "+this.condExpr.getLineNumber() + " col: " +this.condExpr.getColNumber() + "\n";
        }

        // 4) verify the compoundAssignStmt
        errorMessage += this.compoundAssignStmt.semanticCheck(scopeStack);

        // 5) verify that the for-block is valid
        errorMessage += this.stmtBody.semanticCheck(scopeStack);

        // delete the current loop scope
        scopeStack.deleteCurrentScope();

        return errorMessage;
    }


    @Override
    public String prettyPrint(String indentSpace) {
        String prettyString = indentSpace + "|--forLoop\n";

        // print the counter initialize stmt
        prettyString += ("  " + indentSpace + "|--counter\n");
        prettyString += (this.counter.prettyPrint("    " + indentSpace));
        prettyString += ("    " + indentSpace + "|--=\n");
        prettyString += (this.intialIndexExpr.prettyPrint("    " + indentSpace));

        // print the condition expr
        prettyString += ("  " + indentSpace + "|--condExpr\n");
        prettyString += (this.condExpr.prettyPrint("    " + indentSpace));

        // print the compound assign stmt
        prettyString += ("  " + indentSpace + "|--compoundExpr\n");
        prettyString += (this.compoundAssignStmt.prettyPrint("    " + indentSpace));

        // print the for loop body
        prettyString += "  " + indentSpace + "|--body\n";
        prettyString += this.stmtBody.prettyPrint("    " + indentSpace);

        return prettyString;
    }

    @Override
    public LlLocation generateLlIr(LlBuilder builder, LlSymbolTable symbolTable) {
        LlLocation initialIndexTemp = this.intialIndexExpr.generateLlIr(builder, symbolTable);

        // generate the initial assignment statement
        LlAssignStmtRegular initialIndexAssignmentStatement = new LlAssignStmtRegular(new LlLocationVar(this.counter.getLocationName().toString()), initialIndexTemp);
        String loopCondition = "FOR_COND_" + builder.generateLabel();
        String startLoopLabel = "FOR_" + builder.generateLabel();

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
        builder.putInPocket(this.compoundAssignStmt);
        this.stmtBody.generateLlIr(builder, symbolTable);
        builder.emptyPocket();


        // update the counter.
        LlLocation lastTempUsed = this.compoundAssignStmt.generateLlIr(builder, symbolTable);
//        jump to the start of the loop
        builder.appendStatement(new LlJumpUnconditional(loopCondition));


        LlEmptyStmt emptyStmt = new LlEmptyStmt();

        // now add the end of loop label
        builder.appendStatement(endLoopLabel, emptyStmt);

        return lastTempUsed;

    }
}
