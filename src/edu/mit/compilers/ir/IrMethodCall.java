package edu.mit.compilers.ir;

import java.util.*;

public class IrMethodCall extends IrExpr{
    private final IrIdent methodName;
    private final List<IrArg> argsList;
    private final IrType methodType;

    public IrMethodCall(IrIdent methodName, IrType methodType, List<IrArg> argsList) {
        super(methodName.getLineNumber(), methodName.getColNumber());
        this.methodName = methodName;
        this.argsList = argsList;
        this.methodType = methodType;
    }

    public IrType getExpressionType() {
        return this.methodType;
    }

    public String toString(){
        return this.methodName + "(" +this.argsList.toString()+ ")";
    }

}