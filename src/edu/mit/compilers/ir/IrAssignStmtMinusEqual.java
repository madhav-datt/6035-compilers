package edu.mit.compilers.ir;

import edu.mit.compilers.LocalVariableTable;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrAssignStmtMinusEqual extends IrAssignStmt {
    private final IrExpr decrementBy;

    public IrAssignStmtMinusEqual(IrLocation storeLocation, IrExpr decrementBy) {
        super(storeLocation);
        this.decrementBy = decrementBy;
    }
    public String generateCode(StringBuilder assemblySoFar, LocalVariableTable table){
        String assembly = "";
        // first evaluate the decremented expression
        // then substract the returned value
        String decrementByReg = this.decrementBy.generateCode(assemblySoFar, table);
        String decrementedVarReg = table.getByValue(this.getStoreLocation().varName);
        assembly += String.format("sub %s, %s", decrementByReg, decrementedVarReg);
        assemblySoFar.append(assembly);
        return "";
    }

}
