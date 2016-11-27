package edu.mit.compilers.cfg;

import edu.mit.compilers.ll.Ll;
import edu.mit.compilers.ll.LlStatement;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by devinmorgan on 11/20/16.
 */
public class BasicBlock {

    public Edge getLeft() {
        return this.left;
    }

    public Edge getRight() {
        return this.right;
    }

    private Edge left = new Edge();
    private Edge right = new Edge();

    private LinkedHashMap<String, LlStatement> labelsToStmtsMap;
    private BasicBlock defaultBranch;
    private BasicBlock alternativeBranch;

    public BasicBlock(LinkedHashMap<String, LlStatement> labelsToStmtsMap) {
        this.labelsToStmtsMap = new LinkedHashMap<>(labelsToStmtsMap);
    }

    public LinkedHashMap<String, LlStatement> getLabelsToStmtsMap() {
        return this.labelsToStmtsMap;
    }

    public ArrayList<LlStatement> getStmtsList() {
        ArrayList<LlStatement> stmtsList = new ArrayList<>();
        for (String label : this.labelsToStmtsMap.keySet()) {
            LlStatement stmt = this.labelsToStmtsMap.get(label);
            stmtsList.add(stmt);
        }
        return stmtsList;
    }

    public ArrayList<String> getLabelsList() {
        return new ArrayList<String>(this.labelsToStmtsMap.keySet());
    }

    protected void setDefaultBranch(BasicBlock defaultBranch) {
        this.defaultBranch = defaultBranch;
    }

    protected void setAlternativeBranch(BasicBlock alternativeBranch) {
        this.alternativeBranch = alternativeBranch;
    }

    public BasicBlock getDefaultBranch() {
        return defaultBranch;
    }

    public BasicBlock getAlternativeBranch() {
        return alternativeBranch;
    }

    @Override
    public String toString() {
        String str = "";
        for(String label : this.labelsToStmtsMap.keySet()){
            str += String.format("%1$15s :  ", label);
            str += this.labelsToStmtsMap.get(label) + "\n";

        }
        return str;
    }
}
