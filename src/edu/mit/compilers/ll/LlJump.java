package edu.mit.compilers.ll;

/**
 * Created by devinmorgan on 11/18/16.
 */
public abstract class LlJump extends LlStatement {
    protected final String jumpToLabel;

    public LlJump(String jumpToLabel) {
        this.jumpToLabel = jumpToLabel;
    }

    public String getJumpToLabel() {
        return jumpToLabel;
    }
}
