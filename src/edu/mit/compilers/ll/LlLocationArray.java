package edu.mit.compilers.ll;

/**
 * Created by devinmorgan on 11/18/16.
 */
public class LlLocationArray extends LlLocation {
    private final LlLocationVar elementIndex;

    public LlLocationArray(String varName, LlLocationVar elementIndex) {
        super(varName);
        this.elementIndex = elementIndex;
    }

    public LlLocationVar getElementIndex() {
        return this.elementIndex;
    }

    @Override
    public String toString() {
        return this.getVarName() + "[" + elementIndex.toString() + "] ";
    }
}
