package edu.mit.compilers.cfg;

import edu.mit.compilers.ll.*;

import java.util.*;

/**
 * Created by devinmorgan on 11/27/16.
 */
public class GlobalDCE {

    // Given: CFG, (table[label, variable] => liveness::=[true,false])
    public static CFG performGlobalDCE(CFG cfg, GlobalLivenessTable livenessTable) {

        // loop through each basic block in CFG
        for (BasicBlock bb : cfg.getBasicBlocks()) {
            LinkedHashMap<String, LlStatement> optimizedMap = new LinkedHashMap<>();

            // loop through each stmt in basic block
            for (String label: bb.getLabelsToStmtsMap().keySet()) {
                LlStatement stmt = bb.getLabelsToStmtsMap().get(label);

                if (stmt instanceof LlAssignStmtRegular
                        || stmt instanceof LlAssignStmtUnaryOp
                        || stmt instanceof LlAssignStmtBinaryOp) {
                    LlAssignStmt assignStmt = (LlAssignStmt) stmt;

                    if (livenessTable.livenessOf(label, assignStmt.getStoreLocation())) {
                        // don't add stmt to the new map if it's dead
                    }
                    else { optimizedMap.put(label, stmt); } // add the stmt if its storeLocation is live
                }
                else { optimizedMap.put(label, stmt); } // add the stmt if its not an assignment
            }
            // swap out BB's stmtsList with the optimized stmts list
            bb.setLabelsToStmtsMap(optimizedMap);
        }
        return cfg; // a mutated version of the original cfg
    }


}

/*
    do
        for each node visited using BFS (starting from the exitNode, stop when all nodes have been processed):
            for each stmt at each node visited

                do nothing for any variables not mentioned in the current stmt

    while (initialTable != newTable)
 */