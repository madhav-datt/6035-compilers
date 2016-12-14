package edu.mit.compilers.opt;

import edu.mit.compilers.LlBuilder;
import edu.mit.compilers.cfg.CFG;
import edu.mit.compilers.ll.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by madhav on 11/26/16.
 */
public class RegisterAllocation {

    private ArrayList<String> availableRegisters;
    private CFG methodCFG;

    private HashMap<LlLocation, Integer> varUsageCount;
    private HashMap<LlLocation, String> varRegisterAllocations;
    private HashMap<CFG.SymbolDef, ArrayList<CFG.Tuple>> defUseChain;

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

    //Get label number from label string
    private int getLabelNum(String input) {

        input = input.replaceAll("\\D+",""); // Incase of weird failure look at this
        String someNumberStr = input;

        return Integer.parseInt(someNumberStr);
    }

    //Check if allocating var to register creates conflicts
    private boolean isConflict(LlLocation var, String register) {

        //List of def and use line numbers of var in question
        HashSet<Integer> duVar = new HashSet<>();
        for (Map.Entry<CFG.SymbolDef, ArrayList<CFG.Tuple>> duChain : this.defUseChain.entrySet()) {
            CFG.SymbolDef currentDef = duChain.getKey();

            if (currentDef.symbol.equals(var)) {
                duVar.add(this.getLabelNum(currentDef.useDef.label));
                for (CFG.Tuple varUses : duChain.getValue()){
                    duVar.add(this.getLabelNum(varUses.label));
                }
            }
        }

        if (this.varRegisterAllocations.size() == 0) {
            return false;
        }

        //Check for all variables (refered to as otherVar) that have been allocated to said register
        for (Map.Entry<LlLocation, String> statementEntry : this.varRegisterAllocations.entrySet()) {
            LlLocation otherVar = statementEntry.getKey();

            //Continue if otherVar is allocated different register
            if (!register.equals(statementEntry.getValue())) {
                continue;
            }


            //Check if var and otherVar conflict, for all chains with otherVar
            for (Map.Entry<CFG.SymbolDef, ArrayList<CFG.Tuple>> duChain : this.defUseChain.entrySet()) {
                CFG.SymbolDef duOtherVar = duChain.getKey();

                //Check if selected du-chain corresponds to otherVar
                if (duOtherVar.symbol.equals(otherVar)) {
                    ArrayList<Integer> otherVarUseList = new ArrayList<>();
                    int defOtherVar = this.getLabelNum(duOtherVar.useDef.label);

                    //Find last use of otherVar in selected du chain
                    for (CFG.Tuple varUses : duChain.getValue()){
                        otherVarUseList.add(this.getLabelNum(varUses.label));
                    }
                    otherVarUseList.add(defOtherVar);

//                    System.out.println(var.toString() + " : " + duOtherVar.toString());
//                    System.out.println(otherVarUseList.toString());
//                    System.out.println(duVar.toString());
//                    System.out.println();

                    //Handle zero len case separately
                    //No conflict if other var is never used
                    if (otherVarUseList.size() == 0) {
                        break;
                    }

                    int maxUseOtherVar = Collections.max(otherVarUseList) + 10;
                    int minUseOtherVar = Collections.min(otherVarUseList);

                    //Check if any use/def of var lies on a def-use chain of otherVar
                    for (int varDefUses : duVar) {
                        if (varDefUses >= minUseOtherVar && varDefUses <= maxUseOtherVar) {
                            return true;
                        }
                    }

                    //Handle zero len case separately
                    //No conflict if other var is never used
                    if (duVar.size() == 0) {
                        break;
                    }

                    int maxUseVar = Collections.max(duVar) + 10;
                    int minUseVar = Collections.min(duVar);

                    for (int otherVarDefUses : otherVarUseList) {
                        if (otherVarDefUses >= minUseVar && otherVarDefUses <= maxUseVar) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    //Allocate registers according to greedy algorithm
    //Find most used variable from varUsageCount, allocate to register, check for conflicts
    //If conflict found, allocate to next register and repeat process
    //If var could not be allocated to any register, ignore var and continue
    private void allocateRegisters() {
        while (!this.varUsageCount.isEmpty()) {
            //Get key with max usage value
            LlLocation var = Collections.max(this.varUsageCount.entrySet(), Map.Entry.comparingByValue()).getKey();

            if (var == null) {
                //Really?
                this.varUsageCount.remove(var);
                continue;
            }

            //Eliminate string variables
            if (var.toString().length() > 4 && var.toString().substring(0, 4).equals("#str")) {
                this.varUsageCount.remove(var);
                continue;
            }

            //Eliminate array location variables
            if (var instanceof LlLocationArray) {
                this.varUsageCount.remove(var);
                continue;
            }

            //Eliminate register allocation for parameters
            if (methodCFG.getParamsList().contains(var)) {
                this.varUsageCount.remove(var);
                continue;
            }

            for (String register : this.availableRegisters) {
                //If no conflict exists, allocate to selected register
                if (!isConflict(var, register)) {
                    this.varRegisterAllocations.put(var, register);
                    break;
                }
            }
            this.varUsageCount.remove(var);

        }
    }

    public RegisterAllocation(ArrayList<String> givenRegisters, CFG methodCFG) {
        this.availableRegisters = givenRegisters;
        this.methodCFG = methodCFG;
        this.varUsageCount = new HashMap<>();
        this.varRegisterAllocations = new HashMap<>();
        this.defUseChain = methodCFG.buildDefUseChains();

        int currentDepth = 0;

        //Use heuristics to count variable/LlLocation uses and defs
        for (Map.Entry<String, LlStatement> statementEntry : methodCFG.getBuilder().getStatementTable().entrySet()) {
            String statementLabel = statementEntry.getKey();
            LlStatement statement = statementEntry.getValue();

            //If label marks beginning of loop, increase depth by 1
            //If label marks end of loop, decrease depth by 1
            if ((statementLabel.length() > 7 && statementLabel.substring(0, 7).equals("END_FOR")) || (statementLabel.length() > 9 && statementLabel.substring(0, 9).equals("END_WHILE")))
                currentDepth--;

            else if ((statementLabel.length() > 3 && statementLabel.substring(0, 3).equals("FOR") )|| (statementLabel.length() > 5 && statementLabel.substring(0, 5).equals("WHILE")))
                currentDepth++;

            int addUsageConstant = (int) Math.pow(10, currentDepth);

            if (statement instanceof LlAssignStmtRegular) {
               LlLocation storeLocation = ((LlAssignStmtRegular) statement).getStoreLocation();
               this.updateVarUsageTable(storeLocation, addUsageConstant);

               LlComponent arg = ((LlAssignStmtRegular) statement).getOperand();
               this.updateVarUsageTable(arg, addUsageConstant);
            }

            else if (statement instanceof LlAssignStmtUnaryOp) {
                LlLocation storeLocation = ((LlAssignStmtUnaryOp) statement).getStoreLocation();
                this.updateVarUsageTable(storeLocation, addUsageConstant);

                LlComponent arg = ((LlAssignStmtUnaryOp) statement).getOperand();
                this.updateVarUsageTable(arg, addUsageConstant);
            }

            else if (statement instanceof LlAssignStmtBinaryOp) {
                LlLocation storeLocation = ((LlAssignStmtBinaryOp) statement).getStoreLocation();
                this.updateVarUsageTable(storeLocation, addUsageConstant);
                LlComponent arg1 = ((LlAssignStmtBinaryOp) statement).getLeftOperand();

                this.updateVarUsageTable(arg1, addUsageConstant);
                LlComponent arg2 = ((LlAssignStmtBinaryOp) statement).getLeftOperand();

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
//        System.out.println();
//        System.out.println(this.getVarRegisterAllocations());
//        System.out.println();
    }
}
