package edu.mit.compilers.ir;

import edu.mit.compilers.*;
import edu.mit.compilers.ll.*;

import java.util.ArrayList;

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

        if (this.condExpr instanceof IrOperBinaryCond) {
            IrOperBinaryCond binaryOp = (IrOperBinaryCond) this.condExpr;
            IrExpr op1 = binaryOp.getLeftOperand();
            IrExpr op2 = binaryOp.getRightOperand();

            if (binaryOp.getOperation().equals("&&")) {
                IrCodeBlock emptyBlock = new IrCodeBlock(new ArrayList<>(), new ArrayList<>(), this.getLineNumber(), this.getColNumber());

                // if (op2) --> this.stmtBody
                IrCtrlFlowIf op2If = new IrCtrlFlowIf(op2, this.stmtBody);

                // else --> do nothing
                IrCtrlFlowIfElse op2IfElse = new IrCtrlFlowIfElse(op2If, emptyBlock);

                // if (op1) --> if (op2)
                ArrayList<IrStatement> stmtsList = new ArrayList<>();
                stmtsList.add(op2IfElse);
                IrCodeBlock op1IfBlock = new IrCodeBlock(new ArrayList<>(), stmtsList, this.getLineNumber(), this.getColNumber());
                IrCtrlFlowIf op1If = new IrCtrlFlowIf(op1, op1IfBlock);

                // else --> do nothing
                IrCtrlFlowIfElse op1IfElse = new IrCtrlFlowIfElse(op1If, emptyBlock);

                // generate the short-circuit code
                op1IfElse.generateLlIr(builder, symbolTable);

            }
            else if (binaryOp.getOperation().equals("||")) {
                IrOperUnaryNot notOp1 = new IrOperUnaryNot(op1);
                IrOperUnaryNot notOp2 = new IrOperUnaryNot(op1);

                // if (notOp2) --> this.elseBlock
                IrCtrlFlowIf notOp2If = new IrCtrlFlowIf(notOp2, this.elseBlock);

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
        }

        // return the last known ...
        return null;
    }
}
