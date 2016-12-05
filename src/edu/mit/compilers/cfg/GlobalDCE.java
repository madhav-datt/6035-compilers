package edu.mit.compilers.cfg;

import edu.mit.compilers.ll.LlStatement;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

/**
 * Created by devinmorgan on 12/4/16.
 */
public class GlobalDCE {

    // mutates the CFG by performing Global Dead Code Elimination
    public static void performGlobalDeadCodeElimination(CFG cfg) {
        HashMap<BasicBlock, HashSet<Tuple>> deadCodeMap = LivenessAnalysis.getLivenessAnalysisForCFG(cfg);

        // iterate through each basic block where there is dead code
        for (BasicBlock bb : deadCodeMap.keySet()) {
            LinkedHashMap<String, LlStatement> labelsToStmtsMap = bb.getLabelsToStmtsMap();
            HashSet<Tuple> deadCode = deadCodeMap.get(bb);

            // loop through each line of dead code and e
            for (Tuple tuple : deadCode) {
                String label = tuple.getLabel();
                labelsToStmtsMap.remove(label);
            }
        }
    }
}
