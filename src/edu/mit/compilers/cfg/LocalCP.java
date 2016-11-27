package edu.mit.compilers.cfg;

import edu.mit.compilers.LlBuilder;
import edu.mit.compilers.ll.*;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by devinmorgan on 11/26/16.
 */
public class LocalCP {
    private final HashMap<LlLocation, LlComponent> copyTable = new HashMap<>();
    private final LlBuilder builder;

    public LocalCP(LlBuilder builder) {
        this.builder = builder;
    }

    public static BasicBlock performLocalCSE(BasicBlock bb) {
        LocalCP cp = new LocalCP(bb.getBuilder());
        LinkedHashMap<String, LlStatement> optimizedMap = new LinkedHashMap<>();

        // 2) loop through the current linked hashmap
        for (String label : bb.getLabelsToStmtsMap().keySet()) {
            LlStatement stmt = bb.getLabelsToStmtsMap().get(label);

            // CP only applies to Binary, Unary, and Regular Assignments
            if (stmt instanceof LlAssignStmtUnaryOp) {
                LlAssignStmtUnaryOp unaryOp = (LlAssignStmtUnaryOp) stmt;

                // swap out operand in statement if its a copy
                LlAssignStmtUnaryOp optStmt = cp.swapOperandsForUnaryStmt(unaryOp);
                String optLabel = cp.builder.generateLabel();
                optimizedMap.put(optLabel, optStmt);
                
                // remove this var from the copyTable
                cp.copyTable.remove(unaryOp.getStoreLocation());
            }
            else if (stmt instanceof LlAssignStmtBinaryOp) {
                LlAssignStmtBinaryOp binaryOp = (LlAssignStmtBinaryOp) stmt;

                // swap out left and/or right operand if its a copy
                LlAssignStmtBinaryOp optStmt = cp.swapOperandForBinaryStmt(binaryOp);
                String optLabel = cp.builder.generateLabel();
                optimizedMap.put(optLabel, optStmt);

                // remove this var from the copyTable
                cp.copyTable.remove(binaryOp.getStoreLocation());
            }
            else if (stmt instanceof LlJumpConditional) {
                LlJumpConditional condJmp = (LlJumpConditional) stmt;

                // swap out operand in statement if its a copy
                LlJumpConditional optStmt = cp.swapVariableForConditionalJump(condJmp);
                String optLabel = cp.builder.generateLabel();
                optimizedMap.put(optLabel, optStmt);
            }
            else if (stmt instanceof LlReturn) {
                LlReturn rtnStmt = (LlReturn) stmt;

                // swap out operand in statement if its a copy
                LlReturn optStmt = cp.swapVariableForReturnStmt(rtnStmt);
                String optLabel = cp.builder.generateLabel();
                optimizedMap.put(optLabel, optStmt);
            }
            else if (stmt instanceof LlAssignStmtRegular) {
                LlAssignStmtRegular stmtRegular = (LlAssignStmtRegular) stmt;

                // swap out operand in statement if its a copy
                LlAssignStmtRegular optStmt = cp.swapOperandsForRegularStmt(stmtRegular);
                String optLabel = cp.builder.generateLabel();
                optimizedMap.put(optLabel, optStmt);

                // add LHS definitions to the copyTable
                // TODO: Figure out how to make this work for a[i] (i.e. arrays)
                if (stmtRegular.getStoreLocation() instanceof LlLocationVar
                        && !(stmtRegular.getOperand() instanceof LlLocationArray)) {
                    cp.copyTable.put(stmtRegular.getStoreLocation(), stmtRegular.getOperand());
                }
            }
        }

        return new BasicBlock(optimizedMap, cp.builder);
    }

    private LlReturn swapVariableForReturnStmt(LlReturn rtnStmt) {
        // check if the operand is a variable in the copyTable
        if (rtnStmt.getReturnValue() instanceof LlLocationVar) {
            LlLocationVar var = (LlLocationVar) rtnStmt.getReturnValue();

            // return a new statement with the var from copyTable if it exists
            if (this.copyTable.containsKey(var)) {
                return new LlReturn(var);
            }
        }
        return rtnStmt;
    }

    private LlJumpConditional swapVariableForConditionalJump(LlJumpConditional condJmp) {
        // check if the operand is a variable in the copyTable
        if (condJmp.getCondition() instanceof LlLocationVar) {
            LlLocationVar var = (LlLocationVar) condJmp.getCondition();

            // return a new statement with the var from copyTable if it exists
            if (this.copyTable.containsKey(var)) {
                return new LlJumpConditional(condJmp.getJumpToLabel(), var);
            }
        }
        return condJmp;
    }

    private LlAssignStmtRegular swapOperandsForRegularStmt(LlAssignStmtRegular regular) {
        // check if the operand is a variable in the copyTable
        if (regular.getOperand() instanceof LlLocationVar) {
            LlLocationVar var = (LlLocationVar) regular.getOperand();

            // return a new statement with the var from copyTable if it exists
            if (this.copyTable.containsKey(var)) {
                return new LlAssignStmtRegular(regular.getStoreLocation(), this.copyTable.get(var));
            }
        }
        return regular;
    }

    private LlAssignStmtUnaryOp swapOperandsForUnaryStmt(LlAssignStmtUnaryOp unaryOp) {
        // check if the operand is a variable in the copyTable
        if (unaryOp.getOperand() instanceof LlLocationVar) {
            LlLocationVar var = (LlLocationVar) unaryOp.getOperand();

            // return a new statement with the var from copyTable if it exists
            if (this.copyTable.containsKey(var)) {
                return new LlAssignStmtUnaryOp(unaryOp.getStoreLocation(), this.copyTable.get(var), unaryOp.getOperator());
            }
        }
        return unaryOp;
    }

    private LlAssignStmtBinaryOp swapOperandForBinaryStmt(LlAssignStmtBinaryOp binaryOp) {
        LlComponent newLeftOperand = binaryOp.getLeftOperand();
        LlComponent newRightOperand = binaryOp.getRightOperand();

        // check the left operand
        if (binaryOp.getLeftOperand() instanceof LlLocationVar) {
            LlLocationVar var = (LlLocationVar) binaryOp.getLeftOperand();

            // swap the variable if necessary
            if (this.copyTable.containsKey(var)) {
                newLeftOperand = this.copyTable.get(var);
            }
        }
        // check the right operand
        if (binaryOp.getRightOperand() instanceof LlLocationVar) {
            LlLocationVar var = (LlLocationVar) binaryOp.getRightOperand();

            // swap the variable if necessary
            if (this.copyTable.containsKey(var)) {
                newRightOperand = this.copyTable.get(var);
            }
        }
        return new LlAssignStmtBinaryOp(binaryOp.getStoreLocation(),
                newLeftOperand, binaryOp.getOperation(), newRightOperand);
    }

}
