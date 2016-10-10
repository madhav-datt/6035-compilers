package edu.mit.compilers;

import edu.mit.compilers.ir.Ir;

import java.util.*;

public class SymbolTable {
    private Hashtable<String, Ir> symbolTable = new Hashtable<String, Ir>();
    protected SymbolTable prev;

    public SymbolTable(){
        this.symbolTable = new Hashtable<String, Ir>();
    }

    public void put(String identifier, Ir value){
        symbolTable.put(identifier, value);
    }

    public Ir get(String s){
        for( SymbolTable e = this; e != null; e = e.prev ) {
            Ir found = (Ir) (e.symbolTable.get(s));
            if (found != null) return found;
        }
        return null;
    }

}