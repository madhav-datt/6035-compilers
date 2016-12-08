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

    public static void performGlobalCommonSubexpressionEliminationOnCFG(CFG cfg) {
        GlobalCSE cse = new GlobalCSE(cfg);
        ArrayList<BasicBlock> basicBlocks = cfg.getBasicBlocks();

        // 1) perform local common subexpression elimination
        for (BasicBlock bb : basicBlocks) {
            LocalCSE.performLocalCSE(bb);
        }

        // get available expression for each BasicBlock in the CFG
        HashMap<BasicBlock, HashSet<Computation>> availExprIN
                = AvailableExpressions.getAvailableExpressionsForCFG(cfg);

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
                            LlLocationVar compTemp = cse.eliminateCommonSubExpressionThroughoutCFG(bb, label, comp);
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
                            LlLocationVar compTemp = cse.eliminateCommonSubExpressionThroughoutCFG(bb, label, comp);
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

    private LlLocationVar eliminateCommonSubExpressionThroughoutCFG(BasicBlock startNode, String expLineLabel, Computation comp) {
        LlLocationVar compTemp = null; // contains the tempVar that will replace the common sub-expression

        // 1) BFS back up the CFG to the point(s) where the original expression was found
        HashSet<BasicBlock> visited = new HashSet<>();
        LinkedList<BasicBlock> queue = new LinkedList<>();
        queue.add(startNode);

        while (queue.size() > 0) {
            // need to reconstruct the BasickBlock's stmt's list
            ArrayList<String> backwardsLabelsList = new ArrayList<>();
            ArrayList<LlStatement> backwardsStmtList = new ArrayList<>();
            LinkedHashMap<String, LlStatement> newBBStmsLabelMap = new LinkedHashMap<>();

            // explore a new node
            BasicBlock currentNode = queue.pop();
            visited.add(currentNode);

            // check to see if this BasicBlock contains the initial expr DEF
            LinkedHashMap<String, LlStatement> labelstoStmtsMap = currentNode.getLabelsToStmtsMap();
            ArrayList<String> bbStmtLabels = new ArrayList<>(labelstoStmtsMap.keySet());
            for (int i = bbStmtLabels.size(); i >= 0; i--) {
                String stmtlabel = bbStmtLabels.get(i);
                LlStatement stmt = labelstoStmtsMap.get(stmtlabel);

                if (stmt instanceof LlAssignStmtUnaryOp) {
                    LlAssignStmtUnaryOp unaryOp = (LlAssignStmtUnaryOp) stmt;

                    // we found a match!!!!
                    if (this.containsUnarySubExpression(unaryOp, comp)) {

                        // create new labels
                        String compLabel = this.cfg.getBuilder().generateLabel();
                        backwardsLabelsList.add(compLabel);
                        String reassignLabel = this.cfg.getBuilder().generateLabel();
                        backwardsLabelsList.add(reassignLabel);

                        // create the new stmts
                        compTemp = this.cfg.getBuilder().generateTemp();
                        LlAssignStmtUnaryOp compStmt =
                                new LlAssignStmtUnaryOp(
                                        compTemp,
                                        unaryOp.getOperand(),
                                        unaryOp.getOperator()
                                );
                        backwardsStmtList.add(compStmt);
                        LlAssignStmtRegular reAssignStmt =
                                new LlAssignStmtRegular(
                                        unaryOp.getStoreLocation(),
                                        compTemp
                                );
                        backwardsStmtList.add(reAssignStmt);

                        continue; // we don't want to add the stmt/label below as well
                    }
                }
                else if (stmt instanceof LlAssignStmtBinaryOp) {
                    LlAssignStmtBinaryOp binaryOp = (LlAssignStmtBinaryOp) stmt;

                    // we found a match!!!
                    if (this.containsBinarySubExpression(binaryOp, comp)) {

                        // create new labels
                        String compLabel = this.cfg.getBuilder().generateLabel();
                        backwardsLabelsList.add(compLabel);
                        String reassignLabel = this.cfg.getBuilder().generateLabel();
                        backwardsLabelsList.add(reassignLabel);

                        // create the new stmts
                        compTemp = this.cfg.getBuilder().generateTemp();
                        LlAssignStmtBinaryOp compStmt =
                                new LlAssignStmtBinaryOp(
                                        compTemp,
                                        binaryOp.getLeftOperand(),
                                        binaryOp.getOperation(),
                                        binaryOp.getRightOperand()
                                );
                        backwardsStmtList.add(compStmt);
                        LlAssignStmtRegular reAssignStmt =
                                new LlAssignStmtRegular(
                                        binaryOp.getStoreLocation(),
                                        compTemp
                                );
                        backwardsStmtList.add(reAssignStmt);

                        continue; // we don't want to add the stmt/label below as well
                    }
                }

                // keep track of the order of the BasicBlock's stmt's list
                backwardsLabelsList.add(stmtlabel);
                backwardsStmtList.add(stmt);
            }

            // re-assign the BasicBlock's label=>stmt map to the
            // updated label=>stmt map
            for (int i = 0; i < backwardsLabelsList.size(); i++) {
                String label = backwardsLabelsList.get(0);
                LlStatement stmt = backwardsStmtList.get(0);
                newBBStmsLabelMap.put(label, stmt);
            }
            startNode.setLabelsToStmtsMap(newBBStmsLabelMap);


            // add unvisited nodes to the queue
            for (BasicBlock pred : currentNode.getPredecessors()) {
                if (!visited.contains(pred)) {
                    queue.add(pred);
                }
            }
        }
        return compTemp; // should never be null
    }
}
