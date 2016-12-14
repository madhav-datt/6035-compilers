package edu.mit.compilers.ll;

/**
 * Created by abel on 11/20/16.
 */
public class LlEmptyStmt extends LlStatement{
    @Override
    public String toString() {
        return "EMPTY_STATEMENT";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof LlEmptyStmt;
    }

    @Override
    public int hashCode() {
        return 17; // some arbitrary prime;
    }
}
