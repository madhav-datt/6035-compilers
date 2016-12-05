package edu.mit.compilers.cfg;

import edu.mit.compilers.ll.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

/**
 * Created by devinmorgan on 12/4/16.
 */
public class LivenessAnalysis {
    private final HashSet<Tuple> universalRequiredDefs = new HashSet<Tuple>();
    private final HashMap<BasicBlock, HashSet<Tuple>> requiredDefsIN = new HashMap<>();
    private final HashMap<BasicBlock, HashSet<Tuple>> requiredDefsOUT = new HashMap<>();

    private LivenessAnalysis(CFG cfg) {
        ArrayList<BasicBlock> bbList = cfg.getBasicBlocks();

        // 1) initialize universalRequiredDefs with the union of all USE(bb)'s
        for (BasicBlock bb : bbList) {
            this.universalRequiredDefs.addAll(USE(bb));
        }

        // 2) perform the worklist algorithm

        // OUT[n] = E for all nodes
        for (BasicBlock bb: bbList) {
            this.requiredDefsOUT.put(
                    bb, new HashSet<Tuple>(this.universalRequiredDefs) // prevent mutability issues
            );
        }

        ArrayList<BasicBlock> activeNodes = new ArrayList<>(bbList);
        BasicBlock exit = activeNodes.get(activeNodes.size()-1);

        // OUT[Exit] = emptyset
        this.requiredDefsOUT.put(exit, new HashSet<Tuple>());

        // IN[Exit] = USE[Exit];
        this.requiredDefsIN.put(exit, USE(exit));

        // Changed = N - {Exit};
        activeNodes.remove(activeNodes.size()-1); // remove the exit since it was accounted for

        while (activeNodes.size() > 0) {

            // get a node and remove it from active nodes
            BasicBlock node = activeNodes.get(activeNodes.size()-1);
            activeNodes.remove(activeNodes.size()-1);
            HashSet<Tuple> oldIN = this.requiredDefsOUT.get(node);

            // OUT[n] = OUT[n] intersect IN[s] for all s in successors
            HashSet<Tuple> OUT = new HashSet<Tuple>(this.universalRequiredDefs); // OUT[n] = E
            if (node.getAlternativeBranch() != null) {
                OUT.retainAll(this.requiredDefsIN.get(node.getAlternativeBranch()));
            }
            if (node.getDefaultBranch() != null) {
                OUT.retainAll(this.requiredDefsIN.get(node.getAlternativeBranch()));
            }
            if (node.getDefaultBranch() == null && node.getAlternativeBranch() == null) {
                this.requiredDefsOUT.put(node, new HashSet<Tuple>());
            }
            this.requiredDefsOUT.put(node, OUT);

            // DEF[n]
            HashSet<Tuple> DEF = DEF(node);

            // (OUT[n]-DEF[n])
            HashSet<Tuple> OUTminusDEF = new HashSet<Tuple>(this.requiredDefsOUT.get(node)); // make separate copy of OUT
            OUTminusDEF.removeAll(DEF);

            // IN[n] = USE[n] U (OUT[n]-DEF[n])
            HashSet<Tuple> USEplusOUTminusDEF = USE(node);
            USEplusOUTminusDEF.addAll(OUTminusDEF);
            this.requiredDefsIN.put(node, USEplusOUTminusDEF);

            // if IN[n] changed, add its predecessors to activeNodes
            if (!this.requiredDefsIN.equals(oldIN)) {
                for (BasicBlock pred : node.getPredecessors()) {
                    activeNodes.add(0, pred); // at the beginning
                }
            }
        }
    }

    // returns the set of (block, labels) that are dead code. That is,
    // this function returns DEF[s] - (OUT[n] union USE[n])
    public static HashMap<BasicBlock, HashSet<Tuple>> getLivenessAnalysisForCFG(CFG cfg) {
        LivenessAnalysis la = new LivenessAnalysis(cfg);
        HashMap<BasicBlock, HashSet<Tuple>> bbToTuplesMap = new HashMap<>();

        // populate the map for each BasicBlock
        for (BasicBlock bb : cfg.getBasicBlocks()) {
            HashSet<Tuple> DEFminumOUTminusUSE = la.DEF(bb);
            DEFminumOUTminusUSE.removeAll(la.requiredDefsOUT.get(bb));
            DEFminumOUTminusUSE.remove(la.USE(bb));
            bbToTuplesMap.put(bb, DEFminumOUTminusUSE);
        }
        return bbToTuplesMap;
    }

    // returns the set of (block, label) pairs that represent LlAssignStmts
    // where the storeLocation has been DEFined and may now be used below
    private HashSet<Tuple> DEF(BasicBlock bb) {
        HashSet<Tuple> setOfNewDefs = new HashSet<Tuple>();
        LinkedHashMap<String, LlStatement> labelsToStmtsMap = bb.getLabelsToStmtsMap();

        for (String label : labelsToStmtsMap.keySet()) {
            LlStatement stmt = labelsToStmtsMap.get(label);

            if (stmt instanceof LlAssignStmt) {
                Tuple newDef = new Tuple(bb, label);
                setOfNewDefs.add(newDef);
            }
        }
        return setOfNewDefs;
    }

    // returns the set of (block, label) pairs that represent LlAssignStmts
    // where the storeLocation has a USE somewhere in the BasicBlock
    private HashSet<Tuple> USE(BasicBlock bb) {
        HashSet<Tuple> setOfNeededDefs = new HashSet<Tuple>();
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
                    HashSet<Tuple> defsForUse = GET_DEFS_FOR_USE(bb,label, var);
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
                    HashSet<Tuple> defsForUse = GET_DEFS_FOR_USE(bb,label, var);
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
                    HashSet<Tuple> defsForUse = GET_DEFS_FOR_USE(bb,label, var);
                    setOfNeededDefs.addAll(defsForUse);
                }
                if (binaryOp.getRightOperand() instanceof LlLocationVar) {

                    // add the set of the DEFs needed for this variable USE
                    // to the set of needed defs
                    LlLocationVar var = (LlLocationVar) binaryOp.getRightOperand();
                    HashSet<Tuple> defsForUse = GET_DEFS_FOR_USE(bb,label, var);
                    setOfNeededDefs.addAll(defsForUse);
                }
            }
        }
        return setOfNeededDefs;
    }

}
