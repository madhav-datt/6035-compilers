package edu.mit.compilers.cfg;

import java.util.*;


/**
 * Created by devinmorgan on 12/10/16.
 */
public class LoopAnalysis {
    private final CFG cfg;
//    private final HashMap<BasicBlock, HashSet<BasicBlock>>;

    private LoopAnalysis(CFG cfg) {
        this.cfg = cfg;
    }

    public static Loop performLoopInvariantCodeMotion(CFG cfg) {
        // 1) get the loops of this graph in their nested hierarchy

        // 2)
    }

    private static

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
        HashMap<BasicBlock, HashSet<BasicBlock>> oldMap;
        do {
            // cache the old HashMap to check if it changes later
            oldMap = new HashMap<>(dominatorsMap);

            // loop until convergence
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
    private static HashMap<BasicBlock, BasicBlock> getImmediateDominatorsMap(CFG cfg) {
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

        // each set should be a singleton except for the root node which is null
        HashMap<BasicBlock, BasicBlock> iDomMap = new HashMap<>();
        for (BasicBlock bb : strictDomMap.keySet()) {
            for (BasicBlock iDomNode : strictDomMap.get(bb)) {
                iDomMap.put(bb, iDomNode);
            }
        }

        return iDomMap;
    }

    // returns a dictionary where each node points to the set of
    // nodes that are its children in the dominator tree
    private static HashMap<BasicBlock, HashSet<BasicBlock>> getNodeToDominatingMap(CFG cfg) {
        HashMap<BasicBlock, BasicBlock> iDomMap = getImmediateDominatorsMap(cfg);

        // initialize each set to be empty
        HashMap<BasicBlock, HashSet<BasicBlock>> nodeToDominatingSet = new HashMap<>();
        for (BasicBlock bb : iDomMap.keySet()) {
            nodeToDominatingSet.put(bb, new HashSet<>());
        }

        // add the nodes that bb immediately dominates to its set
        for (BasicBlock bb : iDomMap.keySet()) {
            BasicBlock iDom = iDomMap.get(bb);
            nodeToDominatingSet.get(iDom).add(bb);
        }

        // look at each nodes children and add child.children to the nodes set
        // until it converges.
        HashMap<BasicBlock, HashSet<BasicBlock>> old;
        do {
            // cache a copy to check if nodeToDominatingSet changes this iteration
            old = new HashMap<>(nodeToDominatingSet);

            for (BasicBlock bb : nodeToDominatingSet.keySet()) {
                for (BasicBlock succ : new HashSet<>(nodeToDominatingSet.get(bb))) {
                    for (BasicBlock deepChild : nodeToDominatingSet.get(succ)) {
                        nodeToDominatingSet.get(bb).add(deepChild);
                    }
                }
            }
        } while (!old.equals(nodeToDominatingSet));

        return nodeToDominatingSet;
    }

    // returns the set of all the back edges in the CFG
    private static HashSet<BackEdge> getBackEdges(CFG cfg) {
        HashMap<BasicBlock, HashSet<BasicBlock>> dominatingMap = getNodeToDominatingMap(cfg);
        HashSet<BackEdge> backEdges = new HashSet<>();

        // find each back edge that exists in the CFG
        for (BasicBlock bb : dominatingMap.keySet()) {
            for (BasicBlock succ : dominatingMap.get(bb)) {

                // since bb dominates succ, then if bb is the successor of succ, (succ --> bb) is a back edge
                if ((succ.getAlternativeBranch() != null && succ.getAlternativeBranch().equals(bb))
                        || (succ.getDefaultBranch() != null && succ.getDefaultBranch().equals(bb))) {
                    backEdges.add(new BackEdge(bb, succ));
                }
            }
        }
        return backEdges;
    }

    // returns a map where the key is a loop header and the value
    // is the merged loop of all the natural loops that stem from
    // that header
    private static HashMap<BasicBlock, Loop> getHeadersToLoopMap(CFG cfg) {
        HashSet<BackEdge> backEdges = getBackEdges(cfg);
        HashMap<BasicBlock, HashSet<Loop>> headersToLoopsSet = new HashMap<>();

        // Initialization: header --> empty set
        for (BackEdge edge : backEdges) {
            headersToLoopsSet.put(edge.getHeaderNode(), new HashSet<>());
        }

        // add all of the loops to the set corresponding to the header
        for (BackEdge edge : backEdges) {
            Loop loop = Loop.loopForBackEdge(edge);
            headersToLoopsSet.get(edge.getHeaderNode()).add(loop);
        }

        // merge all of the natural loops so each
        // header only corresponds to 1 super loop
        HashMap<BasicBlock, Loop> loopsMap = new HashMap<>();
        for (BasicBlock header : headersToLoopsSet.keySet()) {
            Loop mergedLoop = Loop.mergeLoops(header, headersToLoopsSet.get(header));
            loopsMap.put(header, mergedLoop);
        }

        return loopsMap;
    }

    // returns a map where the key is a loop header and the value is
    // the set of headers that the key immediately dominates
    private static HashMap<BasicBlock, HashSet<BasicBlock>> getHeadersImmediateDominatorsGraph(CFG cfg) {
        HashMap<BasicBlock, Loop> headersToLoops = getHeadersToLoopMap(cfg);
        Set<BasicBlock> headersSet = headersToLoops.keySet();

        // find the set of loop headers that each header immediately dominates
        HashMap<BasicBlock, HashSet<BasicBlock>> headersImmediateDomMap = new HashMap<>();
        for (BasicBlock header : headersToLoops.keySet()) {
            Loop headerLoop = headersToLoops.get(header);

            // we only care about the nodes that are nested headers
            HashSet<BasicBlock> nestedHeaders = new HashSet<>(headerLoop.getLoopBlocks());
            nestedHeaders.retainAll(headersSet);

            // header does not immediately dominate x if there exists a
            // y such that header dominates y but both header and y both dominate x
            HashSet<BasicBlock> immediateNestedHeaders = new HashSet<>(nestedHeaders);
            for (BasicBlock x : nestedHeaders) {

                // for each y in {nestedHeaders} - {x}
                HashSet<BasicBlock> headersMINUSx = new HashSet<>(nestedHeaders);
                headersMINUSx.remove(x);
                for (BasicBlock y : headersMINUSx) {

                    // this is a y that header dominates but this y also
                    // dominates x so header does not immediately dominate x
                    if (headersToLoops.get(y).getLoopBlocks().contains(x)) {
                        immediateNestedHeaders.remove(x);
                    }
                }
            }

            headersImmediateDomMap.put(header, immediateNestedHeaders);
        }
        return headersImmediateDomMap;
    }

    private static class BackEdge {
        private final BasicBlock headerNode;
        private final BasicBlock exitNode;

        public BackEdge(BasicBlock headerNode, BasicBlock exitNode) {
            this.headerNode = headerNode;
            this.exitNode = exitNode;
        }

        public BasicBlock getHeaderNode() {
            return this.headerNode;
        }

        public BasicBlock getExitNode() {
            return this.exitNode;
        }
    }

    private static class Loop {
        private final HashSet<BasicBlock> loopBlocks;
        private final BasicBlock header;

        private Loop(HashSet<BasicBlock> loopBlocks, BasicBlock header) {
            this.loopBlocks = loopBlocks;
            this.header = header;
        }

        public HashSet<BasicBlock> getLoopBlocks() {
            return this.loopBlocks;
        }

        public static Loop loopForBackEdge(BackEdge backEdge) {

            // initialize the set of all nodes in the loop to contain the header node
            HashSet<BasicBlock> loopNodes = new HashSet<>();
            loopNodes.add(backEdge.getHeaderNode());

            // determine the remaining nodes in the loop
            Stack<BasicBlock> stack = new Stack<>();
            while (stack.size() > 0) {

                // consider all of the predecessors of each node
                BasicBlock node = stack.pop();
                for (BasicBlock pred : node.getPredecessors()) {

                    // only consider a node once
                    if (!loopNodes.contains(pred)) {
                        loopNodes.add(pred);
                        stack.push(node);
                    }
                }
            }
            return new Loop(loopNodes, backEdge.getHeaderNode());
        }

        public static Loop mergeLoops(BasicBlock header, HashSet<Loop> loopSet) {
            HashSet<BasicBlock> mergedLoopNodes = new HashSet<>();

            for (Loop loop : loopSet) {
                mergedLoopNodes.addAll(loop.getLoopBlocks());
            }

            return new Loop(mergedLoopNodes, header);
        }

    }

}

/*  Goal: Create a hierarchy of the loops such that H => {headers of immediate nested loops}

    Given:  headersToLoopMap = {Headers => Loop}
            headers = {every loop header}

            for each header in headersToLoopMap.keySet():
                Loop loop
                for x in header


    what if I keep track of all of the nodes that I have visited so far so that I don't visit them again

 */































