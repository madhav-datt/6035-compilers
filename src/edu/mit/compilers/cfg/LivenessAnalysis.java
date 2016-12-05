package edu.mit.compilers.cfg;

import edu.mit.compilers.ll.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

/**
 * Created by devinmorgan on 12/4/16.
 */
public class LivenessAnalysis {

    public HashMap<BasicBlock, HashMap<String, Boolean>> performLivenessAnalysis(CFG cfg) {

    }

    // returns the set of (block, label) pairs that represent LlAssignStmts
    // where the storeLocation has been DEFined and may now be used below
    private HashSet<> DEF(BasicBlock bb) {
        /*
            SET_OF_NEW_DEFS = (bb, label) --> the location of the assignStmt
            for each stmt in bb
                if stmt is an assignStmt
                    add def for the storeLoc to SET_OF_NEW_DEFS
         */

    }

    // returns the set of (block, label) paris that represent LlAssignStmts
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
