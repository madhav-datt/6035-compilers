package edu.mit.compilers.ir;

import edu.mit.compilers.LocalVariableTable;
import javafx.beans.binding.StringBinding;

public abstract class IrOper extends IrExpr{

    public IrOper(int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
    }

    public String toString(){
        return "IrOper";
    }

    // should return a register value holding the result of the operation after
    // the assembly is generated
    public String generateCode(StringBuilder assemblySoFar, LocalVariableTable table){
        String register = "";
        if(this instanceof IrOperBinary){

        }
        if(this instanceof IrOperUnary){

        }
        return register;
    }
}
