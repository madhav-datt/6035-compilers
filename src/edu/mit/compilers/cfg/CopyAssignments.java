package edu.mit.compilers.cfg;

import edu.mit.compilers.ll.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

/**
 * Created by devinmorgan on 12/4/16.
 */
public class CopyAssignments {
    private HashSet<Quadruple> universalSet = new HashSet<>();
    private final HashMap<BasicBlock, HashSet<Quadruple>> availCopyIN = new HashMap<>();
    private final HashMap<BasicBlock, HashSet<Quadruple>> availCopyOUT = new HashMap<>();

    private CopyAssignments(CFG cfg) {
        ArrayList<BasicBlock> bbList = cfg.getBasicBlocks();

        // 1) initial allExpressions with the union of all EVAL(bb)'s
        for (BasicBlock bb : bbList) {
            this.universalSet.addAll(COPY(bb));
        }

        // 2) perform the worklist algorithm

        // IN[n] = E for all nodes,  OUT[n] = empty for all nodes
        for (BasicBlock bb: bbList) {
            this.availCopyIN.put(
                    bb, new HashSet<>(this.universalSet) // prevent mutability issues
            );
            this.availCopyOUT.put(bb, new HashSet<>());
        }

        ArrayList<BasicBlock> activeNodes = new ArrayList<>(bbList);
        BasicBlock entry = activeNodes.get(0);

        // IN[Entry] = emptyset;
        this.availCopyIN.put(entry, new HashSet<>());

        // OUT[Entry] = EVAL[Entry];
        this.availCopyOUT.put(entry, COPY(entry));

        // Changed = N - {Entry};
        activeNodes.remove(0); // remove the entry since it was accounted for

        while (activeNodes.size() > 0) {

            // get a node and remove it from active nodes
            BasicBlock node = activeNodes.get(0);
            activeNodes.remove(0);
            HashSet<Quadruple> oldOUT = this.availCopyOUT.get(node);

            // IN[n] = IN[n] intersect OUT[p] for all p in predecessors
            HashSet<Quadruple> IN = this.availCopyIN.get(node); // IN[n] = E
            for (BasicBlock pred : node.getPredecessors()) {
                IN.retainAll(this.availCopyOUT.get(pred));
            }
            if (node.getPredecessors().size() == 0) {
                // IN should be there empty set if there are no predecessors
                IN = new HashSet<>();
            }
            this.availCopyIN.put(node, IN);

            // KILL[n]
            HashSet<Quadruple> KILL = KILL(node, new HashSet<>(this.universalSet));

            // (IN[n]-KILL[n])
            HashSet<Quadruple> INminusKILL = new HashSet<>(this.availCopyIN.get(node)); // make separate copy of IN
            INminusKILL.removeAll(KILL);

            // OUT[n] = EVAL[n] U (IN[n]-KILL[n])
            HashSet<Quadruple> COPYplusINminusKILL = COPY(node);
            COPYplusINminusKILL.addAll(INminusKILL);
            this.availCopyOUT.put(node, COPYplusINminusKILL);

            // if OUT[n] changed, add its successors to activeNodes
            if (!this.availCopyOUT.get(node).equals(oldOUT)) {
                if (node.getDefaultBranch() != null) {
                    activeNodes.add(node.getDefaultBranch());
                }
                if (node.getAlternativeBranch() != null) {
                    activeNodes.add(node.getAlternativeBranch());
                }
            }
        }
    }

    public static HashMap<BasicBlock, HashMap<LlLocation, LlComponent>> getCopyAssignmentsForCFG(CFG cfg) {
        CopyAssignments ae = new CopyAssignments(cfg);
        HashMap<BasicBlock, HashMap<LlLocation, LlComponent>> bbToCopyTableMap = new HashMap<>();

        // loop through each BasicBlock
        for (BasicBlock bb : cfg.getBasicBlocks()) {
            HashMap<LlLocation, LlComponent> copyTable = new HashMap<>();

            // populate the copyTable for the BasicBlock
            for (Quadruple quad : ae.availCopyIN.get(bb)) {
                LlLocationVar u = quad.getU();
                LlComponent v = quad.getV();
                copyTable.put(u, v);
            }
            bbToCopyTableMap.put(bb, copyTable);
        }
        return bbToCopyTableMap;
    }

    // returns the set of quadruples (u, v, block, pos) such
    // that u <-- v is a copy assignment and pos is the position
    // in the block where the copy assignment occurs AND
    // neither u nor v is assigned to later in the block
    private HashSet<Quadruple> COPY(BasicBlock bb) {
        LinkedHashMap<String, LlStatement> labelsToStmtsMap = bb.getLabelsToStmtsMap();
        HashSet<Quadruple> copyAssignCandidates = new HashSet<>();

        // check each stmt to see if it is a copy Assignment
        for (String label : labelsToStmtsMap.keySet()) {
            LlStatement stmt = labelsToStmtsMap.get(label);

            // we only care about assignment statements
            if (stmt instanceof LlAssignStmt) {
                LlAssignStmt assignStmt = (LlAssignStmt) stmt;

                // first check to see if any existing Quadruples become invalid
                // as a result of the current assignment (or re-assignment)
                if (assignStmt.getStoreLocation() instanceof LlLocationVar) {
                    LlLocationVar storeLoc = (LlLocationVar) assignStmt.getStoreLocation();

                    // the u or v just got reassigned in this stmt, remove the corresponding Quadruple
                    for (Quadruple quad : new HashSet<>(copyAssignCandidates)) {
                        if (quad.containsVar(storeLoc)) {
                            copyAssignCandidates.remove(quad);
                        }
                    }
                }

                // check each LlAssignStmtRegular in case it is a copyAssignment
                if (stmt instanceof LlAssignStmtRegular) {
                    LlAssignStmtRegular stmtRegular = (LlAssignStmtRegular) stmt;

                    // if the AssignStmt is qualified to be a copyAssignment,
                    // make a quad and add it to the candidates list
                    if (!(stmtRegular.getStoreLocation() instanceof LlLocationArray)
                            && !(stmtRegular.getOperand() instanceof LlLocationArray)) {

                        LlLocationVar u = (LlLocationVar) stmtRegular.getStoreLocation();
                        LlComponent v = stmtRegular.getOperand();
                        Quadruple quad = new Quadruple(u, v, bb, label);
                        copyAssignCandidates.add(quad);
                    }
                }
            }

        }

        return copyAssignCandidates;
        // 1) loop through each stmt and look for IrAssignStmtRegular's.
        // 2) Create a quadruple for each u <-- v and add it to the candidates set
        // 3) any time you see an IrAssignStmt, check each Quadruple in the candidates set to see if the
        // storeLocation is the u or v in (u, v, i, pos). if it is, remove that Quadruple from the candidates set
        // 4) all candidates that did not get reassigned, constitue the COPY(i) set
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

                    // add to the killedSet all quads that did not originate in this BasicBlock
                    // and whose u or v get reassigned by the current assignStmt
                    for (Quadruple quad : superSet) {
                        if (quad.getBlock() != bb && quad.containsVar(var)) {
                            killedSet.add(quad);
                        }
                    }
                }
            }
        }
        return killedSet;
    }

    public class Quadruple {
        // the quadruple is of the form
        // (u, v, i, pos) which represents u <-- v; @ instruction pos in block i
        private final LlLocationVar u;
        private final LlComponent v;
        private final BasicBlock block;
        private final String stmtLabel;

        public Quadruple(LlLocationVar u, LlComponent v, BasicBlock block, String stmtLabel) {
            this.u = u;
            this.v = v;
            this.block = block;
            this.stmtLabel = stmtLabel;
        }

        public LlLocationVar getU() {
            return this.u;
        }

        public LlComponent getV() {
            return this.v;
        }

        public boolean containsVar(LlLocationVar var) {
            return this.u.equals(var) || this.v.equals(var);
        }

        public BasicBlock getBlock() {
            return block;
        }

        @Override
        public String toString() {
            return "(" + this.u.toString() + ", " + this.v.toString() + ")";
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Quadruple) {
                Quadruple that = (Quadruple) obj;

                // two quadruples will be equal if they have equivalent u and v's
                return this.u.equals(that.u) && this.v.equals(that.v);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return this.u.hashCode() * this.v.hashCode();
        }
    }
}