package edu.mit.compilers.cfg;

import edu.mit.compilers.ll.*;

import java.util.*;

/**
 * Created by devinmorgan on 11/23/16.
 */
public class BbDag {
    private final HashMap<LlComponent, ArrayList<Node>> compsToUsesMap;
    private final ArrayList<BinaryComputation> computationsList

    public static BbDag createBasicBlockDag(BasicBlock bb) {
        LinkedHashMap<String, LlStatement> labelsToStmtsMap = bb.getLabelsToStmtsMap();
        BbDag dag = new BbDag();

        HashSet<Node> nodes;
        for (String label : labelsToStmtsMap.keySet()) {
            LlStatement stmt = labelsToStmtsMap.get(label);
            // 1) add regular, unary, and binary stmts
            if (stmt instanceof LlAssignStmtBinaryOp) {
                LlAssignStmtBinaryOp binaryOp = (LlAssignStmtBinaryOp) stmt;
                dag.addBinaryComputationToDag(binaryOp, dag);
            }
            else if (stmt instanceof LlAssignStmtUnaryOp) {

            }
            else if (stmt instanceof LlAssignStmtRegular) {

            }
        }
        // TODO: figure out how to incorporate methodCalls, return stmts, and jumps
    }

    private void addBinaryComputationToDag(LlAssignStmtBinaryOp binaryOp) {
        LlComponent leftOperand = binaryOp.getLeftOperand();
        LlComponent rightOperand = binaryOp.getRightOperand();

        // ensure the the left and right components exist in compsToUsesMap
        if (this.getReadCompFromUsedComps(leftOperand) == null) {
            this.addReadCompToUsedComps(leftOperand);
        }
        if (this.getReadCompFromUsedComps(rightOperand) == null) {
            this.addReadCompToUsedComps(rightOperand);
        }

        // create the new computation
        Node leftChild = this.getReadCompFromUsedComps(leftOperand);
        Node rightChild = this.getReadCompFromUsedComps(rightOperand);
        String operator = binaryOp.getOperation();
        LlLocation storeLoc = binaryOp.getStoreLocation();
        BinaryComputation newComputation = new BinaryComputation(storeLoc, leftChild, operator, rightChild);

        // search through computationsList to see if thiscomputation
        // was performed already
        for (BinaryComputation computation : this.computationsList) {

            // don't create another computation if it already exists
            if (newComputation.equals(computation)) {
                computation.addVariableToComputation(storeLoc);
                return;
            }
        }

        // if this computation is unique, add it to the list
        this.computationsList.add(newComputation);
    }

    private Node getReadCompFromUsedComps(LlComponent comp) {
        if (this.compsToUsesMap.containsKey(comp)) {

            // return the most recent use of the comp (i.e. size - 1)
            ArrayList<Node> prevInstances = this.compsToUsesMap.get(comp);
            return prevInstances.get( prevInstances.size() - 1) ;
        }

        return null;
    }

    private Node addReadCompToUsedComps(LlComponent comp) {
        if (!this.compsToUsesMap.containsKey(comp)) {

            // create the initial LeafNode
            LeafNode leaf = new LeafNode(comp);

            // create the prev Instances array and add the leaf
            ArrayList<Node> prevInstances = new ArrayList<>();
            prevInstances.add(leaf);

            // put comp and prevInstances in the comps-->uses map
            this.compsToUsesMap.put(comp, prevInstances);

            // return the LeafNode
            return leaf;
        }

        return null;
    }

    private static abstract class Node { /* TODO: createUnaryNode() */ }

    private static class LeafNode extends Node {
        private final LlComponent comp;

        public LeafNode(LlComponent comp) {
            this.comp = comp;
        }

        public LlComponent getComp() {
            return this.comp;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof LeafNode) {
                LeafNode that = (LeafNode) obj;

                return this.getComp().equals(that.getComp());
            }
            return false;
        }
    }

    private static class BinaryComputationNode extends Node {
        private final BinaryComputation binaryComputation;

        public BinaryComputationNode(BinaryComputation binaryComputation) {
            this.binaryComputation = binaryComputation;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof BinaryComputationNode) {
                BinaryComputationNode that = (BinaryComputationNode) obj;
                return this.binaryComputation.equals(that.binaryComputation);
            }
            return false;
        }
    }

    private static class BinaryComputation {
        private final ArrayList<LlLocation> equivalentVars;
        private final Node leftChild;
        private final String operator;
        private final Node rightChild;

        public BinaryComputation(LlLocation storeLoc, Node leftChild, String operator, Node rightChild) {
            this.leftChild = leftChild;
            this.operator = operator;
            this.rightChild = rightChild;

            // add storeLoc as the first element in equivalentVars
            this.equivalentVars = new ArrayList<>();
            this.equivalentVars.add(storeLoc);
        }

        public void addVariableToComputation(LlLocation storeLoc) {
            this.equivalentVars.add(storeLoc);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof BinaryComputation) {
                BinaryComputation that = (BinaryComputation) obj;
                // TODO: deal with commutative and associative properites here

                return this.leftChild.equals(that.leftChild)
                        && this.operator.equals(that.operator)
                        && this.rightChild.equals(that.rightChild);
            }
            return false;
        }
    }
}







// 1) There is a node in the DAG for each of the initial values of the variables appearing in the basic block.
// 2) There is a node N associated with each statement s within the block.
//    the children of N are those nodes corresponding to the statements that
//    are the last definitions (prior to s) of the operands used by s. If there are no
//    statements that define them, then point to the initial values
// 3) Node N is labeled by the operator applied at s. Also attach to N is the list
//    of variables for which N is the last definition of those variables within the block.
// 4) Certain nodes are designated output nodes.
//    These are the nodes whose variables are live on exit from the block;
//    that is, their values may be used later, in another block of the flow
//    graph.


/*
    Types of nodes:
    1) initial value nodes
    2) operation nodes (with exactly 2 operands)
    3)
 */