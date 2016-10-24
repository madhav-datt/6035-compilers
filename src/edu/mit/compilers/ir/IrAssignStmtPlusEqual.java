package edu.mit.compilers.ir;

import edu.mit.compilers.LocalVariableTable;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrAssignStmtPlusEqual extends IrAssignStmt{
    private final IrExpr incrementBy;

    public IrAssignStmtPlusEqual(IrLocation storeLocation, IrExpr incrementBy) {
        super(storeLocation);
        this.incrementBy = incrementBy;
    }
    public String generateCode(StringBuilder assemblySoFar, LocalVariableTable table){
        String assembly = "";
        // first evaluate the decremented expression
        // then substract the returned value
        String decrementByReg = this.incrementBy.generateCode(assemblySoFar, table);
        String decrementedVarReg = table.getByValue(this.getStoreLocation().varName);
        assembly += String.format("add %s, %s", decrementByReg, decrementedVarReg);
        assemblySoFar.append(assembly);
        assemblySoFar.append("\n");
        return "";
    }
}
