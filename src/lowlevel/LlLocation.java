package lowlevel;

/**
 * Created by devinmorgan on 11/18/16.
 */
public abstract class LlLocation extends Ll{
    private final String varName;

    public LlLocation(String varName) {
        this.varName = varName;
    }
}
