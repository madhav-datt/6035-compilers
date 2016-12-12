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

        // short-circuit if the expr is a BinaryArith
        if (this.condExpr instanceof IrOperBinaryCond) {
            IrOperBinaryCond binaryOp = (IrOperBinaryCond) this.condExpr;
            IrExpr op1 = binaryOp.getLeftOperand();
            IrExpr op2 = binaryOp.getRightOperand();

            if (binaryOp.getOperation().equals("&&")) {

                // if (op2) --> this.stmtBody
                IrCtrlFlowIf op2If = new IrCtrlFlowIf(op2, this.stmtBody);

                // else --> do nothing
                IrCodeBlock emptyBlock = new IrCodeBlock(new ArrayList<>(), new ArrayList<>(), this.getLineNumber(), this.getColNumber());
                IrCtrlFlowIfElse op2Else = new IrCtrlFlowIfElse(op2If, emptyBlock);

                // if (op1) --> if (op2)
                ArrayList<IrStatement> stmtsList = new ArrayList<>();
                stmtsList.add(op2Else);
                IrCodeBlock op1IfBlock = new IrCodeBlock(new ArrayList<>(), stmtsList, this.getLineNumber(), this.getColNumber());
                IrCtrlFlowIf op1If = new IrCtrlFlowIf(op1, op1IfBlock);

                // else --> do nothing
                IrCtrlFlowIfElse op1IfElse = new IrCtrlFlowIfElse(op1If, emptyBlock);

                // create the short-circuit code instead
                op1IfElse.generateLlIr(builder, symbolTable);
            }
            else if (binaryOp.getOperation().equals("||")) {
                IrOperUnaryNot notOp1 = new IrOperUnaryNot(op1);
                IrOperUnaryNot notOp2 = new IrOperUnaryNot(op1);
                IrCodeBlock emptyBlock = new IrCodeBlock(new ArrayList<>(), new ArrayList<>(), this.getLineNumber(), this.getColNumber());

                // if (notOp2) --> do nothing
                IrCtrlFlowIf notOp2If = new IrCtrlFlowIf(notOp2, emptyBlock);

                // else --> this.stmtBody
                IrCtrlFlowIfElse notOp2IfElse = new IrCtrlFlowIfElse(notOp2If, this.stmtBody);

                // if (notOp1) --> if (notOp2)
                ArrayList<IrStatement> stmstList = new ArrayList<>();
                stmstList.add(notOp2IfElse);
                IrCodeBlock notOp1IfBlock = new IrCodeBlock(new ArrayList<>(), stmstList, this.getLineNumber(), this.getColNumber());
                IrCtrlFlowIf notOp1If = new IrCtrlFlowIf(notOp1, notOp1IfBlock);

                // else --> this.stmtBody
                IrCtrlFlowIfElse notOp1IfElse = new IrCtrlFlowIfElse(notOp1If, this.stmtBody);

                // generate the short-circuit code
                notOp1IfElse.generateLlIr(builder, symbolTable);
            }
        }
        else {
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

            // finally generate the if statement body itself
            this.stmtBody.generateLlIr(builder, symbolTable);

            // append end if label
            LlEmptyStmt endIfEmptyStmt = new LlEmptyStmt();
            builder.appendStatement(endIfLabel, endIfEmptyStmt);
        }

        // return the last known ...
        return null;
    }
}
