package edu.mit.compilers.ir;

import edu.mit.compilers.LocalVariableTable;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrAssignStmtEqual extends IrAssignStmt {
    private final IrExpr newValue;

    public IrAssignStmtEqual(IrLocation storeLocation, IrExpr newValue) {
        super(storeLocation);
        this.newValue = newValue;
    }
    // Evaluates the expression on the left side and moves the value into the register.
    // Expects the expression to return a register name. If the statement evaluates to a
    // literal (is a literal), then the string form of the literal suffices.
    public String generateCode(StringBuilder assemblySoFar, LocalVariableTable localVariableTable){
        System.out.println("In: " + this.getClass());
        localVariableTable.pushToStack(this.getStoreLocation().varName);
        String moveFromReg = newValue.generateCode(assemblySoFar, localVariableTable);
        assemblySoFar.append("mov " + moveFromReg  +", "+ localVariableTable.getByValue(this.getStoreLocation())) ;
        assemblySoFar.append("\n");
        return "";
    }
}

