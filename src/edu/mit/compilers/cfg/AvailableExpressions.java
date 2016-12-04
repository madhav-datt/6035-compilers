package edu.mit.compilers.cfg;

import edu.mit.compilers.ll.LlAssignStmtBinaryOp;
import edu.mit.compilers.ll.LlAssignStmtUnaryOp;
import edu.mit.compilers.ll.LlComponent;
import edu.mit.compilers.ll.LlStatement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

/**
 * Created by devinmorgan on 12/3/16.
 */
public class AvailableExpressions {

    public ArrayList<HashSet<Computation>> getAvailableExpressionsForCFG(CFG cfg) {
        /*
            1) iterate over all the basic blocks in the CFG to create EVAL(i)
         */
    }

    // returns the set of expressions that are generated
    // in the BasicBlock AND are still available at the
    // end of the BasicBlock
    public HashSet<Computation> EVAL(BasicBlock bb) {
        HashSet<Computation> generatedExpressions = new HashSet<>();

        LinkedHashMap<String, LlStatement> labelsToStmtsMap = bb.getLabelsToStmtsMap();
        for (String label : labelsToStmtsMap.keySet()) {
            LlStatement stmt = labelsToStmtsMap.get(label);

            // TODO: Handle a[i] = -a[i] (+ a[i]). That is, handle arrays!!
            if (stmt instanceof LlAssignStmtUnaryOp) {
                LlAssignStmtUnaryOp unaryOp = (LlAssignStmtUnaryOp) stmt;

                // we only care about cases where the
                // store location is NOT the same as the read location
                // If i = i + 1 then i+1 is generated FIRST but then
                // immediately killed
                LlComponent storeLoc = unaryOp.getStoreLocation();
                String operation = unaryOp.getOperator();
                LlComponent operand = unaryOp.getOperand();
                if (!storeLoc.equals(operand)) {
                    Computation uniComp = Computation.createUnaryComputation(operation, operand);
                    generatedExpressions.add(uniComp);
                }
            }
            else if (stmt instanceof LlAssignStmtBinaryOp) {
                LlAssignStmtBinaryOp binaryOp = (LlAssignStmtBinaryOp) stmt;

                // we only care about cases where the
                // store location is NOT the same as the read location
                // If i = i + 1 then i+1 is generated FIRST but then
                // immediately killed
                LlComponent storeLoc = binaryOp.getStoreLocation();
                LlComponent op1 = binaryOp.getLeftOperand();
                String operation = binaryOp.getOperation();
                LlComponent op2 = binaryOp.getRightOperand();
                if (!storeLoc.equals(op1) && !storeLoc.equals(op2)) {
                    Computation binComp = Computation.createBinaryComputation(op1, operation, op2);
                    generatedExpressions.add(binComp);
                }
            }
        }

    }
}
