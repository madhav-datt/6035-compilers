package edu.mit.compilers.cfg;

import edu.mit.compilers.LlBuilder;
import edu.mit.compilers.ll.LlJump;
import edu.mit.compilers.ll.LlJumpUnconditional;
import edu.mit.compilers.ll.LlStatement;

import java.util.*;

/**
 * Created by devinmorgan on 12/3/16.
 */
public class ProgramPointsCFG {
//    private final ArrayList<BasicBlock> basicBlocks;
    private final ArrayList<Node> nodes;


    public ProgramPointsCFG(CFG cfg) {
        ArrayList<String> orderedLeadersList = cfg.getOrderedLeadersList();
        LinkedHashMap<String, BasicBlock> leadersToBBMap = cfg.getLeadersToBBMap();

        // create and store nodes to represent each basic block
        LinkedHashMap<String, Node> leadersToNodeMap = new LinkedHashMap<>();
        for (String label : leadersToBBMap.keySet()) {
            BasicBlock bb = leadersToBBMap.get(label);
            Node node = new Node(bb.getLabelsToStmtsMap());
            leadersToNodeMap.put(label, node);
        }

        // connect the nodes exactly as they were in the CFG
        for (int i = 0; i < orderedLeadersList.size(); i++) { // loop through the leaders in the order of the linear order of the basic blocks
            String leaderLabel = orderedLeadersList.get(i);
            BasicBlock bb = leadersToBBMap.get(leaderLabel);
            Node node = leadersToNodeMap.get(leaderLabel);

            List<LlStatement> bbStmtsList = bb.getStmtsList();
            LlStatement lastStmtOfCurrentBB = bbStmtsList.get(bbStmtsList.size() - 1);

            // connect if there is a jump from the end of B to the beginning of C
            if (lastStmtOfCurrentBB instanceof LlJump) {
                String targetLabel = ((LlJump) lastStmtOfCurrentBB).getJumpToLabel();
                Node targetNode = leadersToNodeMap.get(targetLabel);
                node.setAlternativeBranch(targetNode);
            }

            // C immediately follows B and B does not end in an unconditional jump
            // (this only holds if B is not the last block))
            if (!(lastStmtOfCurrentBB instanceof LlJumpUnconditional) && (i < orderedLeadersList.size() - 1)) {
                String nextNodeLabel = orderedLeadersList.get(i + 1);
                Node nextNode = leadersToNodeMap.get(nextNodeLabel);
                node.setDefaultBranch(nextNode);
            }
        }

        // 4) assign the list of nodes as a field of THIS object
        this.nodes = new ArrayList<>();
        for (String leaderLabel : orderedLeadersList) {
            this.nodes.add(leadersToNodeMap.get(leaderLabel));
        }

    }

    private class Node {
        private final HashSet<Computation> nodeTopPP = new HashSet<>();
        private final HashMap<String, HashSet<Computation>> instructionTopPPs = new HashMap();
        private final HashMap<String, HashSet<Computation>> instructionBotPPs = new HashMap();
        private final HashSet<Computation> nodeBotPP = new HashSet<>();
        private final LinkedHashMap<String, LlStatement> labelsToStmtsMap;
        private Node alternativeBranch; // set after init
        private Node defaultBranch; // set after init

        public Node(LinkedHashMap<String, LlStatement> labelsToStmtsMap) {
            this.labelsToStmtsMap = labelsToStmtsMap;

            // initialize the instructionTopPPs and instructionBotPPs
            for (String label : this.labelsToStmtsMap.keySet()) {
                this.instructionTopPPs.put(label, new HashSet<>());
                this.instructionBotPPs.put(label, new HashSet<>());
            }
        }

        public void setAlternativeBranch(Node alternativeBranch) {
            this.alternativeBranch = alternativeBranch;
        }

        public void setDefaultBranch(Node defaultBranch) {
            this.defaultBranch = defaultBranch;
        }
    }

    private abstract class Computation {

    }

    private class BinaryComputation extends Computation {

    }

    private class UnaryComputation extends Computation {

    }
}