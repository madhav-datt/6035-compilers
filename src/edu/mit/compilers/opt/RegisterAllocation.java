package edu.mit.compilers.opt;

import edu.mit.compilers.LlBuilder;
import edu.mit.compilers.Register;
import edu.mit.compilers.cfg.CFG;
import edu.mit.compilers.ll.*;

import java.util.*;

/**
 * Created by madhav on 11/26/16.
 */
public class RegisterAllocation {

    private ArrayList<String> availableRegisters;
    private CFG methodCFG;

    private HashMap<LlLocation, Integer> varUsageCount;
    private HashMap<LlLocation, String> varRegisterAllocations;
    private PriorityQueue<LlLocation> mostUsedVar;

    public HashMap<LlLocation, Integer> getVarUsageCount() {
        return varUsageCount;
    }

    public HashMap<LlLocation, String> getVarRegisterAllocations() {
        return varRegisterAllocations;
    }

    private void updateVarUsageTable(LlComponent arg, int usageConstant) {
        if (arg instanceof LlLiteral)
            return;

        int currentUsage = 0;
        if (this.varUsageCount.containsKey(arg))
            currentUsage = this.varUsageCount.get(arg);

        this.varUsageCount.put((LlLocation) arg, currentUsage + usageConstant);
    }

    //Check if allocating var to register creates conflicts
    private boolean isConflict(LlLocation var, String register) {

        return true;
    }

    //Allocate registers according to greedy algorithm
    //Find most used variable from varUsageCount, allocate to register, check for conflicts
    //If conflict found, allocate to next register and repeat process
    //If var could not be allocated to any register, ignore var and continue
    private void allocateRegisters() {
        while (!this.varUsageCount.isEmpty()) {
            //Get key with max usage value
            LlLocation var = Collections.max(this.varUsageCount.entrySet(), Map.Entry.comparingByValue()).getKey();
            for (String register : this.availableRegisters) {
                //If no conflict exists, allocate to selected register
                if (!isConflict(var, register)) {
                    this.varRegisterAllocations.put(var, register);
                    break;
                }
            }
        }
    }

    public RegisterAllocation(ArrayList<String> givenRegisters, CFG methodCFG) {
        this.availableRegisters = givenRegisters;
        this.methodCFG = methodCFG;
        this.varUsageCount = new HashMap<>();
        this.varRegisterAllocations = new HashMap<>();

        int currentDepth = 0;

        //Use heuristics to count variable/LlLocation uses and defs
        for (Map.Entry<String, LlStatement> statementEntry : methodCFG.getBuilder().getStatementTable().entrySet()) {
            String statementLabel = statementEntry.getKey();
            LlStatement statement = statementEntry.getValue();

            //If label marks beginning of loop, increase depth by 1
            //If label marks end of loop, decrease depth by 1
            if (statementLabel.substring(0, 7).equals("END_FOR") || statementLabel.substring(0, 9).equals("END_WHILE"))
                currentDepth--;

            else if (statementLabel.substring(0, 3).equals("FOR") || statementLabel.substring(0, 5).equals("WHILE"))
                currentDepth++;

            int addUsageConstant = (int) Math.pow(10, currentDepth);

            if (statement instanceof LlAssignStmtRegular) {
               LlLocation storeLocation = ((LlAssignStmtRegular) statement).getStoreLocation();
               this.updateVarUsageTable(storeLocation, addUsageConstant);

               LlComponent arg = ((LlAssignStmtRegular) statement).getArg();
               this.updateVarUsageTable(arg, addUsageConstant);
            }

            else if (statement instanceof LlAssignStmtUnaryOp) {
                LlLocation storeLocation = ((LlAssignStmtUnaryOp) statement).getStoreLocation();
                this.updateVarUsageTable(storeLocation, addUsageConstant);

                LlComponent arg = ((LlAssignStmtUnaryOp) statement).getArg();
                this.updateVarUsageTable(arg, addUsageConstant);
            }

            else if (statement instanceof LlAssignStmtBinaryOp) {
                LlLocation storeLocation = ((LlAssignStmtBinaryOp) statement).getStoreLocation();
                this.updateVarUsageTable(storeLocation, addUsageConstant);

                LlComponent arg1 = ((LlAssignStmtBinaryOp) statement).getLeftArg();
                this.updateVarUsageTable(arg1, addUsageConstant);

                LlComponent arg2 = ((LlAssignStmtBinaryOp) statement).getRightArg();
                this.updateVarUsageTable(arg2, addUsageConstant);
            }

            else if (statement instanceof LlJumpConditional) {
                LlComponent arg = ((LlJumpConditional) statement).getCondition();
                this.updateVarUsageTable(arg, addUsageConstant);
            }

            else if (statement instanceof LlReturn) {
                LlComponent arg = ((LlReturn) statement).getReturnValue();
                this.updateVarUsageTable(arg, addUsageConstant);
            }

            else if (statement instanceof LlMethodCallStmt) {
                LlLocation returnLocation = ((LlMethodCallStmt) statement).getReturnLocation();
                if (returnLocation != null)
                    this.updateVarUsageTable(returnLocation, addUsageConstant);

                ArrayList<LlComponent> args = new ArrayList<>(((LlMethodCallStmt) statement).getArgsList());
                for (LlComponent arg : args)
                    this.updateVarUsageTable(arg, addUsageConstant);
            }
        }

        this.allocateRegisters();
    }
}
