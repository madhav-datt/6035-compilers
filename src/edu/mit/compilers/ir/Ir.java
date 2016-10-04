package edu.mit.compilers.ir;

public abstract class Ir {
    // keep track of the line & column number for
    // for error reporting purposes
    private final int lineNumber;
    private final int colNumber;

    public Ir(int lineNumber, int colNumber) {
        this.lineNumber = lineNumber;
        this.colNumber = colNumber;
    }

}