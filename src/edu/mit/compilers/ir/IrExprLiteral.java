package edu.mit.compilers.ir;

import edu.mit.compilers.LocalVariableTable;

public abstract class IrExprLiteral extends IrExpr {
    public IrExprLiteral(int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
    }
    // Expects the inheriting classes to have this method implemented.
    public String generateCode(StringBuilder assemblySoFar, LocalVariableTable table){
        return "";
    }

}