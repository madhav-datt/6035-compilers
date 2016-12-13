package edu.mit.compilers.cfg;

import edu.mit.compilers.ll.*;

import java.util.*;

/**
 * Created by devinmorgan on 12/7/16.
 */
public class GlobalCSE {
    private final CFG cfg;

    private GlobalCSE(CFG cfg) {
        this.cfg = cfg;
    }

    public static void performGlobalCommonSubexpressionEliminationOnCFG(CFG cfg, HashSet<LlLocationVar> globalVariables) {
        GlobalCSE cse = new GlobalCSE(cfg);
        ArrayList<BasicBlock> basicBlocks = cfg.getBasicBlocks();

        // 1) perform local common subexpression elimination
        for (BasicBlock bb : basicBlocks) {
            LocalCSE.performLocalCSE(bb, globalVariables);
        }

        // get available expression for each BasicBlock in the CFG
        HashMap<BasicBlock, HashSet<Computation>> availExprIN
                = AvailableExpressions.getAvailableExpressionsForCFG(cfg, globalVariables);

        // loop through each bb and look for common subexpressions
        for (BasicBlock bb : basicBlocks) {
            HashSet<Computation> bbAvailExpr = availExprIN.get(bb);
            LinkedHashMap<String, LlStatement> labelsToStmtsMap = bb.getLabelsToStmtsMap();

            // 2) check to see if any of the AvailableExpressions are in
            // the Basicblock's LlStmts
            for (Computation comp : bbAvailExpr) {
                for (String label : labelsToStmtsMap.keySet()) {
                    LlStatement stmt = labelsToStmtsMap.get(label);

                    // unary computation
                    if (stmt instanceof LlAssignStmtUnaryOp) {
                        if (cse.containsUnarySubExpression((LlAssignStmtUnaryOp) stmt, comp)) {
                            LlAssignStmtUnaryOp unaryOp = (LlAssignStmtUnaryOp) stmt;

                            // eliminate the common sub-expression through the CFG
                            // and reassign the var to the resulting temp VAr
                            LlLocationVar compTemp = cse.backPropagateToEliminateCommonExpressionFromCFG(bb, comp);
                            LlAssignStmtRegular reassignStmt =
                                    new LlAssignStmtRegular(unaryOp.getStoreLocation(), compTemp);
                            labelsToStmtsMap.put(label, reassignStmt);
                            break; // after localCSE, a given expr should occur only 1/BB
                        }
                    }
                    // binary computation
                    else if (stmt instanceof LlAssignStmtBinaryOp) {
                        if (cse.containsBinarySubExpression((LlAssignStmtBinaryOp) stmt, comp)) {
                            LlAssignStmtBinaryOp binaryOp = (LlAssignStmtBinaryOp) stmt;

                            // eliminate the common sub-expression through the CFG
                            // and reassign the var to the resulting temp VAr
                            LlLocationVar compTemp = cse.backPropagateToEliminateCommonExpressionFromCFG(bb, comp);
                            LlAssignStmtRegular reassignStmt =
                                    new LlAssignStmtRegular(binaryOp.getStoreLocation(), compTemp);
                            labelsToStmtsMap.put(label, reassignStmt);
                            break; // after localCSE, a given expr should only occur 1/BB
                        }
                    }

                    // if one of the vars in the expr get's reassigned,
                    // the common expr is no longer valid.
                    if (stmt instanceof LlAssignStmt) {
                        LlAssignStmt assignStmt = (LlAssignStmt) stmt;
                        if (comp.contains(assignStmt.getStoreLocation())) {
                            break;
                        }
                    }
                }
            }
        }
    }

    private boolean containsUnarySubExpression(LlAssignStmtUnaryOp unaryOp, Computation comp) {
        if (comp instanceof Computation.UnaryComputation) {
            Computation.UnaryComputation otherComp =
                    new Computation.UnaryComputation(
                            unaryOp.getOperator(),
                            unaryOp.getOperand()
                    );
            return comp.equals(otherComp);
        }
        return false;
    }

    private boolean containsBinarySubExpression(LlAssignStmtBinaryOp binaryOp, Computation comp) {
        if (comp instanceof Computation.BinaryComputation) {
            Computation.BinaryComputation otherComp =
                    new Computation.BinaryComputation(
                            binaryOp.getLeftOperand(),
                            binaryOp.getOperation(),
                            binaryOp.getRightOperand()
                    );
            return comp.equals(otherComp);
        }
        return false;
    }

    private LlLocationVar backPropagateToEliminateCommonExpressionFromCFG(BasicBlock startNode, Computation comp) {

        // we want the same unique temp to replace the common
        // sub-expression everywhere in the graph
        LlLocationVar compTemp = this.cfg.getBuilder().generateTemp();

        // 1) BFS back up the CFG to the point(s) where the original
        // expression was found. Start at the predecessors of this node
        HashSet<BasicBlock> visited = new HashSet<>();
        LinkedList<BasicBlock> queue = new LinkedList<>();

        // we don't want to visit the start ever!
        visited.add(startNode);

        for (BasicBlock pred : startNode.getPredecessors()) {
            queue.add(pred);
        }

        while (queue.size() > 0) {
            // explore a new node
            BasicBlock currentNode = queue.pop();

            // only consider nodes we haven't visited yet
            if (!visited.contains(currentNode)) {
                visited.add(currentNode);

                // need to reconstruct the BasickBlock's stmt's list
                boolean subExpressionWasFound = false;
                ArrayList<String> backwardsLabelsList = new ArrayList<>();

                // loop through the BasicBlock backwards to check if it contains
                // one of the initial DEFs of the common sub-expression
                LinkedHashMap<String, LlStatement> labelstoStmtsMap = currentNode.getLabelsToStmtsMap();
                ArrayList<String> bbStmtLabels = new ArrayList<>(labelstoStmtsMap.keySet());
                for (int i = bbStmtLabels.size()-1; i >= 0; i--) {
                    String stmtlabel = bbStmtLabels.get(i);
                    LlStatement stmt = labelstoStmtsMap.get(stmtlabel);

                    // only attempt to replace the subExpression if it was not already found
                    if (!subExpressionWasFound) {
                        if (stmt instanceof LlAssignStmtUnaryOp) {
                            LlAssignStmtUnaryOp unaryOp = (LlAssignStmtUnaryOp) stmt;

                            if (this.containsUnarySubExpression(unaryOp, comp)) { // we found a match!!!!

                                // create the computation stmt and let its label be the existing stmtLabel
                                LlAssignStmtUnaryOp compStmt =
                                        new LlAssignStmtUnaryOp(
                                                compTemp,
                                                unaryOp.getOperand(),
                                                unaryOp.getOperator()
                                        );
                                labelstoStmtsMap.put(stmtlabel, compStmt);

                                // create the re-assignment stmt and make a new label for for it
                                String reassignLabel = this.cfg.getBuilder().generateLabel();
                                LlAssignStmtRegular reAssignStmt =
                                        new LlAssignStmtRegular(
                                                unaryOp.getStoreLocation(),
                                                compTemp
                                        );
                                backwardsLabelsList.add(reassignLabel);
                                labelstoStmtsMap.put(reassignLabel, reAssignStmt);

                                // once we find the subExpression in the BB, stop looking!
                                subExpressionWasFound = true;
                            }
                        }
                        else if (stmt instanceof LlAssignStmtBinaryOp) {
                            LlAssignStmtBinaryOp binaryOp = (LlAssignStmtBinaryOp) stmt;

                            if (this.containsBinarySubExpression(binaryOp, comp)) { // we found a match!!!

                                // create the computation stmt and let its label be the existing stmtLabel
                                LlAssignStmtBinaryOp compStmt =
                                        new LlAssignStmtBinaryOp(
                                                compTemp,
                                                binaryOp.getLeftOperand(),
                                                binaryOp.getOperation(),
                                                binaryOp.getRightOperand()
                                        );
                                labelstoStmtsMap.put(stmtlabel, compStmt);

                                // create the re-assignment stmt and make a new label for for it
                                String reassignLabel = this.cfg.getBuilder().generateLabel();
                                LlAssignStmtRegular reAssignStmt =
                                        new LlAssignStmtRegular(
                                                binaryOp.getStoreLocation(),
                                                compTemp
                                        );
                                backwardsLabelsList.add(reassignLabel);
                                labelstoStmtsMap.put(reassignLabel, reAssignStmt);

                                // once we find the subExpression in the BB, stop looking!
                                subExpressionWasFound = true;
                            }
                        }
                    }

                    // always add the stmtLabels to correctly re-assemble the BasicBlock
                    backwardsLabelsList.add(stmtlabel);
                }

                // create the new label => stmts map by inserting the labels/stmts
                // from the backwards lists in the reverse order
                LinkedHashMap<String, LlStatement> newBBStmsLabelMap = new LinkedHashMap<>();
                for (int i = backwardsLabelsList.size() - 1; i >= 0 ; i--) {
                    String label = backwardsLabelsList.get(i); // in perfect reverse order
                    LlStatement stmt = labelstoStmtsMap.get(label); // out of order but still a hashmap :P

                    newBBStmsLabelMap.put(label, stmt);
                }
                currentNode.setLabelsToStmtsMap(newBBStmsLabelMap);

                // only add predecessor nodes if the subExpression was not found.
                if (!subExpressionWasFound) {
                    for (BasicBlock pred : currentNode.getPredecessors()) {
                        if (!visited.contains(pred)) {
                            queue.add(pred);
                        }
                    }
                }
            }
        }

        return compTemp; // should never be null
    }
}
