package edu.mit.compilers.cfg;

import edu.mit.compilers.ll.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Created by devinmorgan on 11/27/16.
 */
public class GlobalLivenessTable {
    private final HashMap<String, HashMap<String, Boolean>> labelVarLivenessMap = new HashMap<>();

    private void addNewVariableToGlobalLivenessTable(
            LlComponent comp, HashMap<String, Boolean> table) {
        if (comp instanceof LlLocationVar) {
            LlLocationVar var = (LlLocationVar) comp;
            table.put(var.getVarName(), false);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GlobalLivenessTable) {
            GlobalLivenessTable that = (GlobalLivenessTable) obj;

            // iterate over each variable in each stmt
            for (String label : this.labelVarLivenessMap.keySet()) {
                for (String var : this.labelVarLivenessMap.get(label).keySet()) {

                    // every (label, var) pair should match if the tables are equal
                    if (that.labelVarLivenessMap.containsKey(label)) {
                        if (that.labelVarLivenessMap.get(label).containsKey(var)) {
                            if (!that.labelVarLivenessMap.get(label).get(var)
                                    .equals(this.labelVarLivenessMap.get(label).get(var))) {
                                return false;
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    private static GlobalLivenessTable createEmptyGlobalLivenessTable(CFG cfg) {
        GlobalLivenessTable livenessTable = new GlobalLivenessTable();

        for (BasicBlock bb : cfg.getBasicBlocks()) {
            for (String label : bb.getLabelsToStmtsMap().keySet()) {
                LlStatement stmt = bb.getLabelsToStmtsMap().get(label);
                HashMap<String, Boolean> varsForCurrentStmt = new HashMap<>();

                // TODO: Figure out how to handle arrays a[i]
                if (stmt instanceof LlAssignStmtRegular) {
                    LlAssignStmtRegular assignStmt = (LlAssignStmtRegular) stmt;

                    // add read and write vars to livenessTable
                    livenessTable.addNewVariableToGlobalLivenessTable(
                            assignStmt.getStoreLocation(), varsForCurrentStmt);
                    livenessTable.addNewVariableToGlobalLivenessTable(
                            assignStmt.getOperand(), varsForCurrentStmt);

                }
                if (stmt instanceof LlAssignStmtUnaryOp) {
                    LlAssignStmtUnaryOp unaryOp = (LlAssignStmtUnaryOp) stmt;

                    // add read and write vars to livenessTable
                    livenessTable.addNewVariableToGlobalLivenessTable(
                            unaryOp.getStoreLocation(), varsForCurrentStmt);
                    livenessTable.addNewVariableToGlobalLivenessTable(
                            unaryOp.getOperand(), varsForCurrentStmt);
                }
                if (stmt instanceof LlAssignStmtBinaryOp) {
                    LlAssignStmtBinaryOp binaryOp = (LlAssignStmtBinaryOp) stmt;

                    // add read and write vars to livenessTable
                    livenessTable.addNewVariableToGlobalLivenessTable(
                            binaryOp.getStoreLocation(), varsForCurrentStmt);
                    livenessTable.addNewVariableToGlobalLivenessTable(
                            binaryOp.getLeftOperand(), varsForCurrentStmt);
                    livenessTable.addNewVariableToGlobalLivenessTable(
                            binaryOp.getRightOperand(), varsForCurrentStmt);
                }
                if (stmt instanceof LlJumpConditional) {
                    LlJumpConditional condJmp = (LlJumpConditional) stmt;

                    // add conditional variable to livenessTable
                    livenessTable.addNewVariableToGlobalLivenessTable(
                            condJmp.getCondition(), varsForCurrentStmt);
                }
                if (stmt instanceof LlMethodCallStmt) {
                    LlMethodCallStmt method = (LlMethodCallStmt) stmt;

                    // add the method result to the liveTable
                    livenessTable.addNewVariableToGlobalLivenessTable(
                            method.getReturnLocation(), varsForCurrentStmt);
                    for (LlComponent comp : method.getArgsList()) {
                        livenessTable.addNewVariableToGlobalLivenessTable(
                                comp, varsForCurrentStmt);
                    }
                }
                if (stmt instanceof LlReturn) {
                    LlReturn rtn = (LlReturn) stmt;

                    // add the method result to the liveTable
                    livenessTable.addNewVariableToGlobalLivenessTable(
                            rtn.getReturnValue(), varsForCurrentStmt);
                }

                // add the
                livenessTable.labelVarLivenessMap.put(label, varsForCurrentStmt);
            }
        }
    }

    public static GlobalLivenessTable createGlobalLivenessTable(CFG cfg) {
        GlobalLivenessTable newTable = GlobalLivenessTable.createEmptyGlobalLivenessTable(cfg);
        GlobalLivenessTable oldTable;

        do {
            // store the version of the table before it gets mutated
            oldTable = newTable;

            // set up for BFS to traverse the CFG
            HashSet<BasicBlock> visitedBBs = new HashSet<BasicBlock>();
            LinkedList<BasicBlock> frontierSet = new LinkedList<>();
            frontierSet.add(cfg.getExitNode());

            // as long as there are nodes we have not explored
            while (frontierSet.size() > 0) {

                // we don't want to look at nodes we have already visited
                BasicBlock currentNode = frontierSet.pop();
                if (visitedBBs.contains(currentNode)) {
                    continue;
                }
                visitedBBs.add(currentNode);

                // add this nodes predecessors to the frontier set
                for (BasicBlock predecssor : currentNode.getPredecessors()) {
                    frontierSet.add(predecssor);
                }

                for (String label : currentNode.getLabelsToStmtsMap().keySet()) {
                    LlStatement stmt = currentNode.getLabelsToStmtsMap().get(label);

                    if (stmt instanceof LlAssignStmtRegular) {
                        LlAssignStmtRegular regular = (LlAssignStmtRegular) stmt;

                        // update the livenessTable with variable information
                        newTable.updateTableForReadVariable(label, regular.getOperand());
                        newTable.updateTableForWriteVariable(regular.getStoreLocation());
                    }
                    if (stmt instanceof LlAssignStmtUnaryOp) {
                        LlAssignStmtUnaryOp unaryOp = (LlAssignStmtUnaryOp) stmt;

                        // update the livenessTable with variable information
                        newTable.updateTableForReadVariable(label, unaryOp.getOperand());
                        newTable.updateTableForWriteVariable(unaryOp.getStoreLocation());
                    }
                    if (stmt instanceof LlAssignStmtBinaryOp) {
                        LlAssignStmtBinaryOp binaryOp = (LlAssignStmtBinaryOp) stmt;

                        // update the livenessTable with variable information
                        newTable.updateTableForReadVariable(label, binaryOp.getLeftOperand());
                        newTable.updateTableForReadVariable(label, binaryOp.getRightOperand());
                        newTable.updateTableForWriteVariable(binaryOp.getStoreLocation());
                    }
                    if (stmt instanceof LlJumpConditional) {
                        LlJumpConditional condJmp = (LlJumpConditional) stmt;

                        // update the livenessTable with variable information
                        newTable.updateTableForReadVariable(label, condJmp.getCondition());
                    }
                    if (stmt instanceof LlMethodCallStmt) {
                        LlMethodCallStmt method = (LlMethodCallStmt) stmt;

                        // update livenessTAble with variable information
                        newTable.updateTableForWriteVariable(method.getReturnLocation());
                        for (LlComponent comp : method.getArgsList()) {
                            newTable.updateTableForReadVariable(label, comp);
                        }
                    }
                    if (stmt instanceof LlReturn) {
                        LlReturn rtn = (LlReturn) stmt;

                        // update the livenessTable with variable information
                        newTable.updateTableForReadVariable(label, rtn.getReturnValue());
                    }
                }
            }
        } while(!newTable.equals(newTable));
    }

    private void updateTableForReadVariable(String label, LlComponent comp) {
        // TODO: Figure out how to handle arrays a[i]
        if (comp instanceof LlLocationVar) {
            LlLocationVar var = (LlLocationVar) comp;

            // anytime a variable is on the RHS it becomes live
            this.labelVarLivenessMap.get(label).put(var.getVarName(), true);
        }
    }

    private void updateTableForWriteVariable(LlComponent comp) {
        // TODO: Figure out how to handle arrays a[i]
        if (comp instanceof LlLocationVar) {
            // true if x is live in any of the successors of this node
            // false if var is not referred to in RHS
        }
    }

}
/*
 update the liveness of any variable(s) that is(are) read
                update the liveness of the write variable (if one exists)
                    this means checking whether the write var is live in any of the
                    successor of the current node
 */




/*
    for each block in cfg
        successorValues ::= list or bit-vectors at the starting-points of all of the successor nodes
        prev_stmt_out_value
        prev_stmt_in_value

        for each stmt in the block (going bottom to top):
            process read vars:


 */


/*
    @ the current statement, the relevant information is:
        the operands of the current stmt --> L(s,x,in) = true if X is referred to in RHS
        the in-value-bit-vectors of the successors nodes of the current block --> L(s,x,out) = OR over the successors of the current block of the in-value-bit-vectors
        the out-value-bit-vectors of the current stmt --> L(s,x,in) = L(s,x,out) if x is not mentioned in LHS or RHS



        Rules
        if (the in-value of x is non-zero for any of this nodes successors)
            L(s,x,out) = true
        if (x is in RHS of stmt)
            L(s,x,in) = true
 */
























