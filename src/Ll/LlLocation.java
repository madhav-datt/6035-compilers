package Ll;

/**
 * Created by devinmorgan on 11/18/16.
 */
public abstract class LlLocation extends LlComponent {
    private final String varName;

    public LlLocation(String varName) {
        this.varName = varName;
    }

    public String getVarName() {
        return this.varName;
    }
}
