package edu.mit.compilers.cfg;

import edu.mit.compilers.LlBuilder;
import edu.mit.compilers.ll.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

/**
 * Created by devinmorgan on 11/26/16.
 */
public class GlobalCP {
    private final HashMap<LlLocation, LlComponent> copyTable;

    public GlobalCP(HashMap<LlLocation, LlComponent> copyTable) {
        this.copyTable = new HashMap<>(copyTable);
    }

    // mutates the BasicBlock by performing copy
    // propagation using the passed HashMap. If
    // the HashMap is empty, then it performs local
    // copy propagation.
    private static boolean performLocalCP(BasicBlock bb, GlobalCP cp, HashSet<LlLocation> globalVars) {
        LinkedHashMap<String, LlStatement> stmtsToLabelsMap = bb.getLabelsToStmtsMap();
        LinkedHashMap<String, LlStatement> oldCopy = new LinkedHashMap<>(stmtsToLabelsMap);

        // 2) loop through the current linked hashmap
        for (String label : bb.getLabelsToStmtsMap().keySet()) {
            LlStatement stmt = bb.getLabelsToStmtsMap().get(label);

            if (stmt instanceof LlAssignStmtUnaryOp) {
                LlAssignStmtUnaryOp unaryOp = (LlAssignStmtUnaryOp) stmt;

                // swap out operand in statement if its a copy
                LlAssignStmtUnaryOp optStmt = cp.swapOperandsForUnaryStmt(unaryOp);
                stmtsToLabelsMap.put(label, optStmt);
                
                // remove this var from the copyTable
                cp.copyTable.remove(unaryOp.getStoreLocation());
            }
            else if (stmt instanceof LlAssignStmtBinaryOp) {
                LlAssignStmtBinaryOp binaryOp = (LlAssignStmtBinaryOp) stmt;

                // swap out left and/or right operand if its a copy
                LlAssignStmtBinaryOp optStmt = cp.swapOperandForBinaryStmt(binaryOp);
                stmtsToLabelsMap.put(label, optStmt);

                // remove this var from the copyTable
                cp.copyTable.remove(binaryOp.getStoreLocation());
            }
            else if (stmt instanceof LlJumpConditional) {
                LlJumpConditional condJmp = (LlJumpConditional) stmt;

                // swap out operand in statement if its a copy
                LlJumpConditional optStmt = cp.swapVariableForConditionalJump(condJmp);
                stmtsToLabelsMap.put(label, optStmt);
            }
            else if (stmt instanceof LlMethodCallStmt) {
                LlMethodCallStmt method = (LlMethodCallStmt) stmt;

                // swap out any of the parameters in the method call if they are a copy
                LlMethodCallStmt optStmt = cp.swapVariableForMethod(method);
                stmtsToLabelsMap.put(label, optStmt);
            }
            else if (stmt instanceof LlReturn) {
                LlReturn rtnStmt = (LlReturn) stmt;

                // swap out operand in statement if its a copy
                LlReturn optStmt = cp.swapVariableForReturnStmt(rtnStmt);
                stmtsToLabelsMap.put(label, optStmt);
            }
            else if (stmt instanceof LlAssignStmtRegular) {
                LlAssignStmtRegular stmtRegular = (LlAssignStmtRegular) stmt;

                // swap out operand in statement if its a copy
                LlAssignStmtRegular optStmt = cp.swapOperandsForRegularStmt(stmtRegular);
                stmtsToLabelsMap.put(label, optStmt);

                // add LHS definitions to the copyTable
                // TODO: Figure out how to make this work for a[i] (i.e. arrays)
                if (stmtRegular.getStoreLocation() instanceof LlLocationVar
                        && !(stmtRegular.getOperand() instanceof LlLocationArray)
                        && !globalVars.contains(stmtRegular.getStoreLocation())) { // <-- this means it works for constants of vars
                    cp.copyTable.put(stmtRegular.getStoreLocation(), stmtRegular.getOperand());
                }
            }
        }

        // check to see if the BB got updated by copyProp
        return stmtsToLabelsMap.equals(oldCopy);
    }

    public static void performGlobalCP(CFG cfg, HashSet<LlLocation> globalVars) {
        ArrayList<BasicBlock> basicBlocks = cfg.getBasicBlocks();

        // get the HashMap of BasicBlock => (u <-- v)
        HashMap<BasicBlock, HashMap<LlLocation, LlComponent>> copyAssignmentsIN
                = CopyAssignments.getCopyAssignmentsForCFG(cfg);

        // purify copyAssignmentsIN from any globalVariables
        for (BasicBlock bb : copyAssignmentsIN.keySet()) {
            HashMap<LlLocation, LlComponent> bbCopyAssignments = copyAssignmentsIN.get(bb);

            // look over each global var
            for (LlLocation globalVar : globalVars) {

                // if one is found reomve that from the mapping
                if (bbCopyAssignments.containsKey(globalVar)) {
                    bbCopyAssignments.remove(globalVar);
                }
            }
        }

        // perform copy propagation until it converges
        boolean changed = true;
        while (changed) {
            // assume nothing changes at first
            changed = false;

            // perform copy propagation inside the block with the
            // set of copyAssignments available at the start of the BB
            for (BasicBlock bb : basicBlocks) {
                GlobalCP cp = new GlobalCP(copyAssignmentsIN.get(bb));

                // if localCP does change one of the BasicBlocks, then has not reached convergence
                if (!GlobalCP.performLocalCP(bb, cp, globalVars)) {
                    changed = true;
                }
            }
        }
    }

    private LlLocationArray getCopyPropagatedLlLocationArray(LlLocationArray array) {
        LlLocationArray optimizedArray = array;

        if (array.getElementIndex() instanceof LlLocationVar) {
            LlLocationVar indexVar = (LlLocationVar) array.getElementIndex();

            if (this.copyTable.containsKey(indexVar)) {
                optimizedArray = new LlLocationArray(array.getVarName(), this.copyTable.get(indexVar));
            }
        }

        return optimizedArray;
    }

    private LlComponent getCopyPropagatedLlLocationVar(LlLocationVar var) {
        LlComponent optimizedVar = var;

        if (this.copyTable.containsKey(var)) {
            optimizedVar = this.copyTable.get(var);
        }

        return optimizedVar;
    }

    private LlReturn swapVariableForReturnStmt(LlReturn rtnStmt) {
        LlComponent optimizedReturnVal = rtnStmt.getReturnValue();

        // check if the return value is a variable in the copyTable
        if (rtnStmt.getReturnValue() instanceof LlLocationVar) {
            optimizedReturnVal
                    = getCopyPropagatedLlLocationVar((LlLocationVar) rtnStmt.getReturnValue());
        }

        // check if the return value is an array whose index is in the copyTable
        else if (rtnStmt.getReturnValue() instanceof LlLocationArray) {
            optimizedReturnVal
                    = getCopyPropagatedLlLocationArray((LlLocationArray) rtnStmt.getReturnValue());
        }
        return new LlReturn(optimizedReturnVal);
    }

    private LlMethodCallStmt swapVariableForMethod(LlMethodCallStmt methodStmt) {
        ArrayList<LlComponent> optimizedArgsList = new ArrayList<>();

        for (LlComponent arg : methodStmt.getArgsList()) {
            LlComponent optimizedArg = arg;

            // if the arg is a variable, check to see if we can copyPropogate
            if (arg instanceof LlLocationVar) {
                LlLocationVar argVar = (LlLocationVar) arg;
                optimizedArg = getCopyPropagatedLlLocationVar(argVar);
            }

            // if the arg is an array access, check to see if we can copyPropogate the index
            else if (arg instanceof LlLocationArray) {
                LlLocationArray argArray = (LlLocationArray) arg;
                optimizedArg = getCopyPropagatedLlLocationArray(argArray);
            }

            // add to new list of optimized args
            optimizedArgsList.add(optimizedArg);
        }
        return new LlMethodCallStmt(methodStmt.getMethodName(), optimizedArgsList, methodStmt.getReturnLocation());
    }

    private LlJumpConditional swapVariableForConditionalJump(LlJumpConditional condJmp) {
        // check if the operand is a variable in the copyTable
        if (condJmp.getCondition() instanceof LlLocationVar) {
            LlLocationVar var = (LlLocationVar) condJmp.getCondition();

            // return a new statement with the var from copyTable if it exists
            if (this.copyTable.containsKey(var)) {
                return new LlJumpConditional(condJmp.getJumpToLabel(), this.copyTable.get(var));
            }
        }
        return condJmp;
    }

    private LlAssignStmtRegular swapOperandsForRegularStmt(LlAssignStmtRegular regular) {
        LlLocation optimizedStoreLoc = regular.getStoreLocation();
        LlComponent optimizedOperand = regular.getOperand();

        // 1) check to see if the storeLocation is an LlLocationArray whose index can be copy-propagated
        if (regular.getStoreLocation() instanceof LlLocationArray) {
            optimizedStoreLoc = getCopyPropagatedLlLocationArray((LlLocationArray) regular.getStoreLocation());
        }

        // 2) check to see if the the operand is an LlLocationArray whose index can be copy-propagated
        if (regular.getOperand() instanceof LlLocationArray) {
            optimizedOperand = getCopyPropagatedLlLocationArray((LlLocationArray) regular.getOperand());
        }

        // 3) otherwise check to see if the operand is just a straight up var that can be copy-propagated
        else if (regular.getOperand() instanceof LlLocationVar) {
            optimizedOperand = getCopyPropagatedLlLocationVar((LlLocationVar) regular.getOperand());
        }

        // 4) return the optimized stmt
        return new LlAssignStmtRegular(optimizedStoreLoc, optimizedOperand);
    }

    private LlAssignStmtUnaryOp swapOperandsForUnaryStmt(LlAssignStmtUnaryOp unaryOp) {
        LlLocation optimizedStoreLoc = unaryOp.getStoreLocation();
        LlComponent optimizedOperand = unaryOp.getOperand();

        // 1) check to see if the storeLocation is an LlLocationArray whose index can be copy-propagated
        if (unaryOp.getStoreLocation() instanceof LlLocationArray) {
            optimizedStoreLoc = getCopyPropagatedLlLocationArray((LlLocationArray) unaryOp.getStoreLocation());
        }

        // 2) check to see if the the operand is an LlLocationArray whose index can be copy-propagated
        if (unaryOp.getOperand() instanceof LlLocationArray) {
            optimizedOperand = getCopyPropagatedLlLocationArray((LlLocationArray) unaryOp.getOperand());
        }

        // 3) otherwise check to see if the operand is just a straight up var that can be copy-propagated
        else if (unaryOp.getOperand() instanceof LlLocationVar) {
            optimizedOperand = getCopyPropagatedLlLocationVar((LlLocationVar) unaryOp.getOperand());
        }

        // 4) return the optimized stmt
        return new LlAssignStmtUnaryOp(optimizedStoreLoc, optimizedOperand, unaryOp.getOperator());
    }

    private LlAssignStmtBinaryOp swapOperandForBinaryStmt(LlAssignStmtBinaryOp binaryOp) {
        LlLocation optimizedStoreLoc = binaryOp.getStoreLocation();
        LlComponent optimizedLeftOp = binaryOp.getLeftOperand();
        LlComponent optimizedRightOp = binaryOp.getRightOperand();

        // 1) check to see if the storeLocation is an LlLocationArray whose index can be copy-propagated
        if (binaryOp.getStoreLocation() instanceof LlLocationArray) {
            optimizedStoreLoc = getCopyPropagatedLlLocationArray((LlLocationArray) binaryOp.getStoreLocation());
        }

        // 2) check to see if the the leftOperand is an LlLocationArray whose index can be copy-propagated
        if (binaryOp.getLeftOperand() instanceof LlLocationArray) {
            optimizedLeftOp = getCopyPropagatedLlLocationArray((LlLocationArray) binaryOp.getLeftOperand());
        }

        // 3) otherwise check to see if the leftOperand is just a straight up var that can be copy-propagated
        else if (binaryOp.getLeftOperand() instanceof LlLocationVar) {
            optimizedLeftOp = getCopyPropagatedLlLocationVar((LlLocationVar) binaryOp.getLeftOperand());
        }

        // 5) check to see if the the leftOperand is an LlLocationArray whose index can be copy-propagated
        if (binaryOp.getRightOperand() instanceof LlLocationArray) {
            optimizedRightOp = getCopyPropagatedLlLocationArray((LlLocationArray) binaryOp.getRightOperand());
        }

        // 6) otherwise check to see if the leftOperand is just a straight up var that can be copy-propagated
        else if (binaryOp.getRightOperand() instanceof LlLocationVar) {
            optimizedRightOp = getCopyPropagatedLlLocationVar((LlLocationVar) binaryOp.getRightOperand());
        }

        // 7) return the optimized stmt
        return new LlAssignStmtBinaryOp(optimizedStoreLoc, optimizedLeftOp, binaryOp.getOperation(), optimizedRightOp);
    }

}
