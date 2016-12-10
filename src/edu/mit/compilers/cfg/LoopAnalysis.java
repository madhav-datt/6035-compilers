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

    // returns a map where there is a key for each node in the CFG
    // and the corresponding value is the set of all nodes that
    // dominate the key node
    private static HashMap<BasicBlock, HashSet<BasicBlock>> getDominatorsMap(CFG cfg) {
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

    // returns a map where there is a key for each node in the CFG
    // and the corresponding value is the set of all nodes that
    // STRICTLY dominate the key node (i.e. the set doesn't
    // contain the key node)
    private static HashMap<BasicBlock, HashSet<BasicBlock>> getStrictDominatorsMap(CFG cfg) {
        HashMap<BasicBlock, HashSet<BasicBlock>> dominatorsMap = LoopAnalysis.getDominatorsMap(cfg);

        // loop through the dominators map and remove the
        // BasicBlock from its dominators set
        for (BasicBlock bb : dominatorsMap.keySet()) {
            dominatorsMap.get(bb).remove(bb);
        }

        return dominatorsMap;
    }

    // returns a map where there is a key for each node in the CFG
    // and the corresponding value is the set of all nodes that
    // IMMEDIATELY dominate the key node (i.e. the set contains
    // exactly 1 element)
    private static HashMap<BasicBlock, HashSet<BasicBlock>> getImmediateDominatorsMap(CFG cfg) {
        HashMap<BasicBlock, HashSet<BasicBlock>> strictDomMap = LoopAnalysis.getStrictDominatorsMap(cfg);

        // A is in idom(B) if there is no C such that A is in sdom(C) and both A and C are in sdom(B)
        for (BasicBlock B : strictDomMap.keySet()) {

            // we will mutate strictDomMap.get(B) (in the if-stmt below)
            // while iterating over it so we need to make a defensive copy
            HashSet<BasicBlock> sdom = new HashSet<>(strictDomMap.get(B));

            for (BasicBlock A : sdom) {

                // we will mutate sdom while iterating over it so we need
                // to make a defensive copy
                HashSet<BasicBlock> sdomMinusA = new HashSet<>(sdom);
                sdomMinusA.remove(A);

                for (BasicBlock C: sdomMinusA) {

                    // if A is in sdom(C) then it can't be in idom(B)
                    if (strictDomMap.get(C).contains(A)) {
                        strictDomMap.get(B).remove(C);
                    }
                }
            }
        }

        return strictDomMap; // each set should contain exactly 1 element
    }

}
