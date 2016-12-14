package edu.mit.compilers.cfg;

import edu.mit.compilers.ll.*;

import java.util.*;

/**
 * Created by devinmorgan on 12/4/16.
 */
public class LivenessAnalysis {
    private final HashSet<BlockLabelPair> universalRequiredDefs = new HashSet<>();
    private final HashMap<BasicBlock, HashSet<BlockLabelPair>> requiredDefsIN = new HashMap<>();
    private final HashMap<BasicBlock, HashSet<BlockLabelPair>> requiredDefsOUT = new HashMap<>();
    private final HashMap<CFG.SymbolDef, HashSet<BlockLabelPair>> defsForUses;
    private final CFG cfg;

    private LivenessAnalysis(CFG cfg) {
        this.cfg = cfg;
        this.defsForUses = this.cfg.getDefsForUseAsBlockLabelPairs();
        ArrayList<BasicBlock> bbList = this.cfg.getBasicBlocks();

        // OUT[n] = EmptySet for all nodes
        for (BasicBlock bb: bbList) {
            this.requiredDefsIN.put(bb, new HashSet<>());
        }

        // Changed = N - {Exit};
        LinkedList<BasicBlock> activeNodes = new LinkedList<>(bbList);
        BasicBlock exit = activeNodes.removeLast();

        // OUT[Exit] = emptyset
        this.requiredDefsOUT.put(exit, new HashSet<>());

        // IN[Exit] = USE[Exit];
        this.requiredDefsIN.put(exit, USE(exit));

        while (activeNodes.size() > 0) {

            // get a node and remove it from active nodes
            BasicBlock node = activeNodes.removeLast();
            HashSet<BlockLabelPair> oldIN = this.requiredDefsIN.get(node);

            // OUT[n] = OUT[n] intersect IN[s] for all s in successors
            HashSet<BlockLabelPair> OUT = new HashSet<>(); // OUT[n] = EmptySet
            if (node.getAlternativeBranch() != null) {
                OUT.retainAll(this.requiredDefsIN.get(node.getAlternativeBranch()));
            }
            if (node.getDefaultBranch() != null) {
                OUT.retainAll(this.requiredDefsIN.get(node.getDefaultBranch()));
            }
            if (node.getDefaultBranch() == null && node.getAlternativeBranch() == null) {
                this.requiredDefsOUT.put(node, new HashSet<>());
            }
            this.requiredDefsOUT.put(node, OUT);

            // DEF[n]
            HashSet<BlockLabelPair> DEF = DEF(node);

            // (OUT[n]-DEF[n])
            HashSet<BlockLabelPair> OUTminusDEF = new HashSet<>(this.requiredDefsOUT.get(node)); // make separate copy of OUT
            OUTminusDEF.removeAll(DEF);

            // IN[n] = USE[n] U (OUT[n]-DEF[n])
            HashSet<BlockLabelPair> USEplusOUTminusDEF = USE(node);
            USEplusOUTminusDEF.addAll(OUTminusDEF);
            this.requiredDefsIN.put(node, USEplusOUTminusDEF);

            // if IN[n] changed, add its predecessors to activeNodes
            if (!this.requiredDefsIN.get(node).equals(oldIN)) {
                for (BasicBlock pred : node.getPredecessors()) {
                    activeNodes.addFirst(pred);
                }
            }
        }
    }

    // returns the set of (block, labels) that are dead code. That is,
    // this function returns DEF[s] - (OUT[n] union USE[n])
    public static HashMap<BasicBlock, HashSet<BlockLabelPair>> getLivenessAnalysisForCFG(CFG cfg) {
        LivenessAnalysis la = new LivenessAnalysis(cfg);
        HashMap<BasicBlock, HashSet<BlockLabelPair>> bbToTuplesMap = new HashMap<>();

        // populate the map for each BasicBlock
        for (BasicBlock bb : cfg.getBasicBlocks()) {
            HashSet<BlockLabelPair> DEFminusOUTminusUSE = la.DEF(bb);
            DEFminusOUTminusUSE.removeAll(la.requiredDefsOUT.get(bb));
            DEFminusOUTminusUSE.removeAll(la.USE(bb));
            bbToTuplesMap.put(bb, DEFminusOUTminusUSE);
        }
        return bbToTuplesMap;
    }

    // returns the set of (block, label) pairs that represent LlAssignStmts
    // where the storeLocation has been DEFined and may now be used below
    private HashSet<BlockLabelPair> DEF(BasicBlock bb) {
        HashSet<BlockLabelPair> setOfNewDefs = new HashSet<>();
        LinkedHashMap<String, LlStatement> labelsToStmtsMap = bb.getLabelsToStmtsMap();

        for (String label : labelsToStmtsMap.keySet()) {
            LlStatement stmt = labelsToStmtsMap.get(label);

            if (stmt instanceof LlAssignStmt) {
                BlockLabelPair newDef = new BlockLabelPair(bb, label);
                setOfNewDefs.add(newDef);
            }
        }
        return setOfNewDefs;
    }


    // returns the set of (block, label) pairs that represent LlAssignStmts
    // where the storeLocation has a USE somewhere in the BasicBlock
    private HashSet<BlockLabelPair> USE(BasicBlock bb) {
        HashSet<BlockLabelPair> setOfNeededDefs = new HashSet<BlockLabelPair>();
        LinkedHashMap<String, LlStatement> labelsToStmtsMap = bb.getLabelsToStmtsMap();



        // loop through each stmt in the BasicBlock
        for (String label : labelsToStmtsMap.keySet()) {
            LlStatement stmt = labelsToStmtsMap.get(label);

            // AssignStmtsRegular
            if (stmt instanceof LlAssignStmtRegular) {
                LlAssignStmtRegular regular = (LlAssignStmtRegular) stmt;
                if (regular.getOperand() instanceof LlLocationVar) {

                    // add the set of the DEFs needed for this variable USE
                    // to the set of needed defs
                    LlLocationVar var = (LlLocationVar) regular.getOperand();
                    HashSet<BlockLabelPair> defsForUse = GET_DEFS_FOR_USE(bb,label, var);
                    setOfNeededDefs.addAll(defsForUse);
                }
            }

            // AssignStmtsUnary
            else if (stmt instanceof LlAssignStmtUnaryOp) {
                LlAssignStmtUnaryOp unaryOp = (LlAssignStmtUnaryOp) stmt;
                if (unaryOp.getOperand() instanceof LlLocationVar) {

                    // add the set of the DEFs needed for this variable USE
                    // to the set of needed defs
                    LlLocationVar var = (LlLocationVar) unaryOp.getOperand();
                    HashSet<BlockLabelPair> defsForUse = GET_DEFS_FOR_USE(bb,label, var);
                    setOfNeededDefs.addAll(defsForUse);
                }
            }

            // AssignStmtsBinary
            else if (stmt instanceof LlAssignStmtBinaryOp) {
                LlAssignStmtBinaryOp binaryOp = (LlAssignStmtBinaryOp) stmt;

                if (binaryOp.getLeftOperand() instanceof LlLocationVar) {

                    // add the set of the DEFs needed for this variable USE
                    // to the set of needed defs
                    LlLocationVar var = (LlLocationVar) binaryOp.getLeftOperand();
                    HashSet<BlockLabelPair> defsForUse = GET_DEFS_FOR_USE(bb,label, var);
                    setOfNeededDefs.addAll(defsForUse);
                }
                if (binaryOp.getRightOperand() instanceof LlLocationVar) {

                    // add the set of the DEFs needed for this variable USE
                    // to the set of needed defs
                    LlLocationVar var = (LlLocationVar) binaryOp.getRightOperand();
                    HashSet<BlockLabelPair> defsForUse = GET_DEFS_FOR_USE(bb,label, var);
                    setOfNeededDefs.addAll(defsForUse);
                }
            }
        }
        return setOfNeededDefs;
    }

    private HashSet<BlockLabelPair> GET_DEFS_FOR_USE(BasicBlock bb, String label, LlLocationVar var) {

        // hacky way to get the first element from the keySet() of labels
        String blockLeader = "";
        for (String leader : bb.getLabelsToStmtsMap().keySet()) { blockLeader = leader; break; }

        // return the HashSet of BlockLabelPairs associated with the SymbolDef
        CFG.SymbolDef symbolDef = this.cfg.new SymbolDef(var, this.cfg.new Tuple(blockLeader, label));
        return this.defsForUses.get(symbolDef);
    }

}