package edu.mit.compilers.ll;

/**
 * Created by devinmorgan on 11/18/16.
 */
public class LlLiteralInt extends LlLiteral {
    private final int intValue;

    public LlLiteralInt(int intValue) {
        this.intValue = intValue;
    }

    public int getIntValue() {
        return this.intValue;
    }

    @Override
    public String toString() {
        return Integer.toString(this.intValue);
    }
}
