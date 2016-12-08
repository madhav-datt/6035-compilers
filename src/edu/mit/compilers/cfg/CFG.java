package edu.mit.compilers.cfg;

import edu.mit.compilers.LlBuilder;
import edu.mit.compilers.ll.LlJump;
import edu.mit.compilers.ll.LlJumpConditional;
import edu.mit.compilers.ll.LlJumpUnconditional;
import edu.mit.compilers.ll.LlStatement;

import java.util.*;

/**
 * Created by devinmorgan on 11/20/16.
 */
public class CFG {
    private final LlBuilder builder;
    private final ArrayList<BasicBlock> basicBlocks;
    private final ArrayList<String> orderedLeadersList;
    private final LinkedHashMap<String, BasicBlock> leadersToBBMap;

    public CFG(LlBuilder builder) {
        this.builder = builder;

        // cache the Labels => Stmts map and extract the labels list
        LinkedHashMap<String, LlStatement> labelStmtsMap = new LinkedHashMap<>(builder.getStatementTable());
        ArrayList<String> labelsList = new ArrayList<>(labelStmtsMap.keySet());

        if (labelsList.size() == 0) {
            this.basicBlocks = new ArrayList<BasicBlock>();
            this.orderedLeadersList = new ArrayList<>();
            this.leadersToBBMap = new LinkedHashMap<>();
        }
        else {
            // 1) determine the leaders in the LLIR
            HashSet<String> leadersSet = new HashSet<>();

            // the first instruction in the LLIR is a leader
            leadersSet.add(labelsList.get(0));

            for (int i = 1; i < labelsList.size(); i++) {
                String label = labelsList.get(i);
                LlStatement stmt = labelStmtsMap.get(label);

                if (stmt instanceof LlJump) {

                    // the TARGET of the jumpStmt is a leader
                    String jmpTolabel = ((LlJump) stmt).getJumpToLabel();
                    leadersSet.add(jmpTolabel);

                    // the stmt FOLLOWING the jumpStmt is a leader
                    String nextStmtLabel = labelsList.get(i + 1);
                    leadersSet.add(nextStmtLabel);
                }
            }

            // 2) create basic blocks from LlStatements
            this.leadersToBBMap = new LinkedHashMap<>();
            HashSet<String> tempLeadersSet = new HashSet<>(leadersSet);
            LinkedList<String> labelsQueue = new LinkedList<>(labelsList);
            this.orderedLeadersList = new ArrayList<>();
            do {
                LinkedHashMap<String, LlStatement> bbLabelsToStmtsMap = new LinkedHashMap<>();

                // basic blocks start with the leader
                String leaderLabel = labelsQueue.pop();
                LlStatement leaderStmt = labelStmtsMap.get(leaderLabel);
                bbLabelsToStmtsMap.put(leaderLabel, leaderStmt);

                // remove this leader from the leadersSet and add it
                // to the leadersList
                tempLeadersSet.remove(leaderLabel);
                this.orderedLeadersList.add(leaderLabel);

                // keep adding LlStatments until you get to the next leader
                while (labelsQueue.size() > 0 && !tempLeadersSet.contains(labelsQueue.peek())) {

                    // keep adding stmts to the currentBBStmtList
                    String label = labelsQueue.pop();
                    LlStatement stmt = labelStmtsMap.get(label);
                    bbLabelsToStmtsMap.put(label, stmt);
                }

                // create the actual BasicBlock and it to the LinkedHashMap
                BasicBlock bb = new BasicBlock(bbLabelsToStmtsMap, builder);
                this.leadersToBBMap.put(leaderLabel, bb);

            } while (labelsQueue.size() > 0);

            // 3) appropriately connect the basic blocks
            for (int i = 0; i < this.orderedLeadersList.size(); i++) { // loop through the leaders in the order of the linear order of the basic blocks
                String leaderLabel = this.orderedLeadersList.get(i);
                BasicBlock bb = this.leadersToBBMap.get(leaderLabel);

                List<LlStatement> bbStmtsList = bb.getStmtsList();
                LlStatement lastStmtOfCurrentBB = bbStmtsList.get(bbStmtsList.size() - 1);

                // connect if there is a jump from the end of B to the beginning of C
                if (lastStmtOfCurrentBB instanceof LlJump) {

                    // set forward edge B --> C
                    String targetLabel = ((LlJump) lastStmtOfCurrentBB).getJumpToLabel();
                    BasicBlock targetBB = this.leadersToBBMap.get(targetLabel);
                    bb.setAlternativeBranch(targetBB);

                    // set reverse edge B <-- C
                    targetBB.addPredecessorNode(bb);
                }

                // C immediately follows B and B does not end in an unconditional jump
                // (this only holds if B is not the last block))
                if (!(lastStmtOfCurrentBB instanceof LlJumpUnconditional) && (i < this.orderedLeadersList.size() - 1)) {

                    // set forward edge B --> C
                    String nextBBLeaderLabel = this.orderedLeadersList.get(i + 1);
                    BasicBlock nextBB = this.leadersToBBMap.get(nextBBLeaderLabel);
                    bb.setDefaultBranch(nextBB);

                    // set reverse edge B --> C
                    nextBB.addPredecessorNode(bb);
                }
            }

            // 4) assign the list of basic blocks as a field of THIS object
            ArrayList<BasicBlock> basicBlocks = new ArrayList<>();
            for (String leaderLabel : this.orderedLeadersList) {
                basicBlocks.add(this.leadersToBBMap.get(leaderLabel));
            }

            this.basicBlocks = basicBlocks;
        }
    }

    public LlBuilder getBuilder() {
        return this.builder;
    }

    public ArrayList<String> getOrderedLeadersList() {
        return new ArrayList<>(orderedLeadersList);
    }

    public LinkedHashMap<String, BasicBlock> getLeadersToBBMap() {
        return new LinkedHashMap<>(leadersToBBMap);
    }

    public BasicBlock getRootBasicBlock() {
        return this.basicBlocks.get(0);
    }

    public ArrayList<BasicBlock> getBasicBlocks() {
        return this.basicBlocks;
    }

    @Override
    public String toString() {
        String str = "CFG:\n";
        for (BasicBlock bb : this.basicBlocks) {
            str += bb.toString() + "\n";
        }
        str = str.substring(0, str.length() - 1);
        return str;
    }
}
