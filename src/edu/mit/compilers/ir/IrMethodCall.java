package edu.mit.compilers.ir;

import java.util.*;

public class IrMethodCall extends IrExpr{
    private final IrIdent methodName;
    private final List<IrArg> argsList;

    public IrMethodCall(IrIdent methodName, List<IrArg> argsList, int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.methodName = methodName;
        this.argsList = argsList;
    }
    public String toString(){
        return this.methodName + "(" +this.argsList.toString()+ ")";
    }

}