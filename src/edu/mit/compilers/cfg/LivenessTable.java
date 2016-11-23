package edu.mit.compilers.cfg;

import edu.mit.compilers.ll.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by devinmorgan on 11/21/16.
 */
public class LivenessTable {
    private final LinkedHashMap<String, StatementData> stmtDataTable;
    private final HashMap<String, VariableData> varDataTable;

    private LivenessTable(LinkedHashMap<String, StatementData> stmtDataTable, HashMap<String, VariableData> varDataTable) {
        this.stmtDataTable = stmtDataTable;
        this.varDataTable = varDataTable;
    }

    // mutates varDataTable and stmtData by updating them with
    // x's liveness and NextUse info
    private static void updateLivenessAndNextUseOfWriteToVariable(LlLocation x, HashMap<String,
            VariableData> varDataTable, StatementData stmtData) {
        VariableData xData = new VariableData();
        String xVarName = x.getVarName();

        if (varDataTable.containsKey(xVarName)) {
            // we have seen this variable before
            xData.setIsLive(false);
        }
        else {
            // we have not seen this variable before
            if (xVarName.indexOf("#") == -1) {
                // it's not a temp var so assume it's live
                xData.setIsLive(true);
            }
            else {
                // it is a temp var so assume it's not live
                xData.setIsLive(false);
            }
        }
        // variable just got assigned so no known next use
        xData.setLastUsedLabel("NoNextUse");

        // update the varDataTable and stmtData objects
        varDataTable.put(x.getVarName(), xData);
        stmtData.setWriteVar(xData);
    }

    // mutates varDataTable and stmtData by updating them with
    // read-var's liveness and NextUse info
    private static void updateLivenessAndNextUseOfReadFromVariable(LlComponent operand, HashMap<String,
            VariableData> varDataTable, StatementData stmtData, String stmtLabel) {
        if (operand instanceof LlLocation) {
            LlLocation var = (LlLocation) operand;
            String name = var.getVarName();
            VariableData data = new VariableData();

            // set yData's inforation
            data.setIsLive(true);
            data.setLastUsedLabel(stmtLabel);

            // update the varDataTable and stmtData objects
            varDataTable.put(name, data);
            stmtData.setReadVarData(name, data);
        }
    }

    public static LivenessTable createLivenessTable(LinkedHashMap<String, LlStatement> labelsToStmtsMap) {
        LinkedHashMap<String, StatementData> stmtDataTable = new LinkedHashMap<>();
        HashMap<String, VariableData> varDataTable = new HashMap<>();

        ArrayList<String> labelsList = new ArrayList<>(labelsToStmtsMap.keySet());
        // start at the last statement in B and scan backwards
        for (int i = labelsList.size()-1; i >= 0; i--) {
            String label = labelsList.get(i);
            LlStatement stmt = labelsToStmtsMap.get(label);

            StatementData stmtData = stmtDataTable.get(label);

            // 1) Attach to stmt i, the information currently found in
            // the symbol table regarding the NextUse and Liveness of x = y + z
            if (stmt instanceof LlAssignStmtBinaryOp) {
                LlAssignStmtBinaryOp binaryOp = (LlAssignStmtBinaryOp) stmt;

                // Y: update liveness and next use
                LlComponent y = binaryOp.getLeftOperand();
                LivenessTable.updateLivenessAndNextUseOfReadFromVariable(y, varDataTable, stmtData, label);

                // Z: update liveness and next use
                LlComponent z = binaryOp.getRightOperand();
                LivenessTable.updateLivenessAndNextUseOfReadFromVariable(z, varDataTable, stmtData, label);

                // X: update liveness and next use (update X after Y,Z in
                // in case of read then write)
                LlLocation x = binaryOp.getStoreLocation();
                LivenessTable.updateLivenessAndNextUseOfWriteToVariable(x, varDataTable, stmtData);

                // attach stmtData to current stmt
                stmtDataTable.put(label, stmtData);

            }
            else if (stmt instanceof LlAssignStmtUnaryOp) {
                LlAssignStmtUnaryOp unaryOp = (LlAssignStmtUnaryOp) stmt;

                // Y: update liveness and next use
                LlComponent y = unaryOp.getOperand();
                LivenessTable.updateLivenessAndNextUseOfReadFromVariable(y, varDataTable, stmtData, label);

                // X: update liveness and next use (update X after Y in
                // case of read then write)
                LlLocation x = unaryOp.getStoreLocation();
                LivenessTable.updateLivenessAndNextUseOfWriteToVariable(x, varDataTable, stmtData);

                // attach stmtData to current stmt
                stmtDataTable.put(label, stmtData);
            }
            else if (stmt instanceof LlAssignStmtRegular) {
                LlAssignStmtRegular regular = (LlAssignStmtRegular) stmt;

                // Y: update liveness and next use
                LlComponent y = regular.getOperand();
                LivenessTable.updateLivenessAndNextUseOfReadFromVariable(y, varDataTable, stmtData, label);

                // X: update liveness and next use (update X after Y in
                // case of read then write)
                LlLocation x = regular.getStoreLocation();
                LivenessTable.updateLivenessAndNextUseOfWriteToVariable(x, varDataTable, stmtData);

                // attach stmtData to current stmt
                stmtDataTable.put(label, stmtData);
            }
            else if (stmt instanceof LlJumpConditional) {
                LlJumpConditional condJump = (LlJumpConditional) stmt;

                LlComponent condition = condJump.getCondition();
                if (condition instanceof LlLocation) {

                    // update liveness and next use
                    LlLocation condVar = (LlLocation) condition;
                    LivenessTable.updateLivenessAndNextUseOfReadFromVariable(condVar, varDataTable, stmtData, label);
                }
            }
            else if (stmt instanceof LlMethodCallStmt) {
                LlMethodCallStmt methodStmt = (LlMethodCallStmt) stmt;

                // update liveness and next use for each arg in method call
                for (LlComponent component : methodStmt.getArgsList()) {
                    if (component instanceof LlLocation) {
                        LlLocation arg = (LlLocation) component;
                        LivenessTable.updateLivenessAndNextUseOfReadFromVariable(arg, varDataTable, stmtData, label);
                    }
                }

                // update liveness and next use
                LlLocation x = methodStmt.getReturnLocation();
                LivenessTable.updateLivenessAndNextUseOfWriteToVariable(x, varDataTable, stmtData);
            }
        }

        return new LivenessTable(stmtDataTable, varDataTable);
    }

    private static class StatementData {
        private VariableData writeVar;
        private HashMap<String, VariableData> readVars = new HashMap<>();

        public VariableData getWriteVar() {
            return this.writeVar;
        }

        public VariableData getReadVarData(String var) {
            return this.readVars.get(var);
        }

        public void setWriteVar(VariableData writeVar) {
            this.writeVar = writeVar;
        }

        public void setReadVarData(String var, VariableData data) {
            this.readVars.put(var, data);
        }
    }

    private static class VariableData {
        private boolean isLive;
        private String lastUsedLabel;

        protected VariableData() {
            this.isLive = false;
            this.lastUsedLabel = "NoNextUse";
        }

        public boolean getIsLive() {
            return this.isLive;
        }

        public String getLastUsedLabel() {
            return this.lastUsedLabel;
        }

        public void setIsLive(boolean isLive) {
            this.isLive = isLive;
        }

        public void setLastUsedLabel(String lastUsedLabel) {
            this.lastUsedLabel = lastUsedLabel;
        }

    }

}
