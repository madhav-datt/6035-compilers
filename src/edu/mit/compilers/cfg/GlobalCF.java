package edu.mit.compilers.cfg;

import edu.mit.compilers.ll.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by devinmorgan on 12/4/16.
 */
public class GlobalConstantPropgationAndFolding {

    // mutates the orignal CFG by performing Global Constant Propogation
    // and Folding on it.
    public static void performGlobalContantPropogationAndFolding(CFG cfg) {
        ArrayList<BasicBlock> basicBlocks = cfg.getBasicBlocks();

        // loop through each BasicBlock
        for (BasicBlock bb : basicBlocks) {
            LinkedHashMap<String, LlStatement> labelsToStmtsMap = bb.getLabelsToStmtsMap();

            // loop through each stmt in each bb
            for (String label : labelsToStmtsMap.keySet()) {
                LlStatement stmt = labelsToStmtsMap.get(label);

                // for Unary Computations
                if (stmt instanceof LlAssignStmtUnaryOp) {
                    LlAssignStmtUnaryOp unaryOp = (LlAssignStmtUnaryOp) stmt;

                    // for BoolLiterals
                    if (unaryOp.getOperand() instanceof LlLiteralBool) {
                        // we don't need to check the operator because
                        // the only operator is the ! operator
                        boolean evaluatedExpr = ! ((LlLiteralBool) unaryOp.getOperand()).getBoolValue();
                        LlLiteralBool newBool = new LlLiteralBool(evaluatedExpr);

                        // swap out the existing stmt with the new, evaluated stmt
                        labelsToStmtsMap.put(label, new LlAssignStmtRegular(unaryOp.getStoreLocation(),newBool));
                    }

                    // for IntLiterals
                    if (unaryOp.getOperand() instanceof LlLiteralInt) {
                        // we don't need to check the operator because
                        // the only operator is the - operator
                        long evaluatedExpr = - ((LlLiteralInt) unaryOp.getOperand()).getIntValue();
                        LlLiteralInt newInt = new LlLiteralInt(evaluatedExpr);

                        // swap out the existing stmt with the new, evaluated stmt
                        labelsToStmtsMap.put(label, new LlAssignStmtRegular(unaryOp.getStoreLocation(),newInt));
                    }
                }

                // for Binary Computations
                else if (stmt instanceof LlAssignStmtBinaryOp) {
                    LlAssignStmtBinaryOp binaryOp = (LlAssignStmtBinaryOp) stmt;

                    // for BoolLiterals
                    if (binaryOp.getLeftOperand() instanceof LlLiteralBool
                            && binaryOp.getRightOperand() instanceof LlLiteralBool) {
                        boolean op1 = ((LlLiteralBool) binaryOp.getLeftOperand()).getBoolValue();
                        boolean op2 = ((LlLiteralBool) binaryOp.getRightOperand()).getBoolValue();

                        // compute the expression depending on whether its || or &&
                        boolean evaluatedExpr = op1 && op2;
                        if (binaryOp.getOperation().equals("||")) {
                            evaluatedExpr = op1 || op2;
                        }
                        LlLiteralBool newBool = new LlLiteralBool(evaluatedExpr);

                        // swap the current BinaryAssignment with a RegularAssignment
                        labelsToStmtsMap.put(label, new LlAssignStmtRegular(binaryOp.getStoreLocation(), newBool));
                    }

                    // for IntLiterals
                    if (binaryOp.getLeftOperand() instanceof LlLiteralInt
                            && binaryOp.getRightOperand() instanceof LlLiteralInt) {
                        long op1 = ((LlLiteralInt) binaryOp.getLeftOperand()).getIntValue();
                        long op2 = ((LlLiteralInt) binaryOp.getLeftOperand()).getIntValue();

                        // don't let a Divide-by-Zero error happen
                        if (!binaryOp.getOperation().equals("/") && op2 != 0) {

                            // compute the expresssion depending on whether its %,+,*,-,/
                            long evaluatedExpr = 69696969;
                            switch (binaryOp.getOperation()) {
                                case "%":
                                    evaluatedExpr = op1 % op2;
                                    break;

                                case "+":
                                    evaluatedExpr = op1 + op2;
                                    break;

                                case "*":
                                    evaluatedExpr = op1 * op2;
                                    break;

                                case "-":
                                    evaluatedExpr = op1 - op2;
                                    break;

                                case "/":
                                    evaluatedExpr = op1 / op2;
                                    break;
                            }
                            LlLiteralInt newInt = new LlLiteralInt(evaluatedExpr);

                            // swap the current BinaryAssignment with a regular Assignment
                            labelsToStmtsMap.put(label, new LlAssignStmtRegular(binaryOp.getStoreLocation(), newInt));
                        }
                    }
                }
            }
        }
    }

}
