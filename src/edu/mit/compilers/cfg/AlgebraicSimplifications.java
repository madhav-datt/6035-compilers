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

        // look at each BasicBlock
        for (BasicBlock bb : basicBlocks) {
            LinkedHashMap<String, LlStatement> labelsToStmtsMap = bb.getLabelsToStmtsMap();

            // check each stmt in each BasicBlock for possible Algebraic simplifications
            for (String label : labelsToStmtsMap.keySet()) {
                LlStatement stmt = labelsToStmtsMap.get(label);

                if (stmt instanceof LlAssignStmtBinaryOp) {
                    LlAssignStmtBinaryOp binaryOp = (LlAssignStmtBinaryOp) stmt;

                    if (isArithmeticExpression(binaryOp)) {
                        LlStatement simplifiedStmt = performBinaryArithmeticSimplifications(binaryOp);
                        labelsToStmtsMap.put(label, simplifiedStmt);
                    }
                    else { // it's boolean
                        LlStatement simplifiedStmt = performBinaryBooleanSimplifications(binaryOp);
                        labelsToStmtsMap.put(label, simplifiedStmt);
                    }
                }
            }
        }
    }

    private static boolean isArithmeticExpression(LlAssignStmtBinaryOp binaryOp) {
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

    private static LlStatement performBinaryArithmeticSimplifications(LlAssignStmtBinaryOp binaryOp) {
        LlAssignStmt simplifiedStmt = binaryOp;
        LlComponent op1 = binaryOp.getLeftOperand();
        String operation = binaryOp.getOperation();
        LlComponent op2 = binaryOp.getRightOperand();

        // stmt is 0 + var
        if (op1 instanceof LlLiteralInt
                && operation.equals("+")
                && op2 instanceof LlLocation) {

            // stmt' is var
            if (((LlLiteralInt) op1).getIntValue() == 0) {
                simplifiedStmt = new LlAssignStmtRegular(binaryOp.getStoreLocation(), op2);
            }
        }

        // stmt is (var + 0) || (var - 0)
        else if (op1 instanceof LlLocation
                && (operation.equals("+") || operation.equals("-"))
                && op2 instanceof LlLiteralInt) {

            // stmt' is var
            if (((LlLiteralInt) op2).getIntValue() == 0) {
                simplifiedStmt = new LlAssignStmtRegular(binaryOp.getStoreLocation(), op1);
            }
        }

        // stmt is 0 - var
        else if (op1 instanceof LlLiteralInt
                && operation.equals("-")
                && op2 instanceof LlLocationVar) {

            // stmt' is -var
            if (((LlLiteralInt) op1).getIntValue() == 0) {
                simplifiedStmt = new LlAssignStmtUnaryOp(binaryOp.getStoreLocation(), op2, operation);
            }
        }

        // stmt is 1 * var
        else if (op1 instanceof LlLiteralInt
                && operation.equals("*")
                && op2 instanceof LlLocation) {

            // stmt' is var
            if (((LlLiteralInt) op1).getIntValue() == 1) {
                simplifiedStmt = new LlAssignStmtRegular(binaryOp.getStoreLocation(), op2);
            }
        }

        // stmt is (var * 1) || (var / 1)
        else if (op1 instanceof LlLocation
                && (operation.equals("*") || operation.equals("/"))
                && op2 instanceof LlLiteralInt) {

            // stmt' is var
            if (((LlLiteralInt) op2).getIntValue() == 1) {
                simplifiedStmt = new LlAssignStmtRegular(binaryOp.getStoreLocation(), op1);
            }
        }

        // stmt is var * 0
        else if (op1 instanceof LlLocation
                && operation.equals("*")
                && op2 instanceof LlLiteralInt) {

            // stmt' is 0
            if (((LlLiteralInt) op2).getIntValue() == 0) {
                simplifiedStmt = new LlAssignStmtRegular(binaryOp.getStoreLocation(), op2);
            }
        }

        // stmt is 0 * var
        else if (op1 instanceof LlLiteralInt
                && operation.equals("*")
                && op2 instanceof LlLocation) {

            // stmt' is 0
            if (((LlLiteralInt) op1).getIntValue() == 0) {
                simplifiedStmt = new LlAssignStmtRegular(binaryOp.getStoreLocation(), op1);
            }
        }

        return simplifiedStmt;
    }

    private static LlStatement performBinaryBooleanSimplifications(LlAssignStmtBinaryOp binaryOp) {
        LlAssignStmt simplifiedStmt = binaryOp;
        LlComponent op1 = binaryOp.getLeftOperand();
        String operation = binaryOp.getOperation();
        LlComponent op2 = binaryOp.getRightOperand();

        // stmt is B || true
        if (op1 instanceof LlLocation
                && operation.equals("||")
                && op2 instanceof LlLiteralBool) {

            // stmt' is true
            if (((LlLiteralBool) op2).getBoolValue() == true) {
                simplifiedStmt = new LlAssignStmtRegular(binaryOp.getStoreLocation(), op2);
            }
        }

        // stmt is true || B
        else if (op1 instanceof LlLiteralBool
                && operation.equals("||")
                && op2 instanceof LlLocation) {

            // stmt' is true
            if (((LlLiteralBool) op1).getBoolValue() == true) {
                simplifiedStmt = new LlAssignStmtRegular(binaryOp.getStoreLocation(), op1);
            }
        }

        // stmt is B || false
        if (op1 instanceof LlLocation
                && operation.equals("||")
                && op2 instanceof LlLiteralBool) {

            // stmt' is B
            if (((LlLiteralBool) op2).getBoolValue() == false) {
                simplifiedStmt = new LlAssignStmtRegular(binaryOp.getStoreLocation(), op1);
            }
        }

        // stmt is false || B
        else if (op1 instanceof LlLiteralBool
                && operation.equals("||")
                && op2 instanceof LlLocation) {

            // stmt' is B
            if (((LlLiteralBool) op1).getBoolValue() == false) {
                simplifiedStmt = new LlAssignStmtRegular(binaryOp.getStoreLocation(), op2);
            }
        }

        // stmt is B && true
        else if (op1 instanceof LlLocation
                && operation.equals("&&")
                && op2 instanceof LlLiteralBool) {

            // stmt' is B
            if (((LlLiteralBool) op2).getBoolValue() == true) {
                simplifiedStmt = new LlAssignStmtRegular(binaryOp.getStoreLocation(), op1);
            }
        }

        // stmt is true && B
        else if (op1 instanceof LlLiteralBool
                && operation.equals("&&")
                && op2 instanceof LlLocation) {

            // stmt' is B
            if (((LlLiteralBool) op1).getBoolValue() == true) {
                simplifiedStmt = new LlAssignStmtRegular(binaryOp.getStoreLocation(), op2);
            }
        }

        // stmt is B && false
        else if (op1 instanceof LlLocation
                && operation.equals("&&")
                && op2 instanceof LlLiteralBool) {

            // stmt' is false
            if (((LlLiteralBool) op2).getBoolValue() == false) {
                simplifiedStmt = new LlAssignStmtRegular(binaryOp.getStoreLocation(), op2);
            }
        }

        // stmt is false && B
        else if (op1 instanceof LlLiteralBool
                && operation.equals("&&")
                && op2 instanceof LlLocation) {

            // stmt' is false
            if (((LlLiteralBool) op1).getBoolValue() == false) {
                simplifiedStmt = new LlAssignStmtRegular(binaryOp.getStoreLocation(), op1);
            }
        }

        // stmt is B == true
        else if (op1 instanceof LlLocation
                && operation.equals("==")
                && op2 instanceof LlLiteralBool) {

            // stmt' is B
            if (((LlLiteralBool) op2).getBoolValue() == true) {
                simplifiedStmt = new LlAssignStmtRegular(binaryOp.getStoreLocation(), op1);
            }
        }

        // stmt is true == B
        else if (op1 instanceof LlLiteralBool
                && operation.equals("==")
                && op2 instanceof LlLocation) {

            // stmt' is B
            if (((LlLiteralBool) op1).getBoolValue() == true) {
                simplifiedStmt = new LlAssignStmtRegular(binaryOp.getStoreLocation(), op2);
            }
        }

        // stmt is B == false
        else if (op1 instanceof LlLocation
                && operation.equals("==")
                && op2 instanceof LlLiteralBool) {

            // stmt' is !B
            if (((LlLiteralBool) op2).getBoolValue() == false) {
                simplifiedStmt = new LlAssignStmtUnaryOp(binaryOp.getStoreLocation(), op1, "!");
            }
        }

        // stmt is false == B
        else if (op1 instanceof LlLiteralBool
                && operation.equals("==")
                && op2 instanceof LlLocation) {

            // stmt' is !B
            if (((LlLiteralBool) op1).getBoolValue() == false) {
                simplifiedStmt = new LlAssignStmtUnaryOp(binaryOp.getStoreLocation(), op2, "!");
            }
        }

        // stmt is B != true
        else if (op1 instanceof LlLocation
                && operation.equals("!=")
                && op2 instanceof LlLiteralBool) {

            // stmt' is !B
            if (((LlLiteralBool) op2).getBoolValue() == true) {
                simplifiedStmt = new LlAssignStmtUnaryOp(binaryOp.getStoreLocation(), op1, "!");
            }
        }

        // stmt is true != B
        else if (op1 instanceof LlLiteralBool
                && operation.equals("!=")
                && op2 instanceof LlLocation) {

            // stmt' is !B
            if (((LlLiteralBool) op1).getBoolValue() == true) {
                simplifiedStmt = new LlAssignStmtUnaryOp(binaryOp.getStoreLocation(), op2, "!");
            }
        }

        // stmt is B != false
        else if (op1 instanceof LlLocation
                && operation.equals("!=")
                && op2 instanceof LlLiteralBool) {

            // stmt' is B
            if (((LlLiteralBool) op2).getBoolValue() == false) {
                simplifiedStmt = new LlAssignStmtRegular(binaryOp.getStoreLocation(), op1);
            }
        }

        // stmt is false != B
        else if (op1 instanceof LlLiteralBool
                && operation.equals("!=")
                && op2 instanceof LlLocation) {

            // stmt' is B
            if (((LlLiteralBool) op1).getBoolValue() == false) {
                simplifiedStmt = new LlAssignStmtRegular(binaryOp.getStoreLocation(), op2);
            }
        }

        return simplifiedStmt;
    }

}
