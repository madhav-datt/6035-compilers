package edu.mit.compilers.cfg;

import edu.mit.compilers.ll.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by devinmorgan on 12/8/16.
 */
public class AlgebraicSimplifications {


    public static void performAlgebraicSimplifications(CFG cfg) {
        ArrayList<BasicBlock> basicBlocks = cfg.getBasicBlocks();

        for (BasicBlock bb : basicBlocks) {
            LinkedHashMap<String, LlStatement> labelsToStmtsMap = bb.getLabelsToStmtsMap();

            for (String label : labelsToStmtsMap.keySet()) {
                LlStatement stmt = labelsToStmtsMap.get(label);

                if (stmt instanceof LlAssignStmtBinaryOp) {
                    LlAssignStmtBinaryOp binaryOp = (LlAssignStmtBinaryOp) stmt;

                    if (isArithmaticExpression(binaryOp)) {
                        LlComponent op1 = binaryOp.getLeftOperand();
                        String operation = binaryOp.getOperation();
                        LlComponent op2 = binaryOp.getRightOperand();

                        // stmt is 0 + var
                        if (op1 instanceof LlLiteralInt
                                && operation.equals("+")
                                && op2 instanceof LlLocation) {

                            // stmt' is var
                            if (((LlLiteralInt) op1).getIntValue() == 0) {
                                LlAssignStmtRegular simplifiedStmt =
                                        new LlAssignStmtRegular(binaryOp.getStoreLocation(), op2);
                                labelsToStmtsMap.put(label, simplifiedStmt);
                            }
                        }

                        // stmt is (var + 0) || (var - 0)
                        else if (op1 instanceof LlLocation
                                && (operation.equals("+") || operation.equals("-"))
                                && op2 instanceof LlLiteralInt) {

                            // stmt' is var
                            if (((LlLiteralInt) op2).getIntValue() == 0) {
                                LlAssignStmtRegular simplifiedStmt =
                                        new LlAssignStmtRegular(binaryOp.getStoreLocation(), op1);
                                labelsToStmtsMap.put(label, simplifiedStmt);
                            }
                        }

                        // stmt is 0 - var
                        else if (op1 instanceof LlLiteralInt
                                && operation.equals("-")
                                && op2 instanceof LlLocationVar) {

                            // stmt' is -var
                            if (((LlLiteralInt) op1).getIntValue() == 0) {
                                LlAssignStmtUnaryOp simplifiedStmt =
                                        new LlAssignStmtUnaryOp(binaryOp.getStoreLocation(), op2, operation);
                                labelsToStmtsMap.put(label, simplifiedStmt);
                            }
                        }

                        // stmt is 1 * var
                        else if (op1 instanceof LlLiteralInt
                                && operation.equals("*")
                                && op2 instanceof LlLocation) {

                            // stmt' is var
                            if (((LlLiteralInt) op1).getIntValue() == 1) {
                                LlAssignStmtRegular simplifiedStmt =
                                        new LlAssignStmtRegular(binaryOp.getStoreLocation(), op2);
                                labelsToStmtsMap.put(label, simplifiedStmt);
                            }
                        }

                        // stmt is (var * 1) || (var / 1)
                        else if (op1 instanceof LlLocation
                                && (operation.equals("*") || operation.equals("/"))
                                && op2 instanceof LlLiteralInt) {

                            // stmt' is var
                            if (((LlLiteralInt) op2).getIntValue() == 1) {
                                LlAssignStmtRegular simplifiedStmt =
                                        new LlAssignStmtRegular(binaryOp.getStoreLocation(), op1);
                                labelsToStmtsMap.put(label, simplifiedStmt);
                            }
                        }

                        // stmt is var * 0
                        else if (op1 instanceof LlLocation
                                && operation.equals("*")
                                && op2 instanceof LlLiteralInt) {

                            // stmt' is 0
                            if (((LlLiteralInt) op2).getIntValue() == 0) {
                                LlAssignStmtRegular simplifiedStmt =
                                        new LlAssignStmtRegular(binaryOp.getStoreLocation(), op2);
                                labelsToStmtsMap.put(label, simplifiedStmt);
                            }
                        }

                        // stmt is 0 * var
                        else if (op1 instanceof LlLiteralInt
                                && operation.equals("*")
                                && op2 instanceof LlLocation) {

                            // stmt' is 0
                            if (((LlLiteralInt) op1).getIntValue() == 0) {
                                LlAssignStmtRegular simplifiedStmt =
                                        new LlAssignStmtRegular(binaryOp.getStoreLocation(), op1);
                                labelsToStmtsMap.put(label, simplifiedStmt);
                            }
                        }
                    }
                    else { // it's boolean

                    }
                }
            }
        }
    }

    private static boolean isArithmaticExpression(LlAssignStmtBinaryOp binaryOp) {
        switch (binaryOp.getOperation()) {
            case "+":
                return true;
            case "-":
                return true;
            case "*":
                return true;
            case "/":
                return true;
            case "%":
                return true;
            default:
                return false;
        }
    }


}
