package edu.mit.compilers.ll;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by devinmorgan on 11/18/16.
 */
public class LlMethodCallStmt extends LlStatement {

    private final LlLocation returnLocation;
    private final String methodName;
    private final List<LlComponent> argsList;

    public LlMethodCallStmt(String methodName, List<LlComponent> argsList, LlLocation returnLocation) {
        this.methodName = methodName;
        this.argsList = argsList;
        this.returnLocation = returnLocation;
    }

    public LlLocation getReturnLocation() {
        return returnLocation;
    }

    public List<LlComponent> getArgsList() {
        return new ArrayList<>(this.argsList);
    }

    @Override
    public String toString() {
        String argsString = "";
        for(LlComponent arg : argsList){
            argsString +=  arg.toString() +",";
        }
        return this.returnLocation.toString() + " = " + this.methodName + "(" + argsString +")" ;
    }
}
