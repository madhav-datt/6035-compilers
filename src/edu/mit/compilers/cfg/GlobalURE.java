package edu.mit.compilers.cfg;

import edu.mit.compilers.ll.LlEmptyStmt;
import edu.mit.compilers.ll.LlStatement;

import java.util.HashSet;
import java.util.Map;

/**
 * Created by madhav on 12/8/16.
 */
public class GlobalURE {

    //Recursive function to DFS on the
    private static void recursiveGlobalURE(BasicBlock node, HashSet<BasicBlock> visited) {

        //Check if node is already visited
        if (visited.contains(node))
            return;

        visited.add(node);
        BasicBlock defaultBranch = node.getDefaultBranch();
        BasicBlock alternateBranch = node.getAlternativeBranch();

        if (defaultBranch != null)
            recursiveGlobalURE(defaultBranch, visited);

        if (alternateBranch != null)
            recursiveGlobalURE(alternateBranch, visited);
    }

    public static void performGlobalURE(CFG cfg) {
        HashSet<BasicBlock> visited = new HashSet<>();
        recursiveGlobalURE(cfg.getRootBasicBlock(), visited);

        for (BasicBlock basicBlock : cfg.getBasicBlocks()) {
            //If block cannot be reached from root node, eliminate as unreachable code
            if (!visited.contains(basicBlock)) {
                for (Map.Entry<String, LlStatement> statementMap : basicBlock.getLabelsToStmtsMap().entrySet()) {
                    basicBlock.getLabelsToStmtsMap().put(statementMap.getKey(), new LlEmptyStmt());
                }
            }
        }
    }
}
