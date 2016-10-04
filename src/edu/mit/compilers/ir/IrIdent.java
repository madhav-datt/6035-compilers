package edu.mit.compilers.ir;

public class IrIdent extends Ir {

    private final String name;

    public IrIdent(String name, int lineNumber, int colNumber){
        super(lineNumber, colNumber);
        this.name = name;
    }
}