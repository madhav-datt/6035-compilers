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

    @Override
    public boolean equals(Object obj) {
        // since we can't evaluate the array offset expression
        // during compile time, it is very difficult to know
        // if 2 LlLocationArray's are the same
        return this == obj;
    }
}
