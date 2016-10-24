package edu.mit.compilers.ir;

import edu.mit.compilers.LocalVariableTable;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrLocationVar extends IrLocation {
    public IrLocationVar(IrIdent varName, IrType varType, int lineNumber, int colNumber) {
        super(varName, varType,lineNumber, colNumber);
    }

    @Override
    public IrType getExpressionType() {
        return this.varType;
    }
    // Returns the register where this field is stored.
    // Doesn't mutate the assemblyCode or the VariableTable
    public String generateCode(StringBuilder assemblySoFar, LocalVariableTable table){
        return table.getByValue(this.varName);
    }
}
