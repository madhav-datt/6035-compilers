package edu.mit.compilers.cfg;

import edu.mit.compilers.ll.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

/**
 * Created by devinmorgan on 12/4/16.
 */
public class AvailableCopyAssignmnets {
    private HashSet<Computation> universalSet = new HashSet<>();
    private final HashMap<BasicBlock, HashSet<Computation>> availCopyIN = new HashMap<>();
    private final HashMap<BasicBlock, HashSet<Computation>> availCopyOUT = new HashMap<>();

    private AvailableCopyAssignmnets(CFG cfg) {
        ArrayList<BasicBlock> bbList = cfg.getBasicBlocks();

        // 1) initial allExpressions with the union of all EVAL(bb)'s
        for (BasicBlock bb : bbList) {
            this.universalSet.addAll(EVAL(bb));
        }

        // 2) perform the worklist algorithm

        // OUT[n] = E for all nodes
        for (BasicBlock bb: bbList) {
            this.availCopyOUT.put(
                    bb, new HashSet<>(this.universalSet) // prevent mutability issues
            );
        }

        ArrayList<BasicBlock> activeNodes = new ArrayList<>(bbList);
        BasicBlock entry = activeNodes.get(0);

        // IN[Entry] = emptyset;
        this.availCopyIN.put(entry, new HashSet<>());

        // OUT[Entry] = EVAL[Entry];
        this.availCopyOUT.put(entry, EVAL(entry));

        // Changed = N - {Entry};
        activeNodes.remove(0); // remove the entry since it was accounted for

        while (activeNodes.size() > 0) {

            // get a node and remove it from active nodes
            BasicBlock node = activeNodes.get(0);
            activeNodes.remove(0);
            HashSet<Computation> oldOUT = this.availCopyOUT.get(node);

            // IN[n] = IN[n] intersect OUT[p] for all p in predecessors
            HashSet<Computation> IN = new HashSet<>(this.universalSet); // IN[n] = E
            for (BasicBlock pred : node.getPredecessors()) {
                IN.removeAll(this.availCopyOUT.get(pred));
            }
            this.availCopyIN.put(node, IN);

            // KILL[n]
            HashSet<Computation> KILL = KILL(node, new HashSet<>(this.universalSet));

            // (IN[n]-KILL[n])
            HashSet<Computation> INminusKILL = new HashSet<>(this.availCopyIN.get(node)); // make separate copy of IN
            INminusKILL.removeAll(KILL);

            // OUT[n] = EVAL[n] U (IN[n]-KILL[n])
            HashSet<Computation> EVALplusINminusKILL = EVAL(node);
            EVALplusINminusKILL.addAll(INminusKILL);
            this.availCopyOUT.put(node, EVALplusINminusKILL);

            // if OUT[n] changed, add its successors to activeNodes
            if (!this.availCopyOUT.equals(oldOUT)) {
                if (node.getDefaultBranch() != null) {
                    activeNodes.add(node.getDefaultBranch());
                }
                if (node.getAlternativeBranch() != null) {
                    activeNodes.add(node.getAlternativeBranch());
                }
            }
        }
    }

    public static HashMap<BasicBlock, HashSet<Computation>> getAvailableExpressionsForCFG(CFG cfg) {
        AvailableCopyAssignmnets ae = new AvailableCopyAssignmnets(cfg);
        return ae.availCopyIN;
    }

    // returns the set of quadruples (u, v, block, pos) such
    // that u <-- v is a copy assignment and pos is the position
    // in the block where the copy assignment occurs AND
    // neither u nor v is assigned to later in the block
    private HashSet<Quadruple> COPY(BasicBlock bb) {
        LinkedHashMap<String, LlStatement> labelsToStmtsMap = bb.getLabelsToStmtsMap();
        HashSet<LlLocationVar> candidates = new HashSet<>();
        HashMap<String, Quadruple> labelToQuadMap = new HashMap<>();
        ArrayList<String> reverseOrderStmts = new ArrayList<>(labelsToStmtsMap.keySet());

        // TODO: Figure out how to handle a[i] = b[i]...That is, figure out arrays!!

        // 1) loop through all the stmts backwards
        for (int i = reverseOrderStmts.size()-1; i >=0; i--) {
            String label = reverseOrderStmts.get(i);
            LlStatement instr = labelsToStmtsMap.get(label);

            // 2) add all LlLocationVars in the RHS of the assignment
            //      stmts to the candidates set of LlLocationVars
            if (instr instanceof LlAssignStmtRegular) {
                LlAssignStmtRegular regular = (LlAssignStmtRegular) instr;
                if (regular.getOperand() instanceof LlLocationVar) {
                    LlLocationVar var = (LlLocationVar) regular.getOperand();
                    candidates.add(var);
                }
            } else if (instr instanceof LlAssignStmtUnaryOp) {
                LlAssignStmtUnaryOp unary = (LlAssignStmtUnaryOp) instr;
                if (unary.getOperand() instanceof LlLocationVar) {
                    LlLocationVar var = (LlLocationVar) unary.getOperand();
                    candidates.add(var);
                }
            } else if (instr instanceof LlAssignStmtBinaryOp) {
                LlAssignStmtBinaryOp binary = (LlAssignStmtBinaryOp) instr;

                if (binary.getLeftOperand() instanceof LlLocationVar) {
                    LlLocationVar var = (LlLocationVar) binary.getLeftOperand();
                    candidates.add(var);
                }

                if (binary.getRightOperand() instanceof LlLocationVar) {
                    LlLocationVar var = (LlLocationVar) binary.getRightOperand();
                    candidates.add(var);
                }
            }

            // 3) if you see an LlAssignmentStmtRegular (i.e. u <-- v)
            //      and u is in the candidate set, add (u,v, block, label)
            //      to the set of qualified candidate sets
            if (instr instanceof LlAssignStmtRegular) {
                LlAssignStmtRegular regular = (LlAssignStmtRegular) instr;
                if (regular.getStoreLocation() instanceof LlLocationVar) {
                    LlLocationVar u = (LlLocationVar) regular.getStoreLocation();

                    if (candidates.contains(u)) {
                        LlLocationVar v = (LlLocationVar) regular.getOperand();
                        Quadruple quad = new Quadruple(u, v, bb, label);
                        labelToQuadMap.put(label, quad);
                    }
                }
            }
        }

        // 4) loop through all the stmts forwards now to remove invalid candidates
        HashSet<Quadruple> mightNeedToRemoveSet = new HashSet<>();
        for (String label : labelsToStmtsMap.keySet()) {
            LlStatement stmt = labelsToStmtsMap.get(label);

            // 5) add a quad (u,v,block,label) to mightNeedToRemoveSet
            //      once you have passed the stmt whose label is label
            if (labelToQuadMap.containsKey(label)) {
                Quadruple quad = labelToQuadMap.get(label);
                mightNeedToRemoveSet.add(quad);
            }

            // 6) remove all qualified candidates where the u or v
            //      is a storeLoc for any LlAssignmentStmt if the
            //      if we've passed the stmt u <-- v already
            if (stmt instanceof LlAssignStmt) {
                LlAssignStmt assignStmt = (LlAssignStmt) stmt;
                if (assignStmt.getStoreLocation() instanceof LlLocationVar) {
                    LlLocationVar var = (LlLocationVar) assignStmt.getStoreLocation();

                    // remove the quad if var is either u or v
                    for (String l : labelToQuadMap.keySet()) {
                        Quadruple quad = labelToQuadMap.get(l);
                        if (quad.containsVar(var)) {
                            labelToQuadMap.remove(l);
                        }
                    }
                }
            }
        }

        // 7) return the set of remaining qualified candidates
        HashSet<Quadruple> validCopyAssignments = new HashSet<>();
        for (String label : labelToQuadMap.keySet()) {
            Quadruple quad = labelToQuadMap.get(label);
            validCopyAssignments.add(quad);
        }
        return validCopyAssignments;
    }

    // returns the set of Quadruples (u, v, block, pos) of copy
    // assignment instances killed by bb where block != bb
    private HashSet<Quadruple> KILL(BasicBlock bb, HashSet<Quadruple> superSet) {
        LinkedHashMap<String, LlStatement> labelsToStmtsMap = bb.getLabelsToStmtsMap();
        HashSet<Quadruple> killedSet = new HashSet<>();

        // loop through each stmt in the BasicBlock
        for (String label : labelsToStmtsMap.keySet()) {
            LlStatement stmt = labelsToStmtsMap.get(label);

            // only consider assignment statements where u <-- ? or v <-- ?
            // for some Quadruple (u ,v, ..., ...)
            if (stmt instanceof LlAssignStmt) {
                LlAssignStmt assignStmt = (LlAssignStmt) stmt;
                if (assignStmt.getStoreLocation() instanceof LlLocationVar) {
                    LlLocationVar var = (LlLocationVar) assignStmt.getStoreLocation();
                    for (Quadruple quad : superSet) {

                        // don't consider Quads if block == bb
                        if (!quad.getBlock().equals(bb)) {

                            // if either u or v gets reassigned, add quad to the killed set
                            if (quad.containsVar(var)) {
                                killedSet.add(quad);
                            }
                        }
                    }
                }
            }
        }
        return killedSet;
    }


    private class Quadruple {
        // the quadruple is of the form
        // (u, v, i, pos) which represents u <-- v; @ instruction pos in block i
        private final LlLocationVar u;
        private final LlLocationVar v;
        private final BasicBlock block;
        private final String stmtLabel;

        public Quadruple(LlLocationVar u, LlLocationVar v, BasicBlock block, String stmtLabel) {
            this.u = u;
            this.v = v;
            this.block = block;
            this.stmtLabel = stmtLabel;
        }

        public boolean containsVar(LlLocationVar var) {
            return this.u.equals(var) || this.v.equals(var);
        }

        public BasicBlock getBlock() {
            return block;
        }
    }
}
