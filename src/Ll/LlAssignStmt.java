package Ll;

/**
 * Created by devinmorgan on 11/18/16.
 */
public abstract class LlAssignStmt extends LlStatement {
    private final LlLocation storeLocation;

    public LlAssignStmt(LlLocation storeLocation) {
        this.storeLocation = storeLocation;
    }
}
