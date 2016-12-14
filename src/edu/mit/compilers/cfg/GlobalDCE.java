package edu.mit.compilers.cfg;

import edu.mit.compilers.ll.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

/**
 * Created by devinmorgan on 12/4/16.
 */
public class GlobalDCE {

    // mutates the CFG by performing Global Dead Code Elimination
    public static void performGlobalDeadCodeElimination(CFG cfg) {
//        HashMap<BasicBlock, HashSet<BlockLabelPair>> deadCodeMap = LivenessAnalysis.getLivenessAnalysisForCFG(cfg);

        // TODO: Make sure that you do not remove assignment stmts for variables from other scopes
        // for example, i = 5 might look like dead code but it's not if i was declared in the scope
        // above

        // iterate through each basic block where there is dead code
//        for (BasicBlock bb : deadCodeMap.keySet()) {
//            LinkedHashMap<String, LlStatement> labelsToStmtsMap = bb.getLabelsToStmtsMap();
//            HashSet<BlockLabelPair> deadCode = deadCodeMap.get(bb);
//
//            // loop through each line of dead code and erase it
//            for (BlockLabelPair tuple : deadCode) {
//                String label = tuple.getLabel();
//                LlStatement stmt = labelsToStmtsMap.get(label);
//
//                if (stmt instanceof LlAssignStmtRegular) {
//                    labelsToStmtsMap.remove(label);
//                }
//                if (stmt instanceof LlAssignStmtUnaryOp) {
//                    labelsToStmtsMap.remove(label);
//                }
//                // make sure that it doesn't remove divide by zero
//                if (stmt instanceof LlAssignStmtBinaryOp) {
//                    LlAssignStmtBinaryOp binaryOp = (LlAssignStmtBinaryOp) stmt;
//                    if (binaryOp.getRightOperand().equals("/")) {
//                        LlComponent rightOp = binaryOp.getRightOperand();
//
//                        // if the divisor is the literal 0, don't remove
//                        if (rightOp instanceof LlLiteralInt) {
//                            LlLiteralInt divisor = (LlLiteralInt) rightOp;
//                            if (divisor.getIntValue() == 0) {
//                                continue;
//                            }
//                        }
//
//                        // since we can't be sure that it's not zero, don't remove it
//                        else {
//                            continue;
//                        }
//                    }
//                }
//            }
        }
//    }
}