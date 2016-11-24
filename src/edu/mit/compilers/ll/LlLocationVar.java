package edu.mit.compilers.ll;

/**
 * Created by devinmorgan on 11/18/16.
 */
public class LlLocationVar extends LlLocation {
    public LlLocationVar(String varName) {
        super(varName);
    }

    @Override
    public String toString() {
        return this.getVarName().toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof LlLocationVar) {
            LlLocationVar that = (LlLocationVar) obj;
            return this.getVarName().equals(that.getVarName());
        }
        return false;
    }
}
