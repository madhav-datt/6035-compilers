package edu.mit.compilers.ir;

import edu.mit.compilers.LocalVariableTable;

public abstract class IrExprSizeOf extends IrExpr {
    public IrExprSizeOf(int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
    }

    // Expects the inheriting classes to have this method implemented.
    public String generateCode(StringBuilder assemblySoFar, LocalVariableTable table){
        System.out.println("In: " + this.getClass());
         if(this instanceof IrExprSizeOfLocation){
            IrExprSizeOfLocation sizeOfLocation;
            sizeOfLocation = (IrExprSizeOfLocation) this;
            sizeOfLocation.generateCode(assemblySoFar, table);
        }
        if(this instanceof IrExprSizeOfType){
            IrExprSizeOfType sizeOfType;
            sizeOfType = (IrExprSizeOfType) this;
            sizeOfType.generateCode(assemblySoFar, table);
        }
        return "done";
    }
}