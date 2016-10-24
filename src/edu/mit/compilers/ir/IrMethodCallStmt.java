package edu.mit.compilers.ir;

import java.util.List;

/**
 * Created by devinmorgan on 10/22/16.
 */
public class IrMethodCallStmt extends IrStatement{
    private final IrIdent methodName;
    private final List<IrArg> argsList;
    private final IrType methodType;

    public IrMethodCallStmt(IrIdent methodName, IrType methodType, List<IrArg> argsList) {
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
    public String generateCode(){
        return "call " + methodName;
    }
}
