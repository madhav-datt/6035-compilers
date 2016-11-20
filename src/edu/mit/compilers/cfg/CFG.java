package edu.mit.compilers.cfg;

import edu.mit.compilers.LlBuilder;
import edu.mit.compilers.ll.LlEmptyStmt;
import edu.mit.compilers.ll.LlJump;
import edu.mit.compilers.ll.LlStatement;

import java.util.*;

/**
 * Created by devinmorgan on 11/20/16.
 */
public class CFG {
    private final LlBuilder builder;

    public CFG(LlBuilder builder) {
        this.builder = builder;
        ArrayList<LlStatement> allLlStmts = new ArrayList<>(builder.getStatementList());

        // 1) determine the leaders in the LLIR
        PriorityQueue<LlStatement> leaders = new PriorityQueue<>();

        // the first instruction in the LLIR is a leader
        leaders.add(allLlStmts.get(0));

        for (int i = 1; i < allLlStmts.size(); i++) {
            LlStatement stmt = allLlStmts.get(i);

            // any instruction that is the TARGET of a jump (i.e. an LlEmptyStmt) is a leader
            if (stmt instanceof LlEmptyStmt) {
                leaders.add(stmt);
            }

            // any instruction that immediately FOLLOWS a jump is a leader
            else if (stmt instanceof LlJump) {
                // we don't want the jump stmt! we want the stmt that follows it
                leaders.add(allLlStmts.get(i+1));
            }
        }
        
        // 2) create maximal BasicBlocks by adding stmts up until the next leader
        ArrayList<BasicBlock> bbList = new ArrayList<>();
        for (LlStatement stmt : allLlStmts) {
            // 1) create a new basic block at when you find a leader
            // 2) e
        }
        
        
//        while (allLlStmts.size() > 0) {
//            ArrayList<LlStatement> currentBBStmts = new ArrayList<>();
//
//            // add the maximum number of LlStatements to currentBBStmts
//            boolean isNotJump;
//            boolean isNotEmptyStmt;
//            do {
//                LlStatement currentStmt = allLlStmts.pop();
//                isNotJump = !(currentStmt instanceof LlJump);
//                isNotEmptyStmt = !(currentStmt instanceof LlEmptyStmt);
//
//                currentBBStmts.add(currentStmt);
//
//            } while (isNotJump && isNotEmptyStmt);
//
//            // create the BasicBlock from the maximal LlStatements list
//            BasicBlock newBB = new BasicBlock(currentBBStmts,)
//
//        }

        // 1)
    }


}
