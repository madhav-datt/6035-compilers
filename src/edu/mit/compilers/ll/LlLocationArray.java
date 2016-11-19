package edu.mit.compilers.ll;

/**
 * Created by devinmorgan on 11/18/16.
 */
public class LlLocationArray extends LlLocation {
    private final int elementIndex;

    public LlLocationArray(String varName, int elementIndex) {
        super(varName);
        this.elementIndex = elementIndex;
    }

    public int getElementIndex() {
        return this.elementIndex;
    }
}
