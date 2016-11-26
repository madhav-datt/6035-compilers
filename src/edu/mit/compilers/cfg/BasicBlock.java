package edu.mit.compilers.cfg;

import edu.mit.compilers.LlBuilder;
import edu.mit.compilers.ll.*;
import org.w3c.dom.ls.LSSerializer;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by devinmorgan on 11/20/16.
 */
public class BasicBlock {
    private final LinkedHashMap<String, LlStatement> labelsToStmtsMap;
    private BasicBlock defaultBranch;
    private BasicBlock alternativeBranch;
    private final LivenessTable livenessTable;
    private final LlBuilder builder;

    public BasicBlock(LinkedHashMap<String, LlStatement> labelsToStmtsMap, LlBuilder builder) {
        this.labelsToStmtsMap = new LinkedHashMap<>(labelsToStmtsMap);
        this.livenessTable = LivenessTable.createLivenessTable(labelsToStmtsMap);
        this.builder = builder;
    }

    public ArrayList<LlStatement> getStmtsList() {
        ArrayList<LlStatement> stmtsList = new ArrayList<>();
        for (String label : this.labelsToStmtsMap.keySet()) {
            LlStatement stmt = this.labelsToStmtsMap.get(label);
            stmtsList.add(stmt);
        }
        return stmtsList;
    }

    public LlBuilder getBuilder() {
        return this.builder;
    }

    public ArrayList<String> getLabelsList() {
        return new ArrayList<String>(this.labelsToStmtsMap.keySet());
    }

    public BasicBlock getDefaultBranch() {
        return defaultBranch;
    }

    public BasicBlock getAlternativeBranch() {
        return alternativeBranch;
    }

    public LinkedHashMap<String, LlStatement> getLabelsToStmtsMap() {
        return new LinkedHashMap<>(getLabelsToStmtsMap());
    }


    protected void setDefaultBranch(BasicBlock defaultBranch) {
        this.defaultBranch = defaultBranch;
    }

    protected void setAlternativeBranch(BasicBlock alternativeBranch) {
        this.alternativeBranch = alternativeBranch;
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
