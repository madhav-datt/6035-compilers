package edu.mit.compilers.cfg;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by devinmorgan on 12/10/16.
 */
public class LoopAnalysis {
    private final CFG cfg;
    private final HashMap<BasicBlock, HashSet<BasicBlock>>;

    private LoopAnalysis(CFG cfg) {
        this.cfg = cfg;
    }


    private static HashMap<BasicBlock, HashSet<BasicBlock>> dom(CFG cfg) {
        HashSet<BasicBlock> allNodes = new HashSet<>(cfg.getBasicBlocks());
        HashMap<BasicBlock, HashSet<BasicBlock>> dominatorsMap = new HashMap<>();

        // D[root] = {root}
        BasicBlock root = cfg.getRootBasicBlock();
        HashSet<BasicBlock> rootDominatorSet = new HashSet<>();
        rootDominatorSet.add(root);
        dominatorsMap.put(root, rootDominatorSet);

        // initialize D[n] = N
        for (BasicBlock bb : cfg.getBasicBlocks()) {
            dominatorsMap.put(bb, new HashSet<>(allNodes));
        }

        // N = N - {root}
        allNodes.remove(root);

        // while D changes do
        HashMap<BasicBlock, HashSet<BasicBlock>> oldMap = dominatorsMap;
        do {
            for (BasicBlock bb : allNodes) {

                // Intersection ( D[p] for each p in predecessors[n] )
                HashSet<BasicBlock> dominators = new HashSet<>(allNodes);
                for (BasicBlock pred : bb.getPredecessors()) {
                    dominators.retainAll(dominatorsMap.get(pred));
                }

                // D[n] = {n} Union ( Intersection ( D[p] for each p in predecessors[n] ) )
                dominators.add(bb);
                dominatorsMap.put(bb, dominators);
            }
        } while (!dominatorsMap.equals(oldMap));

        return dominatorsMap;
    }
}
