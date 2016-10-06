package edu.mit.compilers.ir.MethodCall;
import java.util.*;
import edu.mit.compilers.ir.*;
public class IrMethodCall extends Ir {
    private final IrIdent methodName;
    private final List<IrArg> argsList;

    public IrMethodCall(IrIdent methodName, List<IrArg> argsList, int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.methodName = methodName;
        this.argsList = argsList;
    }
}