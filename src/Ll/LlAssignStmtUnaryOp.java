package Ll;

/**
 * Created by devinmorgan on 11/18/16.
 */
public class LlAssignStmtUnaryOp extends LlAssignStmt{
    private final LlComponent arg;
    private final String operand;

    public LlAssignStmtUnaryOp(LlLocation storeLocation, LlComponent arg, String operand) {
        super(storeLocation);
        this.arg = arg;
        this.operand = operand;
    }
}
