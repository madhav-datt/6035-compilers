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

    public CFG(LlBuilder builder) {
        this.builder = builder;

        // cache the Labels-->Stmts map
        LinkedHashMap<String, LlStatement> labelStmtsMap = new LinkedHashMap<>(builder.getStatementTable());
        Stack<String> labelsStack = new Stack<String>();
        labelsStack.addAll(new ArrayList<String>(labelStmtsMap.keySet()));

        // 1) determine the leaders in the LLIR
        PriorityQueue<String> leaders = new PriorityQueue<>();

        // the first instruction in the LLIR is a leader
        leaders.add(labelsStack.get(0));

        for (int i = 1; i < labelsStack.size(); i++) {
            String label = labelsStack.get(i);
            LlStatement stmt = labelStmtsMap.get(label);

            if (stmt instanceof LlJump) {

                // the TARGET of the jumpStmt is a leader
                String jmpTolabel = ((LlJump) stmt).getJumpToLabel();

                // the stmt FOLLOWING the jumpStmt is a leader
                String nextStmtLabel = labelsStack.get(i+1);
            }
        }
        
        // 2) create basic blocks from LlStatements
        // each basic block will be identified by its leader
        LinkedHashMap<String, BasicBlock> bbLinkedHashMap = new LinkedHashMap<>();
        do {
            ArrayList<LlStatement> currentBBStmtList = new ArrayList<>();

            // basic blocks start with a leader
            String leaderLabel = labelsStack.pop();
            LlStatement leaderStmt = labelStmtsMap.get(leaderLabel);
            currentBBStmtList.add(leaderStmt);

            // keep adding LlStatments until you get to the next leader
            while (labelsStack.peek() != leaders.peek()) {
                // keep adding stmts to the currentBBStmtList
                String label = labelsStack.pop();
                LlStatement stmt = labelStmtsMap.get(label);
                currentBBStmtList.add(stmt);
            }

            // create the actual BasicBlock and it to the LinkedHashMap
            BasicBlock bb = new BasicBlock(currentBBStmtList);
            bbLinkedHashMap.put(leaderLabel,bb);

        } while (labelsStack.size() > 0);

        // 3) appropriately connect the basic blocks
        List<String> bbLeadersList = new ArrayList<>(bbLinkedHashMap.keySet());
        for (int i = 0; i < bbLeadersList.size(); i++) {
            String currentBBLeaderLabel = bbLeadersList.get(i);
            BasicBlock currentBB = bbLinkedHashMap.get(currentBBLeaderLabel);

            List<LlStatement> currentBBStmtsList = currentBB.getStmtsList();
            LlStatement lastStmtOfCurrentBB = currentBBStmtsList.get(currentBBStmtsList.size()-1);

           // connect if there is a jump from the end of B to the beginning of C
            if (lastStmtOfCurrentBB instanceof LlJump) {
                String targetLabel = ((LlJump) lastStmtOfCurrentBB).getJumpToLabel();
                BasicBlock targetBB = bbLinkedHashMap.get(targetLabel);
                currentBB.setAlternativeBranch(targetBB);
            }

            // C immediately follows B and B does not end in an unconditional jump
            // (this only holds if B is not the last block))
            if ( !(lastStmtOfCurrentBB instanceof LlJumpUnconditional) && (i < bbLeadersList.size()-1) ) {
                String nextBBLeaderLabel = bbLeadersList.get(i+1);
                BasicBlock nextBB = bbLinkedHashMap.get(nextBBLeaderLabel);
                currentBB.setDefaultBranch(nextBB);
            }
        }

        // 4) assign the list of basic blocks as a field THIS object
        ArrayList<String> basicBlockLabels = new ArrayList<>(bbLinkedHashMap.keySet());
        ArrayList<BasicBlock> basicBlocks = new ArrayList<>();
        for (String label : basicBlockLabels) {
            basicBlocks.add(bbLinkedHashMap.get(label));
        }

        this.basicBlocks = basicBlocks;
    }
}
