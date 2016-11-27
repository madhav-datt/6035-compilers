package edu.mit.compilers.cfg;

import edu.mit.compilers.LlBuilder;
import edu.mit.compilers.ll.*;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by devinmorgan on 11/27/16.
 */
public class LocalCF {
    private final LlBuilder builder;

    public LocalCF(LlBuilder builder) {
        this.builder = builder;
    }

    public static BasicBlock performLocalCSE(BasicBlock bb) {
        LocalCF cf = new LocalCF(bb.getBuilder());
        LinkedHashMap<String, LlStatement> optimizedMap = new LinkedHashMap<>();

        // 2) loop through the current linked hashmap
        for (String label : bb.getLabelsToStmtsMap().keySet()) {
            LlStatement stmt = bb.getLabelsToStmtsMap().get(label);

            // CF only applies to Binary and Unary expressions
            if (stmt instanceof LlAssignStmtUnaryOp) {
                LlAssignStmtUnaryOp unaryOp = (LlAssignStmtUnaryOp) stmt;

                // swap out operand in statement if its a copy
                LlStatement optStmt = cf.foldConstantForUnaryStmt(unaryOp);
                String optLabel = cf.builder.generateLabel();
                optimizedMap.put(optLabel, optStmt);
            }
            else if (stmt instanceof LlAssignStmtBinaryOp) {
                LlAssignStmtBinaryOp binaryOp = (LlAssignStmtBinaryOp) stmt;

                // swap out left and/or right operand if its a copy
                LlStatement optStmt = cf.foldConstantsForBinaryStmt(binaryOp);
                String optLabel = cf.builder.generateLabel();
                optimizedMap.put(optLabel, optStmt);
            }
        }
        return new BasicBlock(optimizedMap, cf.builder);
    }

    private LlStatement foldConstantForUnaryStmt(LlAssignStmtUnaryOp unaryOp) {
        // check to see if the operand is a constant
        if (unaryOp.getOperand() instanceof LlLiteral) {
            LlComponent computedVal;

            // perform the appropriate computation
            switch (unaryOp.getOperator()) {
                case "-":
                    long intVal = ((LlLiteralInt) unaryOp.getOperand()).getIntValue();
                    computedVal = new LlLiteralInt(-intVal);
                    break;
                case "!":
                    boolean boolVal = ((LlLiteralBool) unaryOp.getOperand()).getBoolValue();
                    computedVal = new LlLiteralInt(!boolVal);
                    break;
                default:
                    computedVal = new LlLiteralInt(69696969); // this case should never happen
            }
            return new LlAssignStmtRegular(unaryOp.getStoreLocation(), computedVal);
        }
        return unaryOp;
    }

    private LlStatement foldConstantsForBinaryStmt(LlAssignStmtBinaryOp binaryOp) {
        // check to see if left and right arguments are constants
        if (binaryOp.getLeftOperand() instanceof LlLiteral
                && binaryOp.getRightOperand() instanceof LlLiteral) {
            LlComponent computedVal;
            long intVal1;
            long intVal2;
            boolean boolVal1;
            boolean boolVal2;

            // perform the appropriate computation
            switch (binaryOp.getOperation()) {
                case "+":
                    intVal1 = ((LlLiteralInt) binaryOp.getLeftOperand()).getIntValue();
                    intVal2 = ((LlLiteralInt) binaryOp.getRightOperand()).getIntValue();
                    computedVal = new LlLiteralInt(intVal1 + intVal2);
                    break;
                case "*":
                    intVal1 = ((LlLiteralInt) binaryOp.getLeftOperand()).getIntValue();
                    intVal2 = ((LlLiteralInt) binaryOp.getRightOperand()).getIntValue();
                    computedVal = new LlLiteralInt(intVal1 * intVal2);
                    break;
                case "/":
                    intVal1 = ((LlLiteralInt) binaryOp.getLeftOperand()).getIntValue();
                    intVal2 = ((LlLiteralInt) binaryOp.getRightOperand()).getIntValue();
                    computedVal = new LlLiteralInt(intVal1 / intVal2);
                    break;
                case "-":
                    intVal1 = ((LlLiteralInt) binaryOp.getLeftOperand()).getIntValue();
                    intVal2 = ((LlLiteralInt) binaryOp.getRightOperand()).getIntValue();
                    computedVal = new LlLiteralInt(intVal1 - intVal2);
                    break;
                case "%":
                    intVal1 = ((LlLiteralInt) binaryOp.getLeftOperand()).getIntValue();
                    intVal2 = ((LlLiteralInt) binaryOp.getRightOperand()).getIntValue();
                    computedVal = new LlLiteralInt(intVal1 % intVal2);
                    break;
                case "&&":
                    boolVal1 = ((LlLiteralBool) binaryOp.getLeftOperand()).getBoolValue();
                    boolVal2 = ((LlLiteralBool) binaryOp.getRightOperand()).getBoolValue();
                    computedVal = new LlLiteralBool(boolVal1 && boolVal2);
                    break;
                case "||":
                    boolVal1 = ((LlLiteralBool) binaryOp.getLeftOperand()).getBoolValue();
                    boolVal2 = ((LlLiteralBool) binaryOp.getRightOperand()).getBoolValue();
                    computedVal = new LlLiteralBool(boolVal1 || boolVal2);
                    break;
                default:
                    computedVal = new LlLiteralInt(69696969); // this case should never happen
            }
            return new LlAssignStmtRegular(binaryOp.getStoreLocation(), computedVal);
        }
       return binaryOp;
    }


}
