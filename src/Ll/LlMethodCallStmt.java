package Ll;

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
}
