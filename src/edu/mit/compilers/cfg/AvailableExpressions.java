package edu.mit.compilers.cfg;

import edu.mit.compilers.ll.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

/**
 * Created by devinmorgan on 12/3/16.
 */
public class AvailableExpressions {

    public ArrayList<HashSet<Computation>> getAvailableExpressionsForCFG(CFG cfg) {
        /*
            1) iterate over all the basic blocks in the CFG to create EVAL(i)
         */
    }

    // returns the set of expressions that are generated
    // in the BasicBlock AND are still available at the
    // end of the BasicBlock
    public HashSet<Computation> EVAL(BasicBlock bb) {
        HashSet<Computation> generatedExpressions = new HashSet<>();

        LinkedHashMap<String, LlStatement> labelsToStmtsMap = bb.getLabelsToStmtsMap();
        for (String label : labelsToStmtsMap.keySet()) {
            LlStatement stmt = labelsToStmtsMap.get(label);

            // TODO: Handle a[i] = -a[i] (+ a[i]). That is, handle arrays!!
            if (stmt instanceof LlAssignStmtUnaryOp) {
                LlAssignStmtUnaryOp unaryOp = (LlAssignStmtUnaryOp) stmt;

                // add all new computations to generatedExpressions
                Computation uniComp = Computation.createUnaryComputation(
                        unaryOp.getOperator(),
                        unaryOp.getOperand()
                );
                generatedExpressions.add(uniComp);
            }
            else if (stmt instanceof LlAssignStmtBinaryOp) {
                LlAssignStmtBinaryOp binaryOp = (LlAssignStmtBinaryOp) stmt;

                // add all new computations to generatedExpressions
                Computation binComp = Computation.createBinaryComputation(
                        binaryOp.getLeftOperand(),
                        binaryOp.getOperation(),
                        binaryOp.getRightOperand()
                );
                generatedExpressions.add(binComp);
            }

            // remove all expressions that contain the storeLocation
            // variable from generatedExpressions because those
            // computations are no longer valid
            if (stmt instanceof LlAssignStmt) {
                LlAssignStmt assignStmt = (LlAssignStmt) stmt;
                for (Computation comp : new HashSet<>(generatedExpressions)) {
                    if (comp.contains(assignStmt.getStoreLocation())) {
                        generatedExpressions.remove(comp);
                    }
                }
            }

        }
        return generatedExpressions;
    }
}
