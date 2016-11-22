package edu.mit.compilers.ll;

/**
 * Created by devinmorgan on 11/18/16.
 */
public class LlJumpConditional extends LlJump{
    private final LlComponent condition;

    public LlJumpConditional(String jumpToLabel, LlComponent condition) {
        super(jumpToLabel);
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "if " + condition.toString() + " goto " + this.jumpToLabel.toString();
    }
}
