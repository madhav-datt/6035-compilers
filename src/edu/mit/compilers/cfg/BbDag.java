package edu.mit.compilers.cfg;

import edu.mit.compilers.ll.*;

import java.util.*;

/**
 * Created by devinmorgan on 11/23/16.
 */
public class BbDag {
    private final HashMap<LlComponent, ArrayList<Node>> componentsToUsesMap;
    private final ArrayList<Computation> computationsList;

    public static BbDag createBasicBlockDag(BasicBlock bb) {
        LinkedHashMap<String, LlStatement> labelsToStmtsMap = bb.getLabelsToStmtsMap();
        BbDag dag = new BbDag();

        HashSet<Node> nodes;
        for (String label : labelsToStmtsMap.keySet()) {
            LlStatement stmt = labelsToStmtsMap.get(label);
            // 1) add regular, unary, and binary stmts
            if (stmt instanceof LlAssignStmtBinaryOp) {
                LlAssignStmtBinaryOp binaryOp = (LlAssignStmtBinaryOp) stmt;
                dag.addBinaryComputationToDag(binaryOp);
            }
            else if (stmt instanceof LlAssignStmtUnaryOp) {
                LlAssignStmtUnaryOp unaryOp = (LlAssignStmtUnaryOp) stmt;
                dag.addUnaryComputationToDag(unaryOp);
            }
            else if (stmt instanceof LlAssignStmtRegular) {
                LlAssignStmtRegular regular = (LlAssignStmtRegular) stmt;
                dag.addAssignmentToDag(regular);
            }
        }
    }

    private void addAssignmentToDag(LlAssignStmtRegular regular) {
        LlComponent operand = regular.getOperand();

        // ensure that the operand component exists in the componentsToUsesMap
        if (this.getReadCompFromUsedComps(operand) == null) {
            this.addReadCompToUsedComps(operand);
        }

        // create/process the assignment
        Node child = this.getReadCompFromUsedComps(operand);
        LlLocation storeLoc = regular.getStoreLocation();
        AssignmentComputation comp = new AssignmentComputation(child, storeLoc);
        this.processVariableAndComputation(comp, storeLoc);
        this.addWriteCompToUsedComps(storeLoc, comp);
    }

    private void addUnaryComputationToDag(LlAssignStmtUnaryOp unaryOp) {
        LlComponent operand = unaryOp.getOperand();

        // ensure that the operand component exists in componentsToUsesMap
        if (this.getReadCompFromUsedComps(operand) == null) {
            this.addReadCompToUsedComps(operand);
        }

        // create/process the computation
        Node child = this.getReadCompFromUsedComps(operand);
        String operator = unaryOp.getOperator();
        LlLocation storeLoc = unaryOp.getStoreLocation();
        UnaryComputation comp = new UnaryComputation(child, storeLoc, operator);
        this.processVariableAndComputation(comp, storeLoc);
        this.addWriteCompToUsedComps(storeLoc, comp);
    }

    private void addBinaryComputationToDag(LlAssignStmtBinaryOp binaryOp) {
        LlComponent leftOperand = binaryOp.getLeftOperand();
        LlComponent rightOperand = binaryOp.getRightOperand();

        // ensure the left and right components exist in componentsToUsesMap
        if (this.getReadCompFromUsedComps(leftOperand) == null) {
            this.addReadCompToUsedComps(leftOperand);
        }
        if (this.getReadCompFromUsedComps(rightOperand) == null) {
            this.addReadCompToUsedComps(rightOperand);
        }

        // create/process the new computation
        Node leftChild = this.getReadCompFromUsedComps(leftOperand);
        Node rightChild = this.getReadCompFromUsedComps(rightOperand);
        String operator = binaryOp.getOperation();
        LlLocation storeLoc = binaryOp.getStoreLocation();
        BinaryComputation comp = new BinaryComputation(leftChild, rightChild, storeLoc, operator);
        this.processVariableAndComputation(comp, storeLoc);
        this.addWriteCompToUsedComps(storeLoc, comp);
    }

    private Node getReadCompFromUsedComps(LlComponent comp) {
        if (this.componentsToUsesMap.containsKey(comp)) {

            // return the most recent use of the comp (i.e. size - 1)
            ArrayList<Node> prevInstances = this.componentsToUsesMap.get(comp);
            return prevInstances.get( prevInstances.size() - 1) ;
        }
        return null;
    }

    private Node addReadCompToUsedComps(LlComponent comp) {
        if (!this.componentsToUsesMap.containsKey(comp)) {

            // create the initial LeafNode
            LeafNode leaf = new LeafNode(comp);

            // create the prev Instances array and add the leaf
            ArrayList<Node> prevInstances = new ArrayList<>();
            prevInstances.add(leaf);

            // put comp and prevInstances in the comps-->uses map
            this.componentsToUsesMap.put(comp, prevInstances);

            // return the LeafNode
            return leaf;
        }
        return null;
    }

    private void addWriteCompToUsedComps(LlLocation storeLoc, Computation computation) {
        Node node = Node.createNodeforComputation(computation);

        if (!this.componentsToUsesMap.containsKey(storeLoc)) {
            // add write variable to componentsToUsesMap
            ArrayList<Node> prevInstances = new ArrayList<>();
            prevInstances.add(node);

            // put comp and prevInstances in the comps-->uses map
            this.componentsToUsesMap.put(storeLoc, prevInstances);
        }
        else {
            // update variable's ArrayList with new write value (node)
            ArrayList<Node> previousWriteValues = this.componentsToUsesMap.get(storeLoc);
            previousWriteValues.add(node);
        }
    }

    private void processVariableAndComputation(Computation newComputation, LlLocation storeLoc) {
        // search through computationsList to see if the computation exists already
        for (Computation computation : this.computationsList) {

            // don't create another computation if it already exists
            if (newComputation.equals(computation)) {
                computation.mapVariableToComputation(storeLoc);
                return;
            }
        }

        // if this computation is unique, add it to the list
        this.computationsList.add(newComputation);


    }

    private static abstract class Computation {
        protected final ArrayList<LlLocation> equivalentVars;
        protected final String operator;

        public Computation(LlLocation storeLoc, String operator) {
            this.operator = operator;

            // add storeLoc as the first element in equivalentVars
            this.equivalentVars = new ArrayList<>();
            this.equivalentVars.add(storeLoc);
        }

        public void mapVariableToComputation(LlLocation storeLoc) {
            this.equivalentVars.add(storeLoc);
        }
    }

    private static class AssignmentComputation extends Computation {
        private final Node child;

        public AssignmentComputation(Node child, LlLocation storeLoc) {
            super(storeLoc, "NoOperator");
            this.child = child;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof AssignmentComputation) {
                AssignmentComputation that = (AssignmentComputation) obj;
                return this.child.equals(that.child);
            }
            return false;
        }
    }

    private static class UnaryComputation extends Computation{
        private final Node child;

        public UnaryComputation(Node child, LlLocation storeLoc, String operator) {
            super(storeLoc, operator);
            this.child = child;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof UnaryComputation) {
                UnaryComputation that = (UnaryComputation) obj;

                return this.operator.equals(that.operator)
                        && this.child.equals(that.child);
            }
            return false;
        }
    }

    private static class BinaryComputation extends Computation{
        private final Node leftChild;
        private final Node rightChild;

        public BinaryComputation(Node leftChild, Node rightChild, LlLocation storeLoc, String operator) {
            super(storeLoc, operator);
            this.leftChild = leftChild;
            this.rightChild = rightChild;
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

    private static abstract class Node {
        public static Node createNodeforComputation(Computation computation) {
            if (computation instanceof AssignmentComputation) {
                AssignmentComputation comp = (AssignmentComputation) computation;
                return new AssignmentComputationNode(comp);
            }
            else if (computation instanceof UnaryComputation) {
                UnaryComputation comp = (UnaryComputation) computation;
                return new UnaryComputationNode(comp);
            }
            else if (computation instanceof BinaryComputation) {
                BinaryComputation comp = (BinaryComputation) computation;
                return new BinaryComputationNode(comp);
            }
            return null;
        }
    }

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

    private static class AssignmentComputationNode extends Node {
        private final AssignmentComputation assignmentComputation;

        public AssignmentComputationNode(AssignmentComputation assignmentComputation) {
            this.assignmentComputation = assignmentComputation;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof AssignmentComputationNode) {
                AssignmentComputationNode that = (AssignmentComputationNode) obj;
                return this.assignmentComputation.equals(that.assignmentComputation);
            }
            return false;
        }
    }

    private static class UnaryComputationNode extends Node {
        private final UnaryComputation unaryComputation;

        public UnaryComputationNode(UnaryComputation unaryComputation) {
            this.unaryComputation = unaryComputation;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof UnaryComputationNode) {
                UnaryComputationNode that = (UnaryComputationNode) obj;
                return this.unaryComputation.equals(that.unaryComputation);
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