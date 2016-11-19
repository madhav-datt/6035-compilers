package Ll;

/**
 * Created by devinmorgan on 11/18/16.
 */
public class LlAssignStmtBinaryOp extends LlAssignStmt{
    private final LlComponent leftArg;
    private final String operand;
    private final LlComponent rightArg;

    public LlAssignStmtBinaryOp(LlLocation storeLocation, LlComponent leftArg, String operand, LlComponent rightArg) {
        super(storeLocation);
        this.leftArg = leftArg;
        this.operand = operand;
        this.rightArg = rightArg;
    }
}