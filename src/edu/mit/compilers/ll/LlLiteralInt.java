package edu.mit.compilers.ll;

/**
 * Created by devinmorgan on 11/18/16.
 */
public class LlLiteralInt extends LlLiteral {
    private final long intValue;

    public LlLiteralInt(long intValue) {
        this.intValue = intValue;
    }

    public long getIntValue() {
        return this.intValue;
    }

    @Override
    public String toString() {
        return Long.toString(this.intValue);
    }
}
